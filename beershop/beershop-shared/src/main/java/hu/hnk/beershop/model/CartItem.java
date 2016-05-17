package hu.hnk.beershop.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * A kosárba tett termékek adatait leíró osztály.
 * 
 * A kosárba tett termékek jellemzői: a termék mint sör {@value CartItem#beer},
 * a darabszáma az aktuális termékből {@value CartItem#quantity}, a termék
 * aktivitása a kosáron belül {@value CartItem#active} (ha <code>true</code>
 * akkor a felhasználó látja a kosarában, egyébként nem), a termék kosárhoz
 * adásának időpontja {@value CartItem#addedToCart}, a termék kosárból való
 * törlésének / megvásárlásának időpontja {@value CartItem#removedFromCart}.
 * 
 * @author Nandi
 *
 */
@Entity
@Builder
@AllArgsConstructor
public class CartItem extends BaseEntity implements Serializable {

	/**
	 * A szerializáció során használt egyedi azonosító.
	 */
	private static final long serialVersionUID = -4761818681252091051L;

	/**
	 * A rendelt sör.
	 */
	@OneToOne
	private Beer beer;

	/**
	 * A termék darabszáma.
	 */
	@Column(name = "quantity")
	private Integer quantity;

	/**
	 * A termék kosárban lévő aktivitása.
	 */
	@Column(name = "active")
	private Boolean active = true;

	/**
	 * A kosárhoz adás dátuma.
	 */
	@Column(name = "addedToCart")
	private LocalDateTime addedToCart;

	/**
	 * A kosárból való eltávolítás dátuma.
	 */
	@Column(name = "removedFromCart")
	private LocalDateTime removedFromCart;

	/**
	 * A kosártermékek osztály kontruktora.
	 */
	public CartItem() {
		// Üres kosár áru objektum példányosítása.
	}

	/**
	 * Visszadja a termék hivatkozását egy sörre.
	 * 
	 * @return a hivatkozott sör.
	 */
	public Beer getBeer() {
		return beer;
	}

	/**
	 * Beállítja a termék hivatkozását egy sörre.
	 * 
	 * @param beer
	 *            a hivatkozandó sör.
	 */
	public void setBeer(Beer beer) {
		this.beer = beer;
	}

	/**
	 * Visszaadja a hivatkozott sörből való darabszámot.
	 * 
	 * @return a sör darabszáma.
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * Beállítja a sör darabszámát.
	 * 
	 * @param quantity
	 *            a beállítandó darabszám.
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * Visszaadja a termék aktivitását.
	 * 
	 * @return the active igaz ha a termék aktív a kosárban, egyébként hamis.
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * Beállítja a termék aktivitását.
	 * 
	 * @param active
	 *            a beállítnadó aktivitás.
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * Visszaadja a dátumot amikor a termék a kosárba került.
	 * 
	 * @return a termék kosárba helyezésének időpontja.
	 */
	public LocalDateTime getAddedToCart() {
		return addedToCart;
	}

	/**
	 * Beállítja a termék kosárba helyezésének időpontját.
	 * 
	 * @param addedToCart
	 *            a beállítandó dátum.
	 */
	public void setAddedToCart(LocalDateTime addedToCart) {
		this.addedToCart = addedToCart;
	}

	/**
	 * Visszaadja a termék eltávolításának idejét a kosárból.
	 * 
	 * @return a termék kosárból való eltávolításának ideje.
	 */
	public LocalDateTime getRemovedFromCart() {
		return removedFromCart;
	}

	/**
	 * Beállítja a termék eltávolításának időpontját.
	 * 
	 * @param removedFromCart
	 *            a termék eltávolításának időpontja.
	 */
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
