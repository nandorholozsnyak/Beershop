/**
 * 
 */
package hu.hnk.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import hu.hnk.beershop.exception.NegativeCountNumber;
import hu.hnk.beershop.model.Storage;
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
	 * A raktárt kezelõ adathozzáférési objektum.
	 */
	@EJB
	private StorageDao storageDao;

	@Override
	public List<Storage> findAll() {
		return storageDao.findAll();
	}

	@Override
	public void saveAllChanges(List<Storage> storage) throws NegativeCountNumber {
		if(storage.stream().filter(p -> p.getQuantity() < 0).collect(Collectors.toList()).isEmpty()) {
			storageDao.saveAllChanges(storage);
		} else {
			throw new NegativeCountNumber("Negative number can't be stored in the database as count number!");
		}
		
	}

}
