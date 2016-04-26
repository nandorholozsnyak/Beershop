package hu.hnk.beershop.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Egy kosár adatait tartalmazó osztály.
 * 
 * @author Nandi
 *
 */
@Entity
public class CartItem extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4761818681252091051L;

	/**
	 * A rendelt sör.
	 */
	@ManyToOne
	private Beer beer;

	@Column(name = "quantity")
	private Integer quantity;

	@Column(name = "active")
	private Boolean active = true;

	@Column(name = "addedToCart")
	private LocalDateTime addedToCart;

	@Column(name = "removedFromCart")
	private LocalDateTime removedFromCart;

	public Beer getBeer() {
		return beer;
	}

	public void setBeer(Beer beer) {
		this.beer = beer;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * @return the addedToCart
	 */
	public LocalDateTime getAddedToCart() {
		return addedToCart;
	}

	/**
	 * @param addedToCart
	 *            the addedToCart to set
	 */
	public void setAddedToCart(LocalDateTime addedToCart) {
		this.addedToCart = addedToCart;
	}

	public LocalDateTime getRemovedFromCart() {
		return removedFromCart;
	}

	public void setRemovedFromCart(LocalDateTime removedFromCart) {
		this.removedFromCart = removedFromCart;
	}

}
