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

import hu.hnk.beershop.exception.CanNotBuyLegendaryBeerYetExceptionException;
import hu.hnk.beershop.exception.DailyBuyActionLimitExceededException;
import hu.hnk.beershop.model.Cargo;
import hu.hnk.beershop.model.CartItem;
import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.discounts.DiscountType;
import hu.hnk.beershop.service.interfaces.CargoService;
import hu.hnk.beershop.service.interfaces.DiscountService;
import hu.hnk.beershop.service.interfaces.RestrictionCheckerService;
import hu.hnk.beershop.service.logfactory.EventLogType;
import hu.hnk.beershop.service.restrictions.BuyActionRestrictions;
import hu.hnk.beershop.service.utils.PaymentMode;
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

	/**
	 * Beállítja a bónusz pont kalkulátort.
	 */
	@EJB
	private BonusPointCalculator calculator;

	/**
	 * Beállítja a kedvezményeket kezelő szolgáltatást.
	 */
	@EJB
	private DiscountService discountService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Cargo saveNewCargo(Cargo cargo, List<CartItem> items)
			throws DailyBuyActionLimitExceededException, CanNotBuyLegendaryBeerYetExceptionException, Exception {

		if (!restrictionCheckerService.checkIfUserCanBuyMoreBeer(cargo.getUser())) {
			throw new DailyBuyActionLimitExceededException("Daily buy action limit exceeded.");
		}

		if (!legendaryItems(items).isEmpty()
				&& !restrictionCheckerService.checkIfUserCanBuyLegendBeer(cargo.getUser())) {
			throw new CanNotBuyLegendaryBeerYetExceptionException("User is not legendary yet.");
		}

		// Elkészítünk egy már a mentett szállítást reprezentáló objektumot.
		Cargo savedCargo;

		// Hozzáadjuk a szállítmányhoz a szállítási költséget.
		cargo.setTotalPrice(cargo.getTotalPrice() + BuyActionRestrictions.getShippingCost());

		savedCargo = saveCargo(cargo, items);
		// a kedvezményeket élesítjük
		logger.info("Date:" + LocalDate.now());
		Arrays.asList(DiscountType.values())
				.stream()
				.forEach(p -> discountService.validateDiscount(p, savedCargo, LocalDate.now()));

		cargoDao.update(savedCargo);
		// Miután mentettük a szállítást utána töröljük a felhasználó kosarából.
		deleteItemsFromUsersCart(cargo);

		// Levonjuk a felhasználótol a megrendelés árát.
		if (PaymentMode.MONEY.getValue()
				.equals(cargo.getPaymentMode()))
			updateUsersMoneyAfterPayment(cargo);
		else if (PaymentMode.BONUSPOINT.getValue()
				.equals(cargo.getPaymentMode()))
			updateBonusPointsAfterPayment(cargo);
		// Frissítjük a felhasználó tapasztalatpontjait.
		updateUserExperiencePoints(cargo);

		// Frissítjük a felhasználó bónusz pontjait.
		updateUserBonusPoints(cargo, savedCargo);

		// Készítünk egy eventLog-ot a sikeres vásárlásról.
		createEventLogForBuyAction(cargo);

		return savedCargo;

	}

	/**
	 * Bónusz pontok frissítése a szállítás mentése után, ha bónusz pontokkal
	 * fizetett.
	 * 
	 * @param cargo
	 *            a készítésben lévő szállítás.
	 */
	private void updateBonusPointsAfterPayment(Cargo cargo) throws Exception {
		cargo.getUser()
				.setPoints(cargo.getUser()
						.getPoints() - cargo.getTotalPrice());

		userDao.update(cargo.getUser());
	}

	/**
	 * A szállítás elkészítése után való bónusz pontok elkönyvelése a
	 * felhasználó számára.
	 * 
	 * @param cargo
	 *            a készítésben lévő szállítás.
	 * @param savedCargo
	 *            a már mentett szállítás.
	 */
	private void updateUserBonusPoints(Cargo cargo, Cargo savedCargo) throws Exception {
		cargo.getUser()
				.setPoints(cargo.getUser()
						.getPoints() + calculator.calculate(savedCargo.getItems()));
		userDao.update(cargo.getUser());

	}

	/**
	 * A felhasználó tapasztalat pontjainak frissítése a szállítás mentése után.
	 * 
	 * @param cargo
	 *            a készítésben lévő szállítás.
	 */
	private void updateUserExperiencePoints(Cargo cargo) throws Exception {
		cargo.getUser()
				.setExperiencePoints(cargo.getUser()
						.getExperiencePoints() + (cargo.getTotalPrice() / 10));
		userDao.update(cargo.getUser());

	}

	/**
	 * Visszaadja a legendás termékek listáját.
	 * 
	 * @param items
	 *            a felhasználó kosarában levő termékek listája.
	 * @return a felhasználó kosarában szereplő legendás termékek listája.
	 */
	private List<CartItem> legendaryItems(List<CartItem> items) {
		return items.stream()
				.filter(p -> p.getBeer()
						.isLegendary())
				.collect(Collectors.toList());
	}

	/**
	 * Új szállítása mentése.
	 * 
	 * @param cargo
	 *            a készítésben lévő szállítás.
	 * @param items
	 *            a szállításhoz hozzácsatolandó termékek listája.
	 * @return a mentett szállítás termékekkel együtt.
	 */
	private Cargo saveCargo(Cargo cargo, List<CartItem> items) throws Exception {
		Cargo savedCargo;
		savedCargo = cargoDao.save(cargo);
		savedCargo.setItems(items);
		cargoDao.update(savedCargo);
		logger.info("Cargo saved.");
		return savedCargo;
	}

	/**
	 * Egy vásárlási esemény létrehozása.
	 * 
	 * @param cargo
	 *            a készítésben lévő szállítás.
	 */
	private void createEventLogForBuyAction(Cargo cargo) throws Exception {

		eventLogDao.save(EventLogFactory.createEventLog(EventLogType.BUY, cargo.getUser()));

	}

	/**
	 * A felhasználó pénzegyenlegének frissítése a készpénzes/átutalásos fizetés
	 * esetén.
	 * 
	 * @param cargo
	 *            a készítésben lévő szállítás.
	 */
	private void updateUsersMoneyAfterPayment(Cargo cargo) throws Exception {
		cargo.getUser()
				.setMoney(cargo.getUser()
						.getMoney() - cargo.getTotalPrice());
		userDao.update(cargo.getUser());
	}

	/**
	 * A szállítás mentése után töröljük a felhasználó kosarából a termékeket.
	 * 
	 * @param cargo
	 *            a készítésben lévő szállítás.
	 */
	private void deleteItemsFromUsersCart(Cargo cargo) throws Exception {
		// try {
		// cargo.getItems()
		// .stream()
		// .forEach(p -> {
		// try {
		// cartItemDao.deleteItemLogically(p);
		// } catch (Exception e) {
		// throwWrapped(e);
		// }
		// });
		// } catch (Exception e) {
		// throw new Exception(e);
		// }
		for (CartItem item : cargo.getItems()) {
			cartItemDao.deleteItemLogically(item);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isThereEnoughMoney(Double totalcost, User user, PaymentMode paymentMode) {
		if (paymentMode.equals(PaymentMode.MONEY))
			return totalcost + BuyActionRestrictions.getShippingCost() <= user.getMoney() ? true : false;
		else if (paymentMode.equals(PaymentMode.BONUSPOINT))
			return totalcost + BuyActionRestrictions.getShippingCost() <= user.getPoints() ? true : false;
		else
			return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double countMoneyAfterPayment(Double totalcost, User user, PaymentMode paymentMode) {
		Double result = 0.0;
		if (paymentMode.equals(PaymentMode.MONEY))
			result = user.getMoney() - totalcost - BuyActionRestrictions.getShippingCost();
		else if (paymentMode.equals(PaymentMode.BONUSPOINT))
			result = user.getPoints() - totalcost - BuyActionRestrictions.getShippingCost();
		logger.info("Money after payment:" + result + " for user " + user.getUsername());
		return result < 0 ? null : result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@CoverageIgnore
	public List<Cargo> findByUser(User user) throws Exception {
		return cargoDao.findByUser(user);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String countdownTenMinutes(Date orderDate) {
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

	/**
	 * Beállítja a bónusz pont kalkulátort.
	 * 
	 * @param calculator
	 *            a bónusz pont kalkulátor.
	 */
	public void setCalculator(BonusPointCalculator calculator) {
		this.calculator = calculator;
	}

	/**
	 * Beállítja a kedvezményeket kezelő szolgáltatást.
	 * 
	 * @param discountService
	 *            a kedvezményeket kezelő szolgáltatás.
	 */
	public void setDiscountService(DiscountService discountService) {
		this.discountService = discountService;
	}

}
