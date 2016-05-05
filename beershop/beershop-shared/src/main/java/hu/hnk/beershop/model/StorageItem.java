package hu.hnk.beershop.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * A sör raktár, amelyben található egy sör illetve egy egész szám attribútum
 * ami a sör mennyiségét hivatott mutatni.
 * 
 * @author Nandi
 *
 */
@Entity
@NamedQueries({ @NamedQuery(name = "StorageItem.findAll", query = "SELECT s FROM StorageItem s"),
		@NamedQuery(name = "StorageItem.findByBeer", query = "SELECT s FROM StorageItem s WHERE beer = :beer") })
@Builder
@AllArgsConstructor
public class StorageItem extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4761818681252091051L;

	/**
	 * A raktárban lévő tárgy osztály konstuktora.
	 */
	public StorageItem() {

	}

	/**
	 * A raktárban számontartott sörre való hivatkozás.
	 */
	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Beer beer;

	/**
	 * A sör aktuális darabszáma a raktárban.
	 */
	private Integer quantity;

	/**
	 * Visszaadja a sörre való hivatkozást.
	 * 
	 * @return a hivatkozott sör.
	 */
	public Beer getBeer() {
		return beer;
	}

	/**
	 * Beállítja a sörre való hivatkozást.
	 * 
	 * @param beer
	 *            a hivatkozandó sör.
	 */
	public void setBeer(Beer beer) {
		this.beer = beer;
	}

	/**
	 * Visszaadja a raktárban elérhető számszerűsített értéket.
	 * 
	 * @return a raktárban előrhető mennyiség.
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * Beállítja a raktárban elérhető sörök darabszámát.
	 * 
	 * @param quantity
	 *            a beállítandó érték.
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
