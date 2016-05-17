package hu.hnk.dao;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	 * Az osztály loggere.
	 */
	public static final Logger logger = LoggerFactory.getLogger(RoleDaoImpl.class);

	/**
	 * Az osztály konstuktora.
	 */
	public RoleDaoImpl() {
		super(Role.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role findByName(String name) {
		logger.info("Finding role by name:" + name);
		TypedQuery<Role> role = entityManager.createNamedQuery("Role.findByName", Role.class);
		role.setParameter("name", name);
		try {
			return role.getSingleResult();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Role> findAll() {
		TypedQuery<Role> query = entityManager.createQuery("SELECT r FROM Role r", Role.class);
		return query.getResultList();
	}

}
