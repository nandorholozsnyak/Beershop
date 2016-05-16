package hu.hnk.beershop.service.interfaces;

import java.util.List;

import hu.hnk.beershop.exception.NegativeQuantityNumberException;
import hu.hnk.beershop.exception.StorageItemQuantityExceededException;
import hu.hnk.beershop.model.Beer;
import hu.hnk.beershop.model.StorageItem;

/**
 * A raktárak kezelő szolgáltatás.
 * 
 * @author Nandi
 *
 */
public interface StorageService {

	/**
	 * A raktár információit lekérdező metódus.
	 * 
	 * @return a raktár információi.
	 */
	public List<StorageItem> findAll();

	/**
	 * Meghívja a raktár adathozzáférési objektumának a mentését, amely a
	 * listában szereplő összes módosítást menti.
	 * 
	 * @param storage
	 *            a raktárban szereplő elemek listája.
	 * @throws NegativeQuantityNumberException
	 *             ha valamelyik elem darabszáma negatív.
	 */
	public void saveAllChanges(List<StorageItem> storage) throws NegativeQuantityNumberException;

	/**
	 * Ellenőrzi egy termék raktárbeli előrhetőségét.
	 * 
	 * Különböző kivételek váltódnak ki ha bizonyos értékek sérülnek.
	 * 
	 * @param storage
	 *            a raktrában szereplő termékek listája.
	 * @param beer
	 *            az ellenőrizendő sör, azaz a raktárban levő hivatkozása.
	 * @param quantity
	 *            az ellenőrizendő mennyiség.
	 * @throws StorageItemQuantityExceededException
	 *             akkor dobjuk ha a termékből többet szeretnének kérni mint
	 *             amennyi rendelkezésre áll a raktárban.
	 * @throws NegativeQuantityNumberException
	 *             akkor dobjuk ha negatív számú terméket szeretnének kérni az
	 *             adott sörből.
	 */
	public void checkStorageItemQuantityLimit(List<StorageItem> storage, Beer beer, Integer quantity)
			throws StorageItemQuantityExceededException, NegativeQuantityNumberException;

}
