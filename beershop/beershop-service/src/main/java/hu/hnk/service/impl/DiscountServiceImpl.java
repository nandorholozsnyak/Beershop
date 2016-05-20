package hu.hnk.service.impl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.hnk.beershop.model.Cargo;
import hu.hnk.beershop.model.CartItem;
import hu.hnk.beershop.model.Rank;
import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.discounts.DailyRankBonus;
import hu.hnk.beershop.service.discounts.DiscountType;
import hu.hnk.beershop.service.interfaces.DiscountService;
import hu.hnk.beershop.service.interfaces.UserService;
import hu.hnk.beershop.service.restrictions.BuyActionRestrictions;

/**
 * A kedvezményeket validáló szolgáltatás.
 * 
 * A {@link DiscountType} kedvezményeket validáló osztály leírása. Többféle
 * kedvezménytípust képes validálni, mindig egy aktuálisan készülőben lévő
 * szállításra érvényesíti ezeket.
 * 
 * @author Nandi
 *
 */
@Stateless
@Local(DiscountService.class)
public class DiscountServiceImpl implements DiscountService {

	/**
	 * Sikeres kedvezmény aktiválásnak loggolási konstansa.
	 */
	private static final String DISCOUNT_SUCCESFULLY_COMPLETED = "Discount succesfully completed.";

	/**
	 * Az osztály loggere.
	 */
	public static final Logger logger = LoggerFactory.getLogger(DiscountServiceImpl.class);

	/**
	 * A felhasználókat kezelő szolgáltatás.
	 */
	@EJB
	private UserService userService;

	/**
	 * A kedvezmények listája.
	 */
	private List<DailyRankBonus> discountList = DailyRankBonus.getDailyBonuses();

	@Override
	public List<DiscountType> getAvailableDailyRankBonusesForUser(User user, LocalDate today) {
		List<DiscountType> result;
		try {
			result = discountList.stream()
					.filter(p -> p.getDay()
							.equals(today.getDayOfWeek())
							&& p.getRanks()
									.contains(userService.countRankFromXp(user)))
					.map(DailyRankBonus::getDiscounts)
					.findFirst()
					.get();
		} catch (NoSuchElementException e) {
			result = Collections.emptyList();
			logger.warn("Returning EMPTY_LIST for getAvaiableDailyRankBonusesForUser()",e);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validateDiscount(DiscountType discountType, Cargo cargo, LocalDate today) {
		if (discountType.equals(DiscountType.FREESHIPPING))
			validateFreeShippingDiscount(cargo, today);
		if (discountType.equals(DiscountType.THECHEAPESTFORFREE))
			validateTheCheapestForFreeDiscount(cargo, today);
		if (discountType.equals(DiscountType.EXTRABONUSPOINTS))
			validateExtraBonusPointsDiscount(cargo, today);
		if (discountType.equals(DiscountType.FIFTYPERCENTAGE))
			validateFiftyPercentageDiscount(cargo, today);

	}

	/**
	 * Az ingyenes szállítás validálása.
	 * 
	 * @param cargo
	 *            a készülőben lévő szállítás.
	 * @param today
	 *            a mai nap.
	 */
	private void validateFreeShippingDiscount(Cargo cargo, LocalDate today) {

		DiscountType actualDiscount = DiscountType.FREESHIPPING;

		// Kikeressük az ingyenes szállításhoz tartozó kedvezményt információt.
		Optional<DailyRankBonus> freeShippingBonus = findDiscountByType(actualDiscount);

		// Megnézzük a rangokat amik jogosultak a kedvezményhez.
		List<Rank> allowedRanks;
		if (freeShippingBonus.isPresent()) {
			allowedRanks = freeShippingBonus.get()
					.getRanks();
			logger.info("Allowed ranks for Free Shipping discount: " + allowedRanks);
			debugLog(today, freeShippingBonus);
			if (isTodayDiscountDay(freeShippingBonus.get(), today)) {
				if (allowedRanks.contains(userService.countRankFromXp(cargo.getUser()))) {
					cargo.setTotalPrice(cargo.getTotalPrice() - BuyActionRestrictions.getShippingCost());
					logger.info(DISCOUNT_SUCCESFULLY_COMPLETED);
				}
			}
		}
	}

	/**
	 * A legolcsóbb termék ingyenességének validálása.
	 * 
	 * @param cargo
	 *            a készülőben lévő szállítás.
	 * @param today
	 *            a mai nap.
	 */
	// Csak ha legalább két terméket tartalmaz a kosár.
	private void validateTheCheapestForFreeDiscount(Cargo cargo, LocalDate today) {
		DiscountType actualDiscount = DiscountType.THECHEAPESTFORFREE;

		// Kikeressük a "legolcsóbb termék ingyenes" kedvezményt.
		Optional<DailyRankBonus> theCheapestForFree = findDiscountByType(actualDiscount);

		List<Rank> allowedRanks;
		if (theCheapestForFree.isPresent()) {
			allowedRanks = theCheapestForFree.get()
					.getRanks();
			logger.info("Allowed ranks for the Cheapest For Free discount:" + allowedRanks);
			debugLog(today, theCheapestForFree);
			if (isTodayDiscountDay(theCheapestForFree.get(), today)) {
				if (allowedRanks.contains(userService.countRankFromXp(cargo.getUser()))) {
					if (cargo.getItems()
							.size() > 1) {
						CartItem cheapest = cargo.getItems()
								.stream()
								.filter(p -> p.getBeer()
										.equals(cargo.getItems()
												.stream()
												.map(CartItem::getBeer)
												.min((c1, c2) -> Double.compare(c1.getPrice(), c2.getPrice()))
												.get()))
								.findFirst()
								.get();
						cargo.setTotalPrice(cargo.getTotalPrice() - cheapest.getQuantity() * cheapest.getBeer()
								.getPrice());
					}
					logger.info(DISCOUNT_SUCCESFULLY_COMPLETED);
				}
			}
		}
	}

	// A szállítás teljes árának az ötödét hozzáadja mint bónusz pont a
	// felhasználónak.
	/**
	 * Az extra bónusz pontok validálása.
	 * 
	 * @param cargo
	 *            a készülőben lévő szállítás.
	 * @param today
	 *            a mai nap.
	 */
	private void validateExtraBonusPointsDiscount(Cargo cargo, LocalDate today) {
		DiscountType actualDiscount = DiscountType.EXTRABONUSPOINTS;

		// Kikeressük az extra bónusz pontos kedvezményt.
		Optional<DailyRankBonus> extraBonusPoints = findDiscountByType(actualDiscount);

		List<Rank> allowedRanks;
		if (extraBonusPoints.isPresent()) {
			allowedRanks = extraBonusPoints.get()
					.getRanks();
			logger.info("Allowed ranks for the Extra Bonus Points discount:" + allowedRanks);
			debugLog(today, extraBonusPoints);
			if (isTodayDiscountDay(extraBonusPoints.get(), today)) {
				if (allowedRanks.contains(userService.countRankFromXp(cargo.getUser()))) {
					cargo.getUser()
							.setPoints(cargo.getUser()
									.getPoints() + cargo.getTotalPrice() / 5);
					logger.info(DISCOUNT_SUCCESFULLY_COMPLETED);
				}
			}
		}
	}

	/**
	 * Az 50%-os kedvezmény validálása.
	 * 
	 * @param cargo
	 *            a készülőben lévő szállítás.
	 * @param today
	 *            a mai nap.
	 */
	private void validateFiftyPercentageDiscount(Cargo cargo, LocalDate today) {
		DiscountType actualDiscount = DiscountType.FIFTYPERCENTAGE;

		// Kikeressük az extra bónusz pontos kedvezményt.
		Optional<DailyRankBonus> fiftyPercentage = findDiscountByType(actualDiscount);

		List<Rank> allowedRanks;
		if (fiftyPercentage.isPresent()) {
			allowedRanks = fiftyPercentage.get()
					.getRanks();
			logger.info("Allowed ranks for the Extra Bonus Points discount:" + allowedRanks);
			debugLog(today, fiftyPercentage);
			if (isTodayDiscountDay(fiftyPercentage.get(), today)
					&& allowedRanks.contains(userService.countRankFromXp(cargo.getUser()))) {
				cargo.setTotalPrice(cargo.getTotalPrice() * 0.5);
				logger.info(DISCOUNT_SUCCESFULLY_COMPLETED);
			}
		}
	}

	/**
	 * Visszaadja a kedvezményt típus alapján a kedvezmények listájából.
	 * 
	 * @param actualDiscount
	 *            az aktuálisan megkeresendő kedvezmény.
	 * @return a kedvezményhez megtalált napi bónusz.
	 */
	private Optional<DailyRankBonus> findDiscountByType(DiscountType actualDiscount) {
		return discountList.stream()
				.filter(e -> e.getDiscounts()
						.contains(actualDiscount))
				.collect(Collectors.toList())
				.stream()
				.findFirst();

	}

	/**
	 * Megvizsgálja hogy a paraméterként kapott napi bónusz aktiválható-e vagy
	 * sem.
	 * 
	 * @param discount
	 *            a napi bónusz.
	 * @param today
	 *            a mai nap.
	 * @return <code>true</code> ha a mai napon aktiválható, <code>false</code>
	 *         ha nem.
	 */
	private boolean isTodayDiscountDay(DailyRankBonus discount, LocalDate today) {
		return today.getDayOfWeek()
				.equals(discount.getDay());
	}

	/**
	 * Debug log készítése.
	 * 
	 * @param today
	 *            a mai nap.
	 * @param bonus
	 *            a napi bónusz.
	 */
	private void debugLog(LocalDate today, Optional<DailyRankBonus> bonus) {
		DiscountType discount = bonus.get()
				.getDiscounts()
				.get(0);
		logger.debug(discount.toString() + "day:" + bonus.get()
				.getDay());
		logger.debug("Today:" + today.getDayOfWeek());
		logger.debug("Is this day " + discount.toString() + " discount day:" + isTodayDiscountDay(bonus.get(), today));
	}

	/**
	 * Beállítja a felhasználókat kezelő szolgáltatást.
	 * 
	 * @param userService
	 *            a felhasználókat kezelő szolgáltatás.
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
