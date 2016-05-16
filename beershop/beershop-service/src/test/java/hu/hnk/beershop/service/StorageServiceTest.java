package hu.hnk.beershop.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import hu.hnk.beershop.exception.NegativeQuantityNumberException;
import hu.hnk.beershop.exception.StorageItemQuantityExceededException;
import hu.hnk.beershop.model.Beer;
import hu.hnk.beershop.model.StorageItem;
import hu.hnk.interfaces.StorageDao;
import hu.hnk.service.impl.StorageServiceImpl;


public class StorageServiceTest {

	private StorageServiceImpl storageService;

	private StorageDao storageDao;

	@Before
	public void bootContainer() throws Exception {
		storageService = Mockito.spy(new StorageServiceImpl());
		storageDao = Mockito.mock(StorageDao.class);
		storageService.setStorageDao(storageDao);
	}

	// List<StorageItem> storage, Beer beer, Integer quantity
	@Test(expected = StorageItemQuantityExceededException.class)
	public void testTheckStorageItemQuantityLimitShouldThrowStorageItemQuantityExceeded()
			throws StorageItemQuantityExceededException, NegativeQuantityNumberException {
		Beer beer = new Beer();
		beer.setName("Teszt sör");
		StorageItem item = new StorageItem();
		item.setBeer(beer);
		item.setQuantity(1);
		List<StorageItem> items = new ArrayList<>();
		items.add(item);
		Mockito.doNothing().when(storageDao).saveAllChanges(items);
		storageService.checkStorageItemQuantityLimit(items, beer, 2);
	}

	@Test(expected = NegativeQuantityNumberException.class)
	public void testTheckStorageItemQuantityLimitShouldThrowNegativeQuantityNumber()
			throws StorageItemQuantityExceededException, NegativeQuantityNumberException {
		Beer beer = new Beer();
		beer.setName("Teszt sör");
		StorageItem item = new StorageItem();
		item.setBeer(beer);
		item.setQuantity(1);
		List<StorageItem> items = new ArrayList<>();
		items.add(item);
		Mockito.doNothing().when(storageDao).saveAllChanges(items);
		storageService.checkStorageItemQuantityLimit(items, beer, -1);
	}

	@Test(expected = NegativeQuantityNumberException.class)
	public void testSaveAllChangesShouldThrowNegativeQuantityNumber() throws NegativeQuantityNumberException {
		Beer beer = new Beer();
		beer.setName("Teszt sör");
		StorageItem item = new StorageItem();
		item.setBeer(beer);
		item.setQuantity(-1);
		List<StorageItem> items = new ArrayList<>();
		items.add(item);
		Mockito.doNothing().when(storageDao).saveAllChanges(items);
		storageService.saveAllChanges(items);
	}

	@Test
	public void testSaveAllChangesShouldThrowNothing() throws NegativeQuantityNumberException {
		Beer beer = new Beer();
		beer.setName("Teszt sör");
		beer.setDiscountAmount(0);
		StorageItem item = new StorageItem();
		item.setBeer(beer);
		item.setQuantity(1);
		List<StorageItem> items = new ArrayList<>();
		items.add(item);
		//Mockito.doNothing().when(storageDao).saveAllChanges(items);
		storageService.saveAllChanges(items);
	}

	@After
	public void destroy() {
		// container.close();
	}

}
