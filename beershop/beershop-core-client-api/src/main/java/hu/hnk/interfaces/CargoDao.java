package hu.hnk.interfaces;

import java.util.List;

import hu.hnk.beershop.model.Cargo;
import hu.hnk.beershop.model.User;

/**
 * A szállításokat kezelő adathozzáférési osztály interfésze.
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
	 */
	public List<Cargo> findByUser(User user);
}
