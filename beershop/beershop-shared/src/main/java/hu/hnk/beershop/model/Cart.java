package hu.hnk.beershop.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * Egy kosár adatait tartalmazó osztály.
 * 
 * Egy felhasználóhoz kötött kosár mely tartalmazza a kosárba tett termékek
 * listáját, {@value Cart#items} illetve a kosárhoz kötött felhasználót,
 * {@value Cart#user}. Egy újonnan létrehozott vendég mindig kap egy kosarat a
 * sikeres regisztráció után.
 * 
 * @author Nandi
 *
 */
@Entity
@Builder
@AllArgsConstructor
public class Cart extends BaseEntity implements Serializable {

	/**
	 * A szerializáció során használt egyedi azonosító.
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

	/**
	 * A kosár osztály konstuktora.
	 */
	public Cart() {
		// Üres kosár objektum példányosítása.
	}

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
