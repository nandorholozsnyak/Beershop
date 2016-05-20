package hu.hnk.interfaces;

import java.util.List;

import hu.hnk.beershop.model.Role;

/**
 * A jogköröket kezelő adathozzáférési osztály megvalósítása.
 * 
 * A {@link Role} entitást kezelhetjük vele.
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
	 * @throws Exception
	 *             adatbázis illetve más nem várt kivétel esetén
	 */
	public Role findByName(String name) throws Exception;

	/**
	 * Visszaadja az adatbázisban szereplő összes jogkört.
	 * 
	 * @return a jogkörök listája.
	 * @throws Exception
	 *             adatbázis illetve más nem várt kivétel esetén
	 */
	public List<Role> findAll() throws Exception;

}
