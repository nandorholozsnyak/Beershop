package hu.hnk.beershop.service;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import hu.hnk.beershop.model.Beer;
import hu.hnk.beershop.model.Cart;
import hu.hnk.beershop.model.CartItem;
import hu.hnk.beershop.model.StorageItem;
import hu.hnk.beershop.model.User;
import hu.hnk.interfaces.CartDao;
import hu.hnk.interfaces.CartItemDao;
import hu.hnk.interfaces.StorageDao;
import hu.hnk.service.CartServiceImpl;
import junit.framework.Assert;

public class CartServiceTest {

	private CartServiceImpl cartService;

	private CartDao cartDao;

	private StorageDao storageDao;

	private CartItemDao cartItemDao;

	@Before
	public void bootContainer() throws Exception {
		cartService = Mockito.spy(new CartServiceImpl());
		cartDao = Mockito.mock(CartDao.class);
		cartItemDao = Mockito.mock(CartItemDao.class);
		storageDao = Mockito.mock(StorageDao.class);
		cartService.setCartDao(cartDao);
		cartService.setStorageDao(storageDao);
		cartService.setCartItemDao(cartItemDao);
	}

	// List<StorageItem> storage, Beer beer, Integer quantity
	@Test
	public void testCountMoneyAfterPayment() {
		User user = new User();
		user.setMoney(2500.0);
		user.setPoints(500.0);
		Assert.assertEquals(1500.0, cartService.countMoneyAfterPayment(user, (double) 1000, "money"));
		Assert.assertEquals(400.0, cartService.countMoneyAfterPayment(user, (double) 100, "bonusPoint"));
		// Lehetetlen eset.
		Assert.assertEquals(0.0, cartService.countMoneyAfterPayment(user, (double) 100, "invalid"));
	}

	@Test
	public void testCountBonusPoints() {
		// Képlet: (Mennyiség*Ár) / 100 + Alkoholtartalom + kapacitás +
		// kedvezmény
		List<CartItem> cartItems = new ArrayList<>();
		CartItem cartItem = new CartItem();
		Beer beer = new Beer();
		beer.setPrice(200.0);
		beer.setAlcoholLevel(10.0);
		beer.setCapacity(0.5);
		beer.setDiscountAmount(0);
		cartItem.setBeer(beer);
		cartItem.setQuantity(5);
		// (5*200) / 100 + 10 + 0.5 + 0
		cartItems.add(cartItem);
		Assert.assertEquals(20.5, cartService.countBonusPoints(cartItems));
	}

	@Test
	public void testCountTotalCost() {
		List<CartItem> cartItems = new ArrayList<>();
		CartItem cartItem = new CartItem();
		Beer beer = new Beer();
		beer.setPrice(200.0);
		beer.setDiscountAmount(0);
		cartItem.setBeer(beer);
		cartItem.setQuantity(5);
		cartItems.add(cartItem);

		CartItem cartItem2 = new CartItem();
		Beer beer2 = new Beer();
		beer2.setPrice(500.0);
		beer2.setDiscountAmount(50);
		cartItem2.setBeer(beer2);
		cartItem2.setQuantity(10);
		cartItems.add(cartItem2);

		Assert.assertEquals(3500.0, cartService.countTotalCost(cartItems));
	}

	@Test
	public void testDeleteItemFromCart() throws Exception {
		Beer beer = new Beer();
		beer.setName("TestBeer");
		CartItem item = new CartItem();
		item.setBeer(beer);
		item.setQuantity(10);
		Mockito.doNothing()
				.when(cartItemDao)
				.deleteItemLogically(item);
		StorageItem stItem = new StorageItem();
		stItem.setBeer(beer);
		stItem.setQuantity(10);
		Mockito.when(storageDao.findByBeer(beer))
				.thenReturn(stItem);
		cartService.deletItemFromCart(item);
		Assert.assertEquals(new Integer(20), stItem.getQuantity());
	}

	@Test(expected = Exception.class)
	public void testDeleteItemFromCartShouldThrowException() throws Exception {
		Beer beer = new Beer();
		beer.setName("TestBeer");
		CartItem item = new CartItem();
		item.setBeer(beer);
		item.setQuantity(10);
		Mockito.doThrow(Exception.class)
				.when(cartItemDao)
				.deleteItemLogically(item);
		StorageItem stItem = new StorageItem();
		stItem.setBeer(beer);
		stItem.setQuantity(10);
		Mockito.when(storageDao.findByBeer(beer))
				.thenReturn(stItem);
		cartService.deletItemFromCart(item);
		fail("Method should have thrown Exception.");
	}

	@Test
	public void testSaveItemsToCartEmptyCart() {
		// Létrehozunk egy kosarat.
		Cart cart = new Cart();
		// Most egy teljesen üres kosárral kezdünk.
		cart.setItems(new ArrayList<>());

		// Definiáljuk azokat a söröket amik majd belekerülnek a kosárba.
		Beer firstBeer = new Beer();
		firstBeer.setName("First Beer");
		Beer secondBeer = new Beer();
		secondBeer.setName("Second Beer");

		// Belerakjuk a söröket.
		Map<Beer, Integer> beersToCart = new HashMap<>();
		beersToCart.put(firstBeer, 10);
		beersToCart.put(secondBeer, 5);

		// Definiáljuk a raktárban lévõ söröket.
		StorageItem firstSi = new StorageItem();
		StorageItem secondSi = new StorageItem();

		firstSi.setBeer(firstBeer);
		firstSi.setQuantity(15);

		secondSi.setBeer(secondBeer);
		secondSi.setQuantity(10);

		// Mock!
		List<StorageItem> stList = Arrays.asList(firstSi, secondSi);
		Mockito.when(storageDao.findAll())
				.thenReturn(stList);

		// A visszavárt sörök, amelyek mint kosár tárgyak helyezõdnek a kosárba.
		CartItem firstExpected = new CartItem();
		firstExpected.setBeer(firstBeer);
		firstExpected.setQuantity(10);

		CartItem secondExpected = new CartItem();
		secondExpected.setBeer(secondBeer);
		secondExpected.setQuantity(5);

		// Szükséges a dátum set-elése miatt.
		cartService.saveItemsToCart(beersToCart, cart);
		for (CartItem cItem : cart.getItems()) {
			cItem.setAddedToCart(null);
		}

		Assert.assertEquals(Arrays.asList(secondExpected, firstExpected), cart.getItems());

	}

	@Test
	public void testSaveItemsToCartWithOneItemInIt() {
		// Létrehozunk egy kosarat.
		Cart cart = new Cart();

		// Definiáljuk azokat a söröket amik majd belekerülnek a kosárba.
		Beer firstBeer = new Beer();
		firstBeer.setName("First Beer");
		Beer secondBeer = new Beer();
		secondBeer.setName("Second Beer");

		// Belerakjuk az elsõ sört, 10 darabszámmal.
		Map<Beer, Integer> beersToCart = new HashMap<>();
		beersToCart.put(firstBeer, 10);

		// Létrehozzuk a raktárban való megfelelójüket.
		StorageItem firstSi = new StorageItem();
		StorageItem secondSi = new StorageItem();
		firstSi.setBeer(firstBeer);
		firstSi.setQuantity(15);

		secondSi.setBeer(secondBeer);
		secondSi.setQuantity(10);

		// Mockoljuk az eredményt amikor meghívódik a storageDao.findAll()
		// metódusa.
		List<StorageItem> stList = new LinkedList(Arrays.asList(firstSi, secondSi));
		Mockito.when(storageDao.findAll())
				.thenReturn(stList);

		// A legelsõ elemet várjuk ami benne van a mi kosarunkban.
		// Már szerepel egy ilyen termék, a 10 darabszámmal a kosárban most azt
		// várjuk hogy ezután 20 darab lesz belõle.
		CartItem firstExpected = new CartItem();
		firstExpected.setBeer(firstBeer);
		firstExpected.setActive(true);
		firstExpected.setQuantity(10);

		// Most már egy olyan kosarunk van amelyben szerepel egy termék.
		cart.setItems(new LinkedList(Arrays.asList(firstExpected)));

		// Szükséges a dátum set-elése miatt.
		cartService.saveItemsToCart(beersToCart, cart);
		for (CartItem cItem : cart.getItems()) {
			cItem.setAddedToCart(null);
		}

		// Azt teszteljük hogy vajon a kosarunkban a termék megegyezik-e mint
		// amit mi várunk.
		// System.out.println("Exp->" + Arrays.asList(firstExpected));
		// System.out.println("Cart->" + cart.getItems());
		Assert.assertEquals(Arrays.asList(firstExpected), cart.getItems());
		Assert.assertEquals(firstExpected.getQuantity(), cart.getItems()
				.get(0)
				.getQuantity());

	}

	// Olyan sört szeretnénk kérni ami nem is létezik a raktárban.
	// Lehetetlen eset.
	@Test
	public void testSaveItemsToCartWithUnmanagedBeer() {
		// Létrehozunk egy kosarat.
		Cart cart = new Cart();

		// Definiáljuk azokat a söröket amik majd belekerülnek a kosárba.
		Beer firstBeer = new Beer();
		firstBeer.setName("First Beer");
		Beer secondBeer = new Beer();
		secondBeer.setName("Second Beer");

		// Belerakjuk az elsõ sört, 10 darabszámmal.
		Map<Beer, Integer> beersToCart = new HashMap<>();
		beersToCart.put(firstBeer, 10);

		// Létrehozzuk a raktárban való megfelelójüket.
		StorageItem firstSi = new StorageItem();
		StorageItem secondSi = new StorageItem();
		firstSi.setBeer(firstBeer);
		firstSi.setQuantity(15);

		secondSi.setBeer(secondBeer);
		secondSi.setQuantity(10);

		// Mockoljuk az eredményt amikor meghívódik a storageDao.findAll()
		// metódusa.
		// Itt a trükk, a második sör már nem szerepel az adatbázisban.
		List<StorageItem> stList = new LinkedList(Arrays.asList(secondSi));
		Mockito.when(storageDao.findAll())
				.thenReturn(stList);

		// A legelsõ elemet várjuk ami benne van a mi kosarunkban.
		// Már szerepel egy ilyen termék, a 10 darabszámmal a kosárban most azt
		// várjuk hogy ezután 20 darab lesz belõle.
		CartItem firstExpected = new CartItem();
		firstExpected.setBeer(firstBeer);
		firstExpected.setActive(true);
		firstExpected.setQuantity(10);

		// Most már egy olyan kosarunk van amelyben szerepel egy termék.
		cart.setItems(new ArrayList<>());

		// Szükséges a dátum set-elése miatt.
		cartService.saveItemsToCart(beersToCart, cart);
		for (CartItem cItem : cart.getItems()) {
			cItem.setAddedToCart(null);
		}
		Assert.assertTrue(cart.getItems()
				.isEmpty());
	}

	@After
	public void destroy() {
		// container.close();
	}

}
