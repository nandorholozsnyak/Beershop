package hu.hnk.beershop.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

/**
 * Egy kosár adatait tartalmazó osztály.
 * 
 * @author Nandi
 *
 */
@Entity
public class Cart implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4761818681252091051L;

	/**
	 * A kosár egyedi azonosítója.
	 */
	@Id
	@Column(name = "id")
	@GeneratedValue
	private Long id;

	/**
	 * A rendelt sörök listája darabszámokkal együtt.
	 */
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

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
