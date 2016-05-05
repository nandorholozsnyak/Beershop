/**
 * 
 */
package hu.hnk.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import hu.hnk.beershop.exception.NegativeQuantityNumber;
import hu.hnk.beershop.exception.StorageItemQuantityExceeded;
import hu.hnk.beershop.model.Beer;
import hu.hnk.beershop.model.StorageItem;
import hu.hnk.beershop.service.interfaces.StorageService;
import hu.hnk.interfaces.StorageDao;


/**
 * @author Nandi
 *
 */
@Stateless
@Local(StorageService.class)
public class StorageServiceImpl implements StorageService {

	/**
	 * Az osztály loggere.
	 */
	public static final Logger logger = Logger.getLogger(StorageServiceImpl.class);

	/**
	 * A raktárt kezelő adathozzáférési objektum.
	 */
	@EJB
	private StorageDao storageDao;

	@Override
	public List<StorageItem> findAll() {
		return storageDao.findAll();
	}

	@Override
	public void saveAllChanges(List<StorageItem> storage) throws NegativeQuantityNumber {
		if (storage.stream()
				.filter(p -> p.getQuantity() < 0)
				.collect(Collectors.toList())
				.isEmpty()
				&& storage.stream()
						.filter(p -> p.getBeer()
								.getDiscountAmount() < 0)
						.collect(Collectors.toList())
						.isEmpty()) {
			storageDao.saveAllChanges(storage);
		} else {
			throw new NegativeQuantityNumber("Negative number can't be stored in the storage table!");
		}
		logger.info("Items saved to the storage.");

	}

	@Override
	public void checkStorageItemQuantityLimit(List<StorageItem> storage, Beer beer, Integer quantity)
			throws StorageItemQuantityExceeded, NegativeQuantityNumber {
		logger.info("Trying to modify item quantity.");
		List<StorageItem> exceededList = storage.stream()
				.filter(p -> p.getBeer()
						.equals(beer))
				.filter(p -> quantity > p.getQuantity())
				.collect(Collectors.toList());

		if (!exceededList.isEmpty()) {
			throw new StorageItemQuantityExceeded("The asked quantity is bigger than the storage quantity given.");
		}

		if (quantity < 0) {
			throw new NegativeQuantityNumber("Can not take negative quantity to cart.");
		}

	}

	/**
	 * @param storageDao
	 *            the storageDao to set
	 */
	public void setStorageDao(StorageDao storageDao) {
		this.storageDao = storageDao;
	}

}
