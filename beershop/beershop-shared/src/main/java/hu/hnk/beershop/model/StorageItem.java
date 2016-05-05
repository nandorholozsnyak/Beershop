package hu.hnk.beershop.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;


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
public class StorageItem extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4761818681252091051L;

	/**
	 * A sör.
	 */
	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Beer beer;

	/**
	 * A sör aktuális darabszáma a raktárban.
	 */
	private Integer quantity;

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

}
