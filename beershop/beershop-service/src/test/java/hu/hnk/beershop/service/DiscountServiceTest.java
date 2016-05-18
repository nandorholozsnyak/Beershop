package hu.hnk.beershop.service;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import hu.hnk.beershop.model.Beer;
import hu.hnk.beershop.model.Cargo;
import hu.hnk.beershop.model.CartItem;
import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.discounts.DiscountType;
import hu.hnk.beershop.service.restrictions.BuyActionRestrictions;
import hu.hnk.service.impl.DiscountServiceImpl;
import hu.hnk.service.impl.UserServiceImpl;

public class DiscountServiceTest {

	DiscountServiceImpl discountService;
	UserServiceImpl userServiceImpl;

	@Before
	public void setUp() throws Exception {
		discountService = new DiscountServiceImpl();
		userServiceImpl = Mockito.spy(new UserServiceImpl());
		discountService.setUserService(userServiceImpl);
	}

	@Test
	public void testValidateFreeShipping() {
		Double price = 500.0;
		Integer quantity = 5;

		// Felépítünk egy szállítást.
		Cargo c = Cargo.builder()
				.items(Arrays.asList(CartItem.builder()
						.beer(Beer.builder()
								.price(price)
								.build())
						.quantity(quantity)
						.build()))
				.totalPrice(price * quantity + BuyActionRestrictions.getShippingCost())
				.user(User.builder()
						.experiencePoints(10.0)
						.build())
				.build();

		// 2016 05 07 - szombati nap.
		discountService.validateDiscount(DiscountType.FREESHIPPING, c, LocalDate.of(2016, 5, 7));
		Assert.assertEquals(price * quantity, c.getTotalPrice(), 0.0);

	}

	@Test
	public void testValidateTheCheapestForFree() {
		Double expensive = 500.0;
		Integer quantity = 5;

		CartItem expensiveItem = CartItem.builder()
				.beer(Beer.builder()
						.name("Drága sör")
						.price(expensive)
						.build())
				.quantity(quantity)
				.build();

		CartItem cheapestItem = CartItem.builder()
				.beer(Beer.builder()
						.name("Olcsó sör")
						.price(expensive / 2)
						.build())
				.quantity(quantity)
				.build();

		// Felépítünk egy szállítást.
		Cargo c = Cargo.builder()
				.items(Arrays.asList(expensiveItem, cheapestItem))
				.totalPrice(
						(expensive * quantity) + (expensive / 2 * quantity) + BuyActionRestrictions.getShippingCost())
				.user(User.builder()
						.experiencePoints(5100.0)
						.build())
				.build();

		// 2016 05 05 - csütörtöki nap.
		discountService.validateDiscount(DiscountType.THECHEAPESTFORFREE, c, LocalDate.of(2016, 5, 5));
		Assert.assertEquals((expensive * quantity) + BuyActionRestrictions.getShippingCost(), c.getTotalPrice(), 0.0);
	}

	@Test
	public void testValidateTheCheapestForFreeIfCartConainsOnlyOneItem() {
		Double expensive = 500.0;
		Integer quantity = 5;

		CartItem expensiveItem = CartItem.builder()
				.beer(Beer.builder()
						.name("Drága sör")
						.price(expensive)
						.build())
				.quantity(quantity)
				.build();

		// Felépítünk egy szállítást.
		Cargo c = Cargo.builder()
				.items(Arrays.asList(expensiveItem))
				.totalPrice((expensive * quantity) + BuyActionRestrictions.getShippingCost())
				.user(User.builder()
						.experiencePoints(5100.0)
						.build())
				.build();

		// 2016 05 05 - csütörtöki nap.
		discountService.validateDiscount(DiscountType.THECHEAPESTFORFREE, c, LocalDate.of(2016, 5, 5));
		Assert.assertEquals((expensive * quantity) + BuyActionRestrictions.getShippingCost(), c.getTotalPrice(), 0.0);
	}

	@Test
	public void testValidateTheExtraBonusPoints() {
		Double expensive = 500.0;
		Integer quantity = 5;

		CartItem expensiveItem = CartItem.builder()
				.beer(Beer.builder()
						.name("Drága sör")
						.price(expensive)
						.build())
				.quantity(quantity)
				.build();

		Double userPoints = 5000.0;
		// Felépítünk egy szállítást.
		Cargo c = Cargo.builder()
				.items(Arrays.asList(expensiveItem))
				.totalPrice((expensive * quantity) + BuyActionRestrictions.getShippingCost())
				.user(User.builder()
						.experiencePoints(2501.0)
						.points(userPoints)
						.build())
				.build();

		// 2016 05 04 - szerdai nap.
		discountService.validateDiscount(DiscountType.EXTRABONUSPOINTS, c, LocalDate.of(2016, 5, 4));
		Assert.assertEquals(userPoints + c.getTotalPrice() / 5, c.getUser()
				.getPoints(), 0.0);
	}

	@Test
	public void testValidateFiftyPercentageForAmatuersOnTuesday() {
		Double expensive = 500.0;
		Integer quantity = 5;

		CartItem expensiveItem = CartItem.builder()
				.beer(Beer.builder()
						.name("Drága sör")
						.price(expensive)
						.build())
				.quantity(quantity)
				.build();

		Double userPoints = 5000.0;
		// Felépítünk egy szállítást.
		Cargo c = Cargo.builder()
				.items(Arrays.asList(expensiveItem))
				.totalPrice((expensive * quantity) + BuyActionRestrictions.getShippingCost())
				.user(User.builder()
						.experiencePoints(1.0)
						.points(userPoints)
						.build())
				.build();

		// 2016 05 03 - keddi nap.
		discountService.validateDiscount(DiscountType.FIFTYPERCENTAGE, c, LocalDate.of(2016, 5, 3));
		Assert.assertEquals((expensive * quantity + BuyActionRestrictions.getShippingCost()) / 2, c.getTotalPrice(),
				0.0);
	}

	@Test
	public void testGetAvailableDailyRankBonusesForUser() {
		User user = User.builder()
				.experiencePoints(1.0)
				.build();

		Assert.assertEquals(Arrays.asList(DiscountType.FIFTYPERCENTAGE),
				discountService.getAvailableDailyRankBonusesForUser(user, LocalDate.of(2016, 05, 17)));
		user.setExperiencePoints(2501.0);
		Assert.assertEquals(Arrays.asList(DiscountType.EXTRABONUSPOINTS),
				discountService.getAvailableDailyRankBonusesForUser(user, LocalDate.of(2016, 05, 18)));
		user.setExperiencePoints(5501.0);
		Assert.assertEquals(Arrays.asList(DiscountType.THECHEAPESTFORFREE),
				discountService.getAvailableDailyRankBonusesForUser(user, LocalDate.of(2016, 05, 19)));

	}

}
