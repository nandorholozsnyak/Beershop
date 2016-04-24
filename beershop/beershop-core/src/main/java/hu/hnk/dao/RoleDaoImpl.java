package hu.hnk.dao;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import hu.hnk.beershop.model.Role;
import hu.hnk.interfaces.RoleDao;

/**
 * A jogköröket kezelõ adathozzáférési osztály megvalósítása.
 * @author Nandi
 *
 */
@Stateless
@Local(RoleDao.class)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class RoleDaoImpl implements RoleDao {
	
	/**
	 * JPA Entity Manager.
	 */
	@PersistenceContext(unitName = "BeerShopUnit")
	private EntityManager em;
	

	/**
	 * {@inheritDoc}
	 */
	public Role findByName(String name) {
		TypedQuery<Role> role = em.createNamedQuery("Role.findByName", Role.class);
		role.setParameter("name", name);
		return role.getSingleResult();
	}
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role save(Role role) {
		return em.merge(role);
	}

}
