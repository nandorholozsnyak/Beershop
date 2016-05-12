package hu.hnk.service.impl;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.hnk.beershop.exception.CanNotBuyLegendaryBeerYetException;
import hu.hnk.beershop.exception.DailyBuyActionLimitExceeded;
import hu.hnk.beershop.model.Cargo;
import hu.hnk.beershop.model.CartItem;
import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.interfaces.CargoService;
import hu.hnk.beershop.service.interfaces.DiscountService;
import hu.hnk.beershop.service.interfaces.RestrictionCheckerService;
import hu.hnk.beershop.service.logfactory.EventLogType;
import hu.hnk.beershop.service.tools.BuyActionRestrictions;
import hu.hnk.beershop.service.tools.DiscountType;
import hu.hnk.interfaces.CargoDao;
import hu.hnk.interfaces.CartItemDao;
import hu.hnk.interfaces.EventLogDao;
import hu.hnk.interfaces.UserDao;
import hu.hnk.service.cobertura.annotation.CoverageIgnore;
import hu.hnk.service.factory.EventLogFactory;
import hu.hnk.service.tools.BonusPointCalculator;

/**
 * @author Nandi
 *
 */
@Stateless
@Local(CargoService.class)
public class CargoServiceImpl implements CargoService {
	/**
	 * Az osztály loggere.
	 */
	public static final Logger logger = LoggerFactory.getLogger(CargoServiceImpl.class);

	/**
	 * A szállításokat kezelő adathozzáférési objektum.
	 */
	@EJB
	private CargoDao cargoDao;

	/**
	 * A kosarat kezelő adathozzáférési osztály.
	 */
	@EJB
	private CartItemDao cartItemDao;

	/**
	 * A felhasználókat kezelő adathozzáférési osztály.
	 */
	@EJB
	private UserDao userDao;

	/**
	 * Az eseményeket kezelő adathozzáférési osztály.
	 */
	@EJB
	private EventLogDao eventLogDao;

	/**
	 * A korlátozásokat kezelő szolgáltatás.
	 */
	@EJB
	private RestrictionCheckerService restrictionCheckerService;

	@EJB
	private BonusPointCalculator calculator;

	@EJB
	private DiscountService discountService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Cargo saveNewCargo(Cargo cargo, List<CartItem> items)
			throws DailyBuyActionLimitExceeded, CanNotBuyLegendaryBeerYetException {

		if (!restrictionCheckerService.checkIfUserCanBuyMoreBeer(cargo.getUser())) {
			throw new DailyBuyActionLimitExceeded("Daily buy action limit exceeded.");
		}

		if (!legendaryItems(items).isEmpty()
				&& !restrictionCheckerService.checkIfUserCanBuyLegendBeer(cargo.getUser())) {
			throw new CanNotBuyLegendaryBeerYetException("User is not legendary yet.");
		}

		// Elkészítünk egy már a mentett szállítást reprezentáló objektumot.
		Cargo savedCargo;

		// Hozzáadjuk a szállítmányhoz a szállítási költséget.
		cargo.setTotalPrice(cargo.getTotalPrice() + BuyActionRestrictions.getShippingCost());

		savedCargo = saveCargo(cargo, items);
		// a kedvezményeket élesítjük
		Arrays.asList(DiscountType.values())
				.stream()
				.forEach(p -> discountService.validateDiscount(p, savedCargo, LocalDate.now()));
		try {
			cargoDao.update(savedCargo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		// Miután mentettük a szállítást utána töröljük a felhasználó kosarából.
		deleteItemsFromUsersCart(cargo);

		// Levonjuk a felhasználótol a megrendelés árát.
		updateUsersMoneyAfterPayment(cargo);

		// Frissítjük a felhasználó tapasztalatpontjait.
		updateUserExperiencePoints(cargo);

		// Frissítjük a felhasználó bónusz pontjait.
		updateUserBonusPoints(cargo, savedCargo);

		// Készítünk egy eventLog-ot a sikeres vásárlásról.
		createEventLogForBuyAction(cargo);

		return savedCargo;

	}

	private void updateUserBonusPoints(Cargo cargo, Cargo savedCargo) {
		cargo.getUser()
				.setPoints(cargo.getUser()
						.getPoints() + calculator.calculate(savedCargo.getItems()));
		try {
			userDao.update(cargo.getUser());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private void updateUserExperiencePoints(Cargo cargo) {
		cargo.getUser()
				.setExperiencePoints(cargo.getUser()
						.getExperiencePoints() + (cargo.getTotalPrice() / 10));
		try {
			userDao.update(cargo.getUser());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private List<CartItem> legendaryItems(List<CartItem> items) {
		return items.stream()
				.filter(p -> p.getBeer()
						.isLegendary())
				.collect(Collectors.toList());
	}

	private Cargo saveCargo(Cargo cargo, List<CartItem> items) {
		Cargo savedCargo = null;
		try {
			savedCargo = cargoDao.save(cargo);
		} catch (Exception e) {
			logger.warn("Error while trying to save new cargo.");
			logger.error(e.getMessage(), e);
		}
		savedCargo.setItems(items);
		try {
			cargoDao.update(savedCargo);
		} catch (Exception e) {
			logger.warn("Error while trying to update new cargo.");
			logger.error(e.getMessage(), e);
		}
		logger.info("Cargo saved.");
		return savedCargo;
	}

	private void createEventLogForBuyAction(Cargo cargo) {
		try {
			eventLogDao.save(EventLogFactory.createEventLog(EventLogType.Buy, cargo.getUser()));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	private void updateUsersMoneyAfterPayment(Cargo cargo) {
		cargo.getUser()
				.setMoney(cargo.getUser()
						.getMoney() - cargo.getTotalPrice());
		try {
			userDao.update(cargo.getUser());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private void deleteItemsFromUsersCart(Cargo cargo) {
		cargo.getItems()
				.stream()
				.forEach(p -> {
					try {
						cartItemDao.deleteItemLogically(p);
					} catch (Exception e) {
						logger.warn("Exception while trying to remove items from user's cart.");
						logger.error(e.getMessage(), e);
					}
				});
	}

	@Override
	public boolean isThereEnoughMoney(Double totalcost, User user) {
		return totalcost + BuyActionRestrictions.getShippingCost() <= user.getMoney() ? true : false;
	}

	@Override
	public Double countMoneyAfterPayment(Double totalcost, User user) {
		Double result = user.getMoney() - totalcost - BuyActionRestrictions.getShippingCost();
		logger.info("Money after payment:" + result + " for user " + user.getUsername());
		return result < 0 ? null : result;
	}
	
	@Override
	@CoverageIgnore
	public List<Cargo> findByUser(User user) {
		return cargoDao.findByUser(user);
	}

	@Override
	public String countdownTenMinute(Date orderDate) {
		LocalDateTime dateTime = LocalDateTime.ofInstant(orderDate.toInstant(), ZoneId.systemDefault());
		Duration tenMinute = Duration.between(dateTime, LocalDateTime.now());
		return tenMinute.toMinutes() > 9 ? "Csomag kiküldve"
				: String.valueOf((9 - ((int) tenMinute.getSeconds() / 60))) + " perc "
						+ String.valueOf(59 - (tenMinute.getSeconds() % 60)) + " másodperc";
	}
	/**
	 * Beállítja a szállításokat kezelő adathozzáférési objektumát.
	 * 
	 * @param cargoDao
	 *            a beállítandó adathozzáférési osztály.
	 */
	public void setCargoDao(CargoDao cargoDao) {
		this.cargoDao = cargoDao;
	}

	/**
	 * Beállítja a kosárban / szállításban szereplő termékek adathozzáférési
	 * objektumát.
	 * 
	 * @param cartItemDao
	 *            az adathozzáférési objektum.
	 */
	public void setCartItemDao(CartItemDao cartItemDao) {
		this.cartItemDao = cartItemDao;
	}

	/**
	 * Beállítja a felhasználókat kezelő adathozzáféréi objektumot.
	 * 
	 * @param userDao
	 *            az adathozzáféréi objektum.
	 */
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * Beállítja az eseményeket kezelő adathozzáférési objektumot.
	 * 
	 * @param eventLogDao
	 *            az adathozzáférési objektum.
	 */
	public void setEventLogDao(EventLogDao eventLogDao) {
		this.eventLogDao = eventLogDao;
	}

	/**
	 * Beállítja a korlátozásokat kezelő adathozzáférési objektumot.
	 * 
	 * @param restrictionCheckerService
	 *            az adathozzáférési objektum.
	 */
	public void setRestrictionCheckerService(RestrictionCheckerService restrictionCheckerService) {
		this.restrictionCheckerService = restrictionCheckerService;
	}

	public void setCalculator(BonusPointCalculator calculator) {
		this.calculator = calculator;
	}

	public void setDiscountService(DiscountService discountService) {
		this.discountService = discountService;
	}

}
