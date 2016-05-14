package hu.hnk.interfaces;

import java.util.List;

import hu.hnk.beershop.model.Role;

/**
 * A jogkörök adatbáziskezelését leíró interfész.
 * 
 * @author Nandi
 *
 */
public interface RoleDao extends BaseDao<Role> {

	/**
	 * Jogkör keresése név alapján.
	 * 
	 * @param name
	 *            a keresendő jogkör neve.
	 * @return a talált jogkör.
	 */
	public Role findByName(String name);

	/**
	 * Visszaadja az adatbázisban szereplő összes jogkört.
	 * 
	 * @return a jogkörök listája.
	 */
	public List<Role> findAll();

}
