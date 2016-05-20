package hu.hnk.managedbeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.hnk.beershop.exception.NegativeQuantityNumberException;
import hu.hnk.beershop.model.StorageItem;
import hu.hnk.beershop.service.interfaces.StorageService;
import hu.hnk.tool.FacesMessageTool;

/**
 * A raktárt kezelő bean.
 * 
 * @author Nandi
 *
 */
@ManagedBean(name = "storageManager")
@ViewScoped
public class StorageManagerBean implements Serializable {

	/**
	 * Az osztály loggere.
	 */
	public static final Logger logger = LoggerFactory.getLogger(StorageManagerBean.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A raktárt kezelő szolgáltatás.
	 */
	@EJB
	private StorageService storageService;

	/**
	 * A raktárban található termékek listája.
	 */
	private List<StorageItem> storage;

	/**
	 * Inicializáló metódus, a managed bean létrejöttekor.
	 */
	@PostConstruct
	public void init() {
		logger.info("storageManagerBean init");
		try {
			storage = storageService.findAll();
		} catch (Exception e) {
			logger.warn("Could not load storage items.");
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * Visszaadja a raktárban lévő termékek listáját.
	 * 
	 * @return a raktárban lévő termékek listája.
	 */
	public List<StorageItem> getStorage() {
		return storage;
	}

	/**
	 * Beállítja a raktárban lévő termékek listáját.
	 * 
	 * @param storage
	 *            a raktárban lévő termékek listája.
	 */
	public void setStorage(List<StorageItem> storage) {
		this.storage = storage;
	}

	/**
	 * Elmenti az adminisztrátor által végzett módosításokat.
	 */
	public void saveChanges() {
		try {
			storageService.saveAllChanges(storage);
			FacesMessageTool.createInfoMessage("Módosítások sikeresen mentve!");
			logger.info("Storage database updated succesfully.");
		} catch (NegativeQuantityNumberException e) {
			logger.warn(e.getMessage(), e);
			FacesMessageTool.createWarnMessage("Negatív érték nem tárolható!");
		} catch (Exception e) {
			logger.warn("Could not save storage items.");
			logger.error(e.getMessage(), e);
		}

	}

}
