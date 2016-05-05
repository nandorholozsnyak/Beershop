package hu.hnk.beershop.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;


/**
 * Egy kosár adatait tartalmazó osztály.
 * 
 * @author Nandi
 *
 */
@Entity
public class Cart extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4761818681252091051L;

	/**
	 * A rendelt sörök listája darabszámokkal együtt.
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	private List<CartItem> items;

	/**
	 * A felhasználó aki rendelkezik az aktuális kosárral.
	 */
	@OneToOne
	private User user;

	// /**
	// * A kosárba helyezés idejét tartalmazza.
	// */
	// @Column(name = "takenToCart")
	// private Date takenToCart;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the items
	 */
	public List<CartItem> getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(List<CartItem> items) {
		this.items = items;
	}

	// /**
	// * @return the takenToCart
	// */
	// public Date getTakenToCart() {
	// return takenToCart;
	// }
	//
	// /**
	// * @param takenToCart
	// * the takenToCart to set
	// */
	// public void setTakenToCart(Date takenToCart) {
	// this.takenToCart = takenToCart;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Cart [id=" + id + ", items=" + items + ", user=" + user + "]";
	}

}
