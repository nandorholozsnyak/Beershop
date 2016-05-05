/**
 * 
 */
package hu.hnk.dao;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

import hu.hnk.beershop.model.Cart;
import hu.hnk.beershop.model.User;
import hu.hnk.interfaces.CartDao;


/**
 * A kosarakat kezelő adathozzáférési osztály implementációja.
 * 
 * @author Nandi
 *
 */
@Stateless
@Local(CartDao.class)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CartDaoImpl extends BaseDaoImpl<Cart> implements CartDao {

	/**
	 * Az osztály konstuktora.
	 */
	public CartDaoImpl() {
		super(Cart.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Cart> findAll() {
		Query q = entityManager.createQuery("SELECT c FROM Cart c");
		return q.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Cart findByUser(User user) {
		Query q = entityManager.createQuery("SELECT c FROM Cart c WHERE user = :user");
		q.setParameter("user", user);
		return (Cart) q.getSingleResult();
	}

}
