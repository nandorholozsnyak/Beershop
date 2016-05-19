/**
 * 
 */
package hu.hnk.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.hnk.beershop.exception.NegativeQuantityNumberException;
import hu.hnk.beershop.exception.StorageItemQuantityExceededException;
import hu.hnk.beershop.model.Beer;
import hu.hnk.beershop.model.StorageItem;
import hu.hnk.beershop.service.interfaces.StorageService;
import hu.hnk.interfaces.StorageDao;
import hu.hnk.service.cobertura.annotation.CoverageIgnore;

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
	public static final Logger logger = LoggerFactory.getLogger(StorageServiceImpl.class);

	/**
	 * A raktárt kezelő adathozzáférési objektum.
	 */
	@EJB
	private StorageDao storageDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	@CoverageIgnore
	public List<StorageItem> findAll() {
		return storageDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveAllChanges(List<StorageItem> storage) throws NegativeQuantityNumberException {
		if (isNothingLessThanZero(storage)) {
			storageDao.saveAllChanges(storage);
		} else {
			throw new NegativeQuantityNumberException("Negative number can't be stored in the storage table!");
		}
		logger.info("Items saved to the storage.");

	}

	private boolean isNothingLessThanZero(List<StorageItem> storage) {
		return storage.stream()
				.filter(p -> p.getQuantity() < 0)
				.collect(Collectors.toList())
				.isEmpty()
				&& storage.stream()
						.filter(p -> p.getBeer()
								.getDiscountAmount() < 0)
						.collect(Collectors.toList())
						.isEmpty()
				&& storage.stream()
						.filter(p -> p.getBeer()
								.getPrice() < 0)
						.collect(Collectors.toList())
						.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void checkStorageItemQuantityLimit(List<StorageItem> storage, Beer beer, Integer quantity)
			throws StorageItemQuantityExceededException, NegativeQuantityNumberException {
		logger.info("Trying to modify item quantity.");
		List<StorageItem> exceededList = storage.stream()
				.filter(p -> p.getBeer()
						.equals(beer))
				.filter(p -> quantity > p.getQuantity())
				.collect(Collectors.toList());

		if (!exceededList.isEmpty()) {
			throw new StorageItemQuantityExceededException(
					"The asked quantity is bigger than the storage quantity given.");
		}

		if (quantity < 0) {
			throw new NegativeQuantityNumberException("Can not take negative quantity to cart.");
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
