package hu.hnk.dao;

import java.time.LocalDateTime;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.hnk.beershop.model.CartItem;
import hu.hnk.interfaces.CartItemDao;

/**
 * @author Nandi
 *
 */
@Stateless
@Local(CartItemDao.class)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CartItemDaoImpl extends BaseDaoImpl<CartItem> implements CartItemDao {
	/**
	 * Az osztály loggere.
	 */
	public static final Logger logger = LoggerFactory.getLogger(CartItemDaoImpl.class);

	/**
	 * Az osztály paraméter nélküli konstuktora.
	 */
	public CartItemDaoImpl() {
		super(CartItem.class);
	}

	@Override
	public void deleteItemLogically(CartItem item) throws Exception {
		logger.info("Trying to delete item logically.");
		item.setActive(false);
		item.setRemovedFromCart(LocalDateTime.now());
		update(item);
	}

}
