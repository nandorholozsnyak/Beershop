package hu.hnk.dao;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;

import hu.hnk.beershop.model.Role;
import hu.hnk.interfaces.RoleDao;


/**
 * A jogköröket kezelő adathozzáférési osztály megvalósítása.
 * 
 * @author Nandi
 *
 */
@Stateless
@Local(RoleDao.class)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {

	/**
	 * Az osztály konstuktora.
	 */
	public RoleDaoImpl() {
		super(Role.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public Role findByName(String name) {
		TypedQuery<Role> role = entityManager.createNamedQuery("Role.findByName", Role.class);
		role.setParameter("name", name);
		try {
			return role.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

}
