package hu.hnk.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import hu.hnk.beershop.model.Beer;
import hu.hnk.beershop.model.Cargo;
import hu.hnk.beershop.model.CartItem;
import hu.hnk.beershop.model.Rank;
import hu.hnk.beershop.service.interfaces.DiscountService;
import hu.hnk.beershop.service.interfaces.UserService;
import hu.hnk.beershop.service.tools.DiscountType;
import hu.hnk.service.tools.BuyActionRestrictions;
import hu.hnk.service.tools.DailyRankBonus;

@Stateless
@Local(DiscountService.class)
public class DiscountServiceImpl implements DiscountService {

	/**
	 * Az osztály loggere.
	 */
	public static final Logger logger = Logger.getLogger(DiscountServiceImpl.class);

	@EJB
	private UserService userService;

	private List<DailyRankBonus> discountList = DailyRankBonus.getDailyBonuses();

	@Override
	public void validateDiscount(DiscountType discountType, Cargo cargo, LocalDate today) {
		if (discountType.equals(DiscountType.FreeShipping))
			validateFreeShippingDiscount(cargo, today);
		if (discountType.equals(DiscountType.TheCheapestForFree))
			validateTheCheapestForFreeDiscount(cargo, today);
		if (discountType.equals(DiscountType.ExtraBonusPoints))
			validateExtraBonusPointsDiscount(cargo, today);
	}

	private void validateFreeShippingDiscount(Cargo cargo, LocalDate today) {

		DiscountType actualDiscount = DiscountType.FreeShipping;

		// Kikeressük az ingyenes szállításhoz tartozó kedvezményt információt.
		Optional<DailyRankBonus> freeShippingBonus = findDiscountByType(actualDiscount);

		// Megnézzük a rangokat amik jogosultak a kedvezményhez.
		List<Rank> allowedRanks = null;
		if (freeShippingBonus.isPresent()) {
			allowedRanks = freeShippingBonus.get()
					.getRanks();
			logger.info("Allowed ranks for Free Shipping discount: " + allowedRanks);
			logger.debug("Free Shipping Discount day: " + freeShippingBonus.get()
					.getDay());
			logger.debug("Today: " + today.getDayOfWeek());
			logger.debug(
					"Is this day  Free Shipping discount day:" + isTodayDiscountDay(freeShippingBonus.get(), today));
			if (isTodayDiscountDay(freeShippingBonus.get(), today)) {
				if (allowedRanks.contains(userService.countRankFromXp(cargo.getUser()))) {
					cargo.setTotalPrice(cargo.getTotalPrice() - BuyActionRestrictions.getShippingCost());
					logger.info("Discount succesfully completed.");
				}
			}
		}
	}

	// Csak ha legalább két terméket tartalmaz a kosár.
	private void validateTheCheapestForFreeDiscount(Cargo cargo, LocalDate today) {
		DiscountType actualDiscount = DiscountType.TheCheapestForFree;

		// Kikeressük a "legolcsóbb termék ingyenes" kedvezményt.
		Optional<DailyRankBonus> theCheapestForFree = findDiscountByType(actualDiscount);

		List<Rank> allowedRanks = null;
		if (theCheapestForFree.isPresent()) {
			allowedRanks = theCheapestForFree.get()
					.getRanks();
			logger.info("Allowed ranks for the Cheapest For Free discount:" + allowedRanks);
			logger.debug("Cheapest For Free Discount day:" + theCheapestForFree.get()
					.getDay());
			logger.debug("Today:" + today.getDayOfWeek());
			logger.debug("Is this day Cheapest For Free discount day:"
					+ isTodayDiscountDay(theCheapestForFree.get(), today));
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
					logger.info("Discount succesfully completed.");
				}
			}
		}
	}
	
	// A szállítás teljes árának az ötödét hozzáadja mint bónusz pont a felhasználónak.
	private void validateExtraBonusPointsDiscount(Cargo cargo, LocalDate today) {
		DiscountType actualDiscount = DiscountType.ExtraBonusPoints;

		// Kikeressük az extra bónusz pontos kedvezményt.
		Optional<DailyRankBonus> extraBonusPoints = findDiscountByType(actualDiscount);

		List<Rank> allowedRanks = null;
		if (extraBonusPoints.isPresent()) {
			allowedRanks = extraBonusPoints.get()
					.getRanks();
			logger.info("Allowed ranks for the Extra Bonus Points discount:" + allowedRanks);
			logger.debug("Extra Bonus Points Discount day:" + extraBonusPoints.get()
					.getDay());
			logger.debug("Today:" + today.getDayOfWeek());
			logger.debug(
					"Is this day Cheapest For Free discount day:" + isTodayDiscountDay(extraBonusPoints.get(), today));
			if (isTodayDiscountDay(extraBonusPoints.get(), today)) {
				if (allowedRanks.contains(userService.countRankFromXp(cargo.getUser()))) {
					cargo.getUser()
							.setPoints(cargo.getUser()
									.getPoints() + cargo.getTotalPrice() / 5);
					logger.info("Discount succesfully completed.");
				}
			}
		}
	}

	private Optional<DailyRankBonus> findDiscountByType(DiscountType actualDiscount) {
		Optional<DailyRankBonus> freeShippingBonus = discountList.stream()
				.filter(e -> e.getDiscounts()
						.contains(actualDiscount))
				.collect(Collectors.toList())
				.stream()
				.findFirst();
		return freeShippingBonus;
	}

	private boolean isTodayDiscountDay(DailyRankBonus discount, LocalDate today) {
		return today.getDayOfWeek()
				.equals(discount.getDay());
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
