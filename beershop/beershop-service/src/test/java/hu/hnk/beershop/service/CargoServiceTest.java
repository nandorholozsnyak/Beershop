package hu.hnk.beershop.service;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import hu.hnk.beershop.exception.CanNotBuyLegendaryBeerYetException;
import hu.hnk.beershop.exception.DailyBuyActionLimitExceededException;
import hu.hnk.beershop.model.Beer;
import hu.hnk.beershop.model.Cargo;
import hu.hnk.beershop.model.CartItem;
import hu.hnk.beershop.model.EventLog;
import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.logfactory.EventLogType;
import hu.hnk.beershop.service.restrictions.BuyActionRestrictions;
import hu.hnk.beershop.service.utils.PaymentMode;
import hu.hnk.interfaces.CargoDao;
import hu.hnk.interfaces.CartItemDao;
import hu.hnk.interfaces.EventLogDao;
import hu.hnk.interfaces.UserDao;
import hu.hnk.service.factory.EventLogFactory;
import hu.hnk.service.impl.CargoServiceImpl;
import hu.hnk.service.impl.DiscountServiceImpl;
import hu.hnk.service.impl.RestrictionCheckerServiceImpl;
import hu.hnk.service.impl.UserServiceImpl;
import hu.hnk.service.tools.BonusPointCalculator;

@PrepareForTest({ LocalDate.class })
@RunWith(PowerMockRunner.class)
public class CargoServiceTest {
	private UserDao userDao;
	private CargoServiceImpl cargoServiceImpl;
	private EventLogDao eventLogDao;
	private RestrictionCheckerServiceImpl restrictionChecker;
	private CartItemDao cartItemDao;
	private CargoDao cargoDao;
	private BonusPointCalculator bonusPointCalculator;
	private DiscountServiceImpl discountService;
	private UserServiceImpl userService;
	private User user;

	@Before
	public void setUp() throws Exception {
		// Mock Halmaz
		cargoServiceImpl = Mockito.spy(new CargoServiceImpl());
		restrictionChecker = Mockito.spy(new RestrictionCheckerServiceImpl());
		discountService = Mockito.spy(new DiscountServiceImpl());
		bonusPointCalculator = Mockito.spy(new BonusPointCalculator());
		userService = Mockito.spy(new UserServiceImpl());
		discountService.setUserService(userService);
		eventLogDao = Mockito.spy(EventLogDao.class);
		cartItemDao = Mockito.mock(CartItemDao.class);
		cargoDao = Mockito.mock(CargoDao.class);
		userDao = Mockito.mock(UserDao.class);
		restrictionChecker.setEventLogDao(eventLogDao);
		cargoServiceImpl.setRestrictionCheckerService(restrictionChecker);
		cargoServiceImpl.setCargoDao(cargoDao);
		cargoServiceImpl.setCalculator(bonusPointCalculator);
		cargoServiceImpl.setDiscountService(discountService);
		cargoServiceImpl.setCartItemDao(cartItemDao);
		cargoServiceImpl.setUserDao(userDao);
		cargoServiceImpl.setEventLogDao(eventLogDao);
		// létrehozunk egy amatőr felhasználót, 1000 Ft-al a számlán, 1000
		// bónuszponttal
		user = new User();
		user.setExperiencePoints(1.0);
		user.setMoney(1000.0);
		user.setPoints(1000.0);

	}

	@Test(expected = DailyBuyActionLimitExceededException.class)
	public void testSaveNewCargoShouldThorwDailyBuyActionLimitExceeded() throws Exception {
		List<CartItem> items = new ArrayList<>();
		Cargo cargo = new Cargo();
		cargo.setUser(user);

		List<EventLog> logs = new ArrayList<>();
		// az amatőr rendelési limitnél egyel nagyobbat adunk meg.
		for (int i = 0; i < BuyActionRestrictions.getRestirctedValues()
				.get(0)
				.getRestrictedValue() + 1; i++) {
			EventLog event = EventLogFactory.createEventLog(EventLogType.BUY, user);
			logs.add(event);
		}
		Mockito.when(eventLogDao.findByUserWhereDateIsToday(user))
				.thenReturn(logs);
		cargoServiceImpl.saveNewCargo(cargo, items);
	}

	@Test(expected = CanNotBuyLegendaryBeerYetException.class)
	public void testSaveNewCargoShouldThorwCanNotBuyLegendaryBeerYetException() throws Exception {

		List<CartItem> items = new ArrayList<>();
		Cargo cargo = new Cargo();
		cargo.setUser(user);

		// Legendás sör elkészítése.
		Beer beer = new Beer();
		beer.setLegendary(true);

		CartItem cartItem = new CartItem();
		cartItem.setBeer(beer);
		cartItem.setQuantity(1);
		items.add(cartItem);
		List<EventLog> logs = new ArrayList<>();
		// az amatőr rendelési limitnél egyel nagyobbat adunk meg.
		for (int i = 0; i < BuyActionRestrictions.getRestirctedValues()
				.get(0)
				.getRestrictedValue(); i++) {

			logs.add(EventLogFactory.createEventLog(EventLogType.BUY, user));
		}

		Mockito.when(eventLogDao.findByUser(user))
				.thenReturn(logs);

		cargoServiceImpl.saveNewCargo(cargo, items);
	}

	@Test
	public void testSaveNewCargoShouldUpdateUsersMoneyAfterPayment() throws Exception {

		List<CartItem> items = new ArrayList<>();
		Cargo cargo = new Cargo();
		cargo.setUser(user);

		// Sör elkészítése.
		Beer beer = Beer.builder()
				.alcoholLevel(5.0)
				.capacity(0.5)
				.discountAmount(0)
				.price(100.0)
				.build();

		CartItem cartItem = new CartItem();
		cartItem.setBeer(beer);
		cartItem.setQuantity(1);
		items.add(cartItem);
		// Ára 100 Ft;
		cargo.setItems(items);
		cargo.setPaymentMode("money");
		cargo.setTotalPrice(100.0);
		List<EventLog> logs = new ArrayList<>();
		// az amatőr rendelési limitnél egyel nagyobbat adunk meg.
		for (int i = 0; i < BuyActionRestrictions.getRestirctedValues()
				.get(0)
				.getRestrictedValue(); i++) {
			logs.add(EventLogFactory.createEventLog(EventLogType.BUY, user));
		}

		Mockito.when(eventLogDao.findByUser(user))
				.thenReturn(logs);

		Mockito.when(cargoDao.save(cargo))
				.thenReturn(cargo);

		cargoServiceImpl.saveNewCargo(cargo, items);
		if (LocalDate.now()
				.getDayOfWeek()
				.equals(DayOfWeek.SATURDAY)) {
			Assert.assertEquals(1000 - cargo.getCargoTotalPrice(), cargo.getUser()
					.getMoney(), 0.0);
		} else {
			Assert.assertEquals(1000 - BuyActionRestrictions.getShippingCost() - cargo.getCargoTotalPrice(),
					cargo.getUser()
							.getMoney(),
					0.0);
		}

	}

	@Test
	public void testSaveNewCargoPayWithBonusPoints() throws Exception {
		List<CartItem> items = new ArrayList<>();
		Cargo cargo = new Cargo();

		// Sör elkészítése.
		Beer beer = Beer.builder()
				.alcoholLevel(5.0)
				.capacity(0.5)
				.discountAmount(0)
				.price(100.0)
				.build();

		CartItem cartItem = CartItem.builder()
				.beer(beer)
				.quantity(1)
				.build();
		items.add(cartItem);
		// Ára 100 Ft;
		cargo = Cargo.builder()
				.items(items)
				.totalPrice(100.0)
				.paymentMode(PaymentMode.BONUSPOINT.getValue())
				.user(user)
				.build();
		List<EventLog> logs = new ArrayList<>();
		// az amatőr rendelési limitnél egyel nagyobbat adunk meg.
		for (int i = 0; i < BuyActionRestrictions.getRestirctedValues()
				.get(0)
				.getRestrictedValue(); i++) {
			logs.add(EventLogFactory.createEventLog(EventLogType.BUY, user));
		}

		Mockito.when(eventLogDao.findByUser(user))
				.thenReturn(logs);

		Mockito.when(cargoDao.save(cargo))
				.thenReturn(cargo);

		// szombati napra mockolunk
		LocalDate date = LocalDate.of(2016, 05, 07);
		PowerMockito.mockStatic(LocalDate.class);

		PowerMockito.when(LocalDate.now())
				.thenReturn(date);

		cargoServiceImpl.saveNewCargo(cargo, items);
		Assert.assertEquals(
				1000 - BuyActionRestrictions.getShippingCost() - 100 + bonusPointCalculator.calculate(cargo.getItems()),
				cargo.getUser()
						.getPoints(),
				0.0);

	}

	@Test
	public void testSaveNewCargoIfUserHasEnoughMoney() throws Exception {
		List<CartItem> items = new ArrayList<>();
		Cargo cargo = new Cargo();
		cargo.setUser(user);

		// Sör elkészítése.
		Beer beer = Beer.builder()
				.alcoholLevel(5.0)
				.capacity(0.5)
				.discountAmount(0)
				.price(100.0)
				.build();

		CartItem cartItem = new CartItem();
		cartItem.setBeer(beer);
		cartItem.setQuantity(1);
		items.add(cartItem);
		// Ára 100 Ft;
		cargo.setItems(items);
		PaymentMode paymentMode = PaymentMode.MONEY;
		cargo.setPaymentMode(paymentMode.getValue());
		Assert.assertTrue(cargoServiceImpl.isThereEnoughMoney(cargo.getCargoTotalPrice(), user, paymentMode));

	}

	@Test
	public void testSaveNewCargoIfUserHasNotEnoughMoney() throws Exception {
		User user = new User();
		// Amatőr lesz a felhasználónk.
		user.setExperiencePoints(0.0);
		user.setMoney(0.0);
		user.setPoints(0.0);
		List<CartItem> items = new ArrayList<>();
		Cargo cargo = new Cargo();
		cargo.setUser(user);

		// Sör elkészítése.
		Beer beer = Beer.builder()
				.alcoholLevel(5.0)
				.capacity(0.5)
				.discountAmount(0)
				.price(100.0)
				.build();

		CartItem cartItem = new CartItem();
		cartItem.setBeer(beer);
		cartItem.setQuantity(1);
		items.add(cartItem);
		// Ára 100 Ft;
		cargo.setItems(items);
		PaymentMode paymentMode = PaymentMode.MONEY;
		cargo.setPaymentMode(paymentMode.getValue());
		Assert.assertFalse(cargoServiceImpl.isThereEnoughMoney(cargo.getCargoTotalPrice(), user, paymentMode));

	}

	@Test
	public void testSaveNewCargoIfUserHasEnoughPoints() throws Exception {
		User user = new User();
		// Amatőr lesz a felhasználónk.
		user.setExperiencePoints(0.0);
		user.setMoney(100.0);
		user.setPoints(1000.0);
		List<CartItem> items = new ArrayList<>();
		Cargo cargo = new Cargo();
		cargo.setUser(user);

		// Sör elkészítése.
		Beer beer = Beer.builder()
				.alcoholLevel(5.0)
				.capacity(0.5)
				.discountAmount(0)
				.price(100.0)
				.build();

		CartItem cartItem = new CartItem();
		cartItem.setBeer(beer);
		cartItem.setQuantity(1);
		items.add(cartItem);
		// Ára 100 Ft;
		cargo.setItems(items);
		PaymentMode paymentMode = PaymentMode.BONUSPOINT;
		cargo.setPaymentMode(paymentMode.getValue());
		Assert.assertTrue(cargoServiceImpl.isThereEnoughMoney(cargo.getCargoTotalPrice(), user, paymentMode));

	}

	@Test
	public void testSaveNewCargoIfUserHasNotEnoughPoints() throws Exception {
		User user = new User();

		// Amatőr lesz a felhasználónk.
		user.setExperiencePoints(0.0);
		user.setMoney(100.0);
		user.setPoints(0.0);
		List<CartItem> items = new ArrayList<>();
		Cargo cargo = new Cargo();
		cargo.setUser(user);

		// Sör elkészítése.
		Beer beer = Beer.builder()
				.alcoholLevel(5.0)
				.capacity(0.5)
				.discountAmount(0)
				.price(100.0)
				.build();

		CartItem cartItem = new CartItem();
		cartItem.setBeer(beer);
		cartItem.setQuantity(1);
		items.add(cartItem);
		// Ára 100 Ft;
		PaymentMode paymentMode = PaymentMode.BONUSPOINT;
		cargo.setPaymentMode(paymentMode.getValue());
		cargo.setItems(items);

		Assert.assertFalse(cargoServiceImpl.isThereEnoughMoney(cargo.getCargoTotalPrice(), user, paymentMode));
	}

	@Test
	public void testCountdownTenMinutePackageAlreadySent() {
		LocalDateTime orderDate = LocalDateTime.now();
		String result = cargoServiceImpl.countdownTenMinutes(Date.from(orderDate.minusMinutes(10)
				.toInstant(ZoneOffset.of("+2"))));
		Assert.assertEquals("Csomag kiküldve", result);
	}

	@Test
	public void testCountdownTenMinutePackageHasNotSentYet() {
		LocalDateTime orderDate = LocalDateTime.now();
		String result = cargoServiceImpl.countdownTenMinutes(Date.from(orderDate.minusMinutes(9)
				.toInstant(ZoneOffset.of("+2"))));
		Assert.assertEquals("0 perc 59 másodperc", result);
	}
}
