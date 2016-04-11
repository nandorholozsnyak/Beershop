package hu.hnk.interfaces;

import hu.hnk.beershop.model.Role;

/**
 * A jogkörök adatbáziskezelését leíró interfész.
 * @author Nandi
 *
 */
public interface RoleDao {
	
	/**
	 * Jogkör keresése név alapján.
	 * @param name a keresendõ jogkör neve.
	 * @return a talált jogkör.
	 */
	public Role findByName(String name);
	
	/**
	 * Jogkör mentése az adatbázisba.
	 * @param role a mentendõ jogkör.
	 * @return a mentett jogkör.
	 */
	public Role save(Role role);
}
