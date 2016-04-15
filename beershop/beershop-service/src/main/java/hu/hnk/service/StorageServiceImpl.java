/**
 * 
 */
package hu.hnk.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

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

	

}
