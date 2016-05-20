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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.hnk.beershop.model.Cart;
import hu.hnk.beershop.model.User;
import hu.hnk.interfaces.CartDao;

/**
 * A kosarakat kezelő adathozzáférési osztály implementációja.
 * 
 * Az adatbázis hozzáférési osztály kezeli a {@link Cart} entitást.
 * 
 * @author Nandi
 *
 */
@Stateless
@Local(CartDao.class)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CartDaoImpl extends BaseDaoImpl<Cart> implements CartDao {

	/**
	 * Az osztály loggere.
	 */
	public static final Logger logger = LoggerFactory.getLogger(CartDaoImpl.class);

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
	public List<Cart> findAll() throws Exception {
		logger.info("Asking all cart items.");
		Query q = entityManager.createQuery("SELECT c FROM Cart c");
		return q.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Cart findByUser(User user) throws Exception {
		logger.info("Asking all cart items for user: {}", user.getUsername());
		Query q = entityManager.createQuery("SELECT c FROM Cart c WHERE user = :user");
		q.setParameter("user", user);
		return (Cart) q.getSingleResult();
	}

}
