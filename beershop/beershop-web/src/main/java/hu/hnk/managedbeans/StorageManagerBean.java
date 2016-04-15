package hu.hnk.managedbeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import hu.hnk.beershop.model.Storage;
import hu.hnk.beershop.service.interfaces.StorageService;

/**
 * @author Nandi
 *
 */
@ManagedBean(name = "storageManager")
@ViewScoped
public class StorageManagerBean implements Serializable {
	/**
	 * A söröket kezelõ szolgáltatás.
	 */
	@EJB
	private StorageService storageService;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Storage> storage;
	
	@PostConstruct
	public void init() {
		storage = storageService.findAll();
	}

	public List<Storage> getStorage() {
		return storage;
	}

	public void setStorage(List<Storage> storage) {
		this.storage = storage;
	}

}
