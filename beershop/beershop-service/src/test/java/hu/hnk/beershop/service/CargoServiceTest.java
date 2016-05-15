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
import org.mockito.Mockito;

import hu.hnk.beershop.exception.CanNotBuyLegendaryBeerYetException;
import hu.hnk.beershop.exception.DailyBuyActionLimitExceeded;
import hu.hnk.beershop.model.Beer;
import hu.hnk.beershop.model.Cargo;
import hu.hnk.beershop.model.CartItem;
import hu.hnk.beershop.model.EventLog;
import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.logfactory.EventLogType;
import hu.hnk.beershop.service.tools.BuyActionRestrictions;
import hu.hnk.interfaces.CargoDao;
import hu.hnk.interfaces.CartItemDao;
import hu.hnk.interfaces.EventLogDao;
import hu.hnk.service.factory.EventLogFactory;
import hu.hnk.service.impl.CargoServiceImpl;
import hu.hnk.service.impl.DiscountServiceImpl;
import hu.hnk.service.impl.RestrictionCheckerServiceImpl;
import hu.hnk.service.impl.UserServiceImpl;
import hu.hnk.service.tools.BonusPointCalculator;

public class CargoServiceTest {

	private CargoServiceImpl cargoServiceImpl;
	private EventLogDao eventLogDao;
	private RestrictionCheckerServiceImpl res;
	private CartItemDao cartItemDao;
	private CargoDao cargoDao;
	private BonusPointCalculator bonusPointCalculator;
	private DiscountServiceImpl discountService;
	private UserServiceImpl userService;

	@Before
	public void setUp() throws Exception {
		cargoServiceImpl = Mockito.spy(new CargoServiceImpl());
		res = Mockito.spy(new RestrictionCheckerServiceImpl());
		discountService = Mockito.spy(new DiscountServiceImpl());
		bonusPointCalculator = Mockito.spy(new BonusPointCalculator());
		userService = Mockito.spy(new UserServiceImpl());
		discountService.setUserService(userService);
		eventLogDao = Mockito.mock(EventLogDao.class);
		cartItemDao = Mockito.mock(CartItemDao.class);
		cargoDao = Mockito.mock(CargoDao.class);
		cargoServiceImpl.setRestrictionCheckerService(res);
		res.setEventLogDao(eventLogDao);
		cargoServiceImpl.setCargoDao(cargoDao);
		cargoServiceImpl.setCalculator(bonusPointCalculator);
		cargoServiceImpl.setDiscountService(discountService);
	}

	@Test(expected = DailyBuyActionLimitExceeded.class)
	public void testSaveNewCargoShouldThorwDailyBuyActionLimitExceeded()
			throws CanNotBuyLegendaryBeerYetException, DailyBuyActionLimitExceeded {
		User user = new User();
		// Amatőr lesz a felhasználónk.
		user.setExperiencePoints(1.0);
		List<CartItem> items = new ArrayList<>();
		Cargo cargo = new Cargo();
		cargo.setUser(user);

		List<EventLog> logs = new ArrayList<>();
		// az amatőr rendelési limitnél egyel nagyobbat adunk meg.
		for (int i = 0; i < BuyActionRestrictions.getRestirctedValues()
				.get(0)
				.getRestrictedValue() + 1; i++) {
			logs.add(EventLogFactory.createEventLog(EventLogType.BUY, user));
		}
		Mockito.when(eventLogDao.findByUserWhereDateIsToday(user))
				.thenReturn(logs);
		cargoServiceImpl.saveNewCargo(cargo, items);
	}

	@Test(expected = CanNotBuyLegendaryBeerYetException.class)
	public void testSaveNewCargoShouldThorwCanNotBuyLegendaryBeerYetException()
			throws CanNotBuyLegendaryBeerYetException, DailyBuyActionLimitExceeded {
		User user = new User();
		// Amatőr lesz a felhasználónk.
		user.setExperiencePoints(1.0);
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
		User user = new User();
		// Amatőr lesz a felhasználónk.
		user.setExperiencePoints(2501.0);
		user.setMoney(1000.0);
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
		User user = new User();
		// Amatőr lesz a felhasználónk.
		user.setExperiencePoints(0.0);
		user.setMoney(1000.0);
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
		cargo.setTotalPrice(100.0);
		cargo.setPaymentMode("bonusPoint");
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
			Assert.assertEquals(1000 - 100, cargo.getUser()
					.getPoints(), 0.0);
		} else {
			Assert.assertEquals(
					1000 - BuyActionRestrictions.getShippingCost() - 100
							+ bonusPointCalculator.calculate(cargo.getItems()),
					cargo.getUser()
							.getPoints(),
					0.0);
		}

	}

	@Test
	public void testSaveNewCargoIfUserHasEnoughMoney() throws Exception {
		User user = new User();
		// Amatőr lesz a felhasználónk.
		user.setExperiencePoints(0.0);
		user.setMoney(1000.0);
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
		cargo.setPaymentMode("money");
		Assert.assertTrue(
				cargoServiceImpl.isThereEnoughMoney(cargo.getCargoTotalPrice(), user, cargo.getPaymentMode()));

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
		cargo.setPaymentMode("bonusPoint");
		Assert.assertTrue(
				cargoServiceImpl.isThereEnoughMoney(cargo.getCargoTotalPrice(), user, cargo.getPaymentMode()));

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
