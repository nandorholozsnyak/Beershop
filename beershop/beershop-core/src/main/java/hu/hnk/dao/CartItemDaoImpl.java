package hu.hnk.dao;

import java.time.LocalDateTime;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

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
	 * 
	 */
	public CartItemDaoImpl() {
		super(CartItem.class);
	}

	@Override
	public void deleteItemLogically(CartItem item) throws Exception {
		item.setActive(false);
		item.setRemovedFromCart(LocalDateTime.now());
		update(item);
		entityManager.detach(item);
	}

}
