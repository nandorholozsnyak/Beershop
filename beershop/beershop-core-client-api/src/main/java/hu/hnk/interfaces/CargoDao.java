package hu.hnk.interfaces;

import java.util.List;

import hu.hnk.beershop.model.Cargo;
import hu.hnk.beershop.model.User;

/**
 * A szállításokat kezelő adathozzáférési osztály implementációja.
 * 
 * Segítségével a {@link Cargo} entitást tudjuk manipulálni.
 * 
 * @author Nandi
 *
 */
public interface CargoDao extends BaseDao<Cargo> {

	/**
	 * Visszaadja a rendelések/szállítások listáját felhasználó szerint.
	 * 
	 * @param user
	 *            a keresendő szállításokkal rendelkező felhasználó
	 * @return a szállítások listája
	 * @throws Exception
	 *             adatbázis illetve más nem várt kivétel esetén
	 */
	public List<Cargo> findByUser(User user) throws Exception;
}
