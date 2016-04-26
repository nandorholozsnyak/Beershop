/**
 * 
 */
package hu.hnk.dao;

import java.time.LocalDateTime;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import hu.hnk.beershop.model.Cart;
import hu.hnk.beershop.model.CartItem;
import hu.hnk.beershop.model.User;
import hu.hnk.interfaces.CartDao;

/**
 * A kosarakat kezelõ adathozzáférési osztály implementációja.
 * 
 * @author Nandi
 *
 */
@Stateless
@Local(CartDao.class)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CartDaoImpl implements CartDao {

	/**
	 * JPA Entity Manager.
	 */
	@PersistenceContext
	private EntityManager em;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Cart> findAll() {
		Query q = em.createQuery("SELECT c FROM Cart c");
		return q.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Cart save(Cart cart) {
		return em.merge(cart);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Cart findByUser(User user) {
		Query q = em.createQuery("SELECT c FROM Cart c WHERE user = :user");
		q.setParameter("user", user);
		return (Cart) q.getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteItemLogically(CartItem item) {
		item.setActive(false);
		item.setRemovedFromCart(LocalDateTime.now());
		em.merge(item);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CartItem updateItem(CartItem item) {
		return em.merge(item);
	}

}
