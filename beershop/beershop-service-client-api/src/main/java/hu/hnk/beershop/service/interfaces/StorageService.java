package hu.hnk.beershop.service.interfaces;

import java.util.List;

import hu.hnk.beershop.exception.NegativeQuantityNumber;
import hu.hnk.beershop.exception.StorageItemQuantityExceeded;
import hu.hnk.beershop.model.Beer;
import hu.hnk.beershop.model.StorageItem;

/**
 * @author Nandi
 *
 */
public interface StorageService {
	
	/**
	 * A raktár információit lekérdezõ metódus.
	 * 
	 * @return a raktár információi.
	 */
	public List<StorageItem> findAll();
	
	/**
	 * Meghívja a raktár adathozzáférési objektumának a mentését, amely a listában szereplõ összes módosítást menti.
	 * @param storage a raktárban szereplõ elemek listája.
	 * @throws NegativeQuantityNumber ha valamelyik elem darabszáma negatív.
	 */
	public void saveAllChanges(List<StorageItem> storage) throws NegativeQuantityNumber;
	
	public void checkStorageItemQuantityLimit(List<StorageItem> storage, Beer beer, Integer quantity) throws StorageItemQuantityExceeded, NegativeQuantityNumber;
	
}
