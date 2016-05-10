package hu.hnk.beershop.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

/**
 * Egy kosár adatait tartalmazó osztály.
 * 
 * @author Nandi
 *
 */
@Entity
@Builder
@AllArgsConstructor
@ToString
public class Cart extends BaseEntity implements Serializable {

	/**
	 * serial Version.
	 */
	private static final long serialVersionUID = -4761818681252091051L;

	/**
	 * A kosár osztály konstuktora.
	 */
	public Cart() {
	}

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

	/**
	 * Visszaadja a kosár tulajdonosát.
	 * 
	 * @return a kosár tulajdonosa.
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Beállítja a kosár tulajdonosát.
	 * 
	 * @param user
	 *            a kosár tulajdonosa.
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Visszaadja a kosárban szereplő termékeket.
	 * 
	 * @return a kosárban szereplő termékek listája.
	 */
	public List<CartItem> getItems() {
		return items;
	}

	/**
	 * Beállítja a kosárba tartozó termékeket.
	 * 
	 * @param items
	 *            a termékek amelyek a kosárban kerülnek.
	 */
	public void setItems(List<CartItem> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "Cart [id=" + id + ", items=" + items + ", user=" + user + "]";
	}

}
