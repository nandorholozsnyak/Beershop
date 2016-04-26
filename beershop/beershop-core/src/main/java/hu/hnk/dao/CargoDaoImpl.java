/**
 * 
 */
package hu.hnk.dao;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import hu.hnk.beershop.model.Cargo;
import hu.hnk.beershop.model.Cart;
import hu.hnk.beershop.model.CartItem;
import hu.hnk.beershop.model.User;
import hu.hnk.interfaces.CargoDao;
import hu.hnk.persistenceunit.PersistenceUnitDeclaration;

/**
 * A kosarakat kezelõ adathozzáférési osztály implementációja.
 * 
 * @author Nandi
 *
 */
@Stateless
@Local(CargoDao.class)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CargoDaoImpl implements CargoDao {

	/**
	 * JPA Entity Manager.
	 */
	@PersistenceContext(unitName = PersistenceUnitDeclaration.PERSISTENCE_UNIT)
	private EntityManager em;

	@Override
	public Cargo save(Cargo cargo) {
		return em.merge(cargo);
	}

}
