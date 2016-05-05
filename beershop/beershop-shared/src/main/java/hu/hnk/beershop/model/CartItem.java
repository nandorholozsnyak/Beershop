package hu.hnk.beershop.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import hu.hnk.beershop.model.Beer.BeerBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * Egy kosár adatait tartalmazó osztály.
 * 
 * @author Nandi
 *
 */
@Entity
@Builder
@AllArgsConstructor
public class CartItem extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4761818681252091051L;

	/**
	 * A kosártermékek osztály kontruktora.
	 */
	public CartItem() {

	}

	/**
	 * A rendelt sör.
	 */
	@OneToOne
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime * result + ((addedToCart == null) ? 0 : addedToCart.hashCode());
		result = prime * result + ((beer == null) ? 0 : beer.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((removedFromCart == null) ? 0 : removedFromCart.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartItem other = (CartItem) obj;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
			return false;
		if (addedToCart == null) {
			if (other.addedToCart != null)
				return false;
		} else if (!addedToCart.equals(other.addedToCart))
			return false;
		if (beer == null) {
			if (other.beer != null)
				return false;
		} else if (!beer.equals(other.beer))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (removedFromCart == null) {
			if (other.removedFromCart != null)
				return false;
		} else if (!removedFromCart.equals(other.removedFromCart))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CartItem [beer=" + beer + ", quantity=" + quantity + ", active=" + active + ", addedToCart="
				+ addedToCart + ", removedFromCart=" + removedFromCart + "]";
	}

}
