/**
 * 
 */
package hu.hnk.dao;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import hu.hnk.beershop.model.Cart;
import hu.hnk.beershop.model.CartItem;
import hu.hnk.beershop.model.User;
import hu.hnk.interfaces.CartDao;

/**
 * @author Nandi
 *
 */
@Stateless
@Local(CartDao.class)
public class CartDaoImpl implements CartDao {

	/**
	 * JPA Entity Manager.
	 */
	@PersistenceContext
	private EntityManager em;

	/*
	 * (non-Javadoc)
	 * 
	 * @see hu.hnk.interfaces.CartDao#findAll()
	 */
	@Override
	public List<Cart> findAll() {
		Query q = em.createQuery("SELECT c FROM Cart c");
		return q.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hu.hnk.interfaces.CartDao#save(hu.hnk.beershop.model.Cart)
	 */
	@Override
	public Cart save(Cart cart) {
		return em.merge(cart);
	}

	@Override
	public Cart findByUser(User user) {
		Query q = em.createQuery("SELECT c FROM Cart c WHERE user = :user");
		q.setParameter("user", user);
		return (Cart) q.getSingleResult();
	}

	@Override
	public void deleteItem(Long id) {
		
		CartItem found = em.find(CartItem.class, id);
		
		em.remove(found);
		
	}

	@Override
	public CartItem updateItem(CartItem item) {
		return em.merge(item);		
	}

}
