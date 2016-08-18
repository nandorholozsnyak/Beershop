package hu.hnk.cucumber;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import hu.hnk.beershop.model.Beer;
import hu.hnk.beershop.model.Cargo;
import hu.hnk.beershop.model.CartItem;
import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.discounts.DiscountType;
import hu.hnk.service.impl.DiscountServiceImpl;
import hu.hnk.service.impl.UserServiceImpl;

public class DiscountSteps {
	private User user;
	private Cargo cargo;
	private DiscountServiceImpl discountService;
	private UserServiceImpl userService;

	@Given("^There is a user$")
	public void There_is_a_user() {
		user = User.builder().username("TestUser").build();
	}

	@Given("^a cargo with items$")
	public void a_cargo_with_items() throws Throwable {
		List<CartItem> items = new ArrayList<>();
		Beer beer = Beer.builder().name("TestBeer").price(100.0).discountAmount(0).build();
		CartItem cartItem = CartItem.builder().beer(beer).quantity(5).active(true).build();
		items.add(cartItem);
		// 500 HUF

		cargo = Cargo.builder().user(user).items(items).totalPrice(500.0).build();
	}

	@When("^validate discounts on saturday$")
	public void validate_discounts_on_saturday() throws Throwable {
		userService = new UserServiceImpl();
		discountService = new DiscountServiceImpl();
		discountService.setUserService(userService);
		discountService.validateDiscount(DiscountType.FREESHIPPING, cargo, LocalDate.of(2016, 8, 20));

	}

	@Then("^shipping cost should be free$")
	public void shipping_cost_should_be_free() throws Throwable {
		Assert.assertEquals(500.0, cargo.getCargoTotalPrice(), 0.0);
	}

}
