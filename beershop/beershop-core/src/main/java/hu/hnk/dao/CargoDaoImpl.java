/**
 * 
 */
package hu.hnk.dao;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;

import hu.hnk.beershop.model.Cargo;
import hu.hnk.beershop.model.User;
import hu.hnk.interfaces.CargoDao;

/**
 * 
 * A kosarakat kezelő adathozzáférési osztály implementációja.
 * 
 * @author Nandi
 *
 */
@Stateless
@Local(CargoDao.class)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CargoDaoImpl extends BaseDaoImpl<Cargo> implements CargoDao {

	/**
	 * Az osztály konstuktora.
	 */
	public CargoDaoImpl() {
		super(Cargo.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Cargo> findByUser(User user) {
		TypedQuery<Cargo> cargos = entityManager.createQuery("SELECT c FROM Cargo c where c.user = :user", Cargo.class);
		cargos.setParameter("user", user);
		return cargos.getResultList();
	}

}
