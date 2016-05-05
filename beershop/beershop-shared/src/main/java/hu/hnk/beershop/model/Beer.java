package hu.hnk.beershop.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * A sör osztály ami tartalmazza a sör adatait.
 * 
 * @author Nandi
 *
 */

// @Table(name = "beer")
@Entity
@NamedQueries({ @NamedQuery(name = "Beer.findAll", query = "SELECT b FROM Beer b") })
@Builder
@AllArgsConstructor
public class Beer extends BaseEntity implements Serializable {

	/**
	 * serial Version.
	 */
	private static final long serialVersionUID = -4761818681252091051L;

	/**
	 * Sör osztály konstuktora.
	 */
	public Beer() {

	}

	/**
	 * A sör neve.
	 */
	@Column(name = "name", nullable = false, unique = true)
	private String name;

	/**
	 * A sör alkoholtartalma.
	 */
	@Column(name = "alcoholLevel", nullable = false)
	private Double alcoholLevel;

	/**
	 * A sör ára.
	 */
	@Column(name = "price", nullable = false)
	private Double price;

	/**
	 * A sörhöz tartozó megjegyzés.
	 */
	@Column(name = "comment", length = 255)
	private String comment;

	/**
	 * A sör űrtartalma.
	 */
	@Column(name = "capacity")
	private Double capacity;

	/**
	 * A sör akció során beállított kedvezménye.
	 */
	@Column(name = "discountAmount", nullable = false, columnDefinition = "int(5) default 0")
	private Integer discountAmount;

	/**
	 * A sör legendás státusza.
	 */
	@Column(name = "legendary")
	private boolean legendary;

	/**
	 * Visszaadja a sör nevét.
	 * 
	 * @return a sör neve.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Beállítja a sör nevét.
	 * 
	 * @param name
	 *            a sör új neve.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Visszaadja a sör alkoholtartalmát.
	 * 
	 * @return a sör alkoholtartalma.
	 */
	public Double getAlcoholLevel() {
		return alcoholLevel;
	}

	/**
	 * Beállítja a sör alkoholtartalmát.
	 * 
	 * @param alcoholLevel
	 *            a sör új alkoholtartalma.
	 */
	public void setAlcoholLevel(Double alcoholLevel) {
		this.alcoholLevel = alcoholLevel;
	}

	/**
	 * Visszaadja a sör árát.
	 * 
	 * @return a sör ára.
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * Beállítja a sör új árát.
	 * 
	 * @param price
	 *            a sör új ára.
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * Visszaadja a sörhöz tartozó megjegyzést.
	 * 
	 * @return a sörhöz tartozó megjegyzés.
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Beállítja a sörhöz tartozó megjegyzést.
	 * 
	 * @param comment
	 *            az új megjegyzés a sörhöz.
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * Visszaadja a sör kapacitását, űrtartalmát.
	 * 
	 * @return az űrtartalma.
	 */
	public Double getCapacity() {
		return capacity;
	}

	/**
	 * Beállítja a sör új űrtartalmát.
	 * 
	 * @param capacity
	 *            a sör új űrtartalma.
	 */
	public void setCapacity(Double capacity) {
		this.capacity = capacity;
	}

	/**
	 * Visszaadja a sör aktuális kedvezményét.
	 * 
	 * @return a sör kedvezménye.
	 */
	public Integer getDiscountAmount() {
		return discountAmount;
	}

	/**
	 * Beállítja a sör kedvezményét.
	 * 
	 * @param discountAmount
	 *            a sör új kedvezménye.
	 */
	public void setDiscountAmount(Integer discountAmount) {
		this.discountAmount = discountAmount;
	}

	/**
	 * Megmondja hogy egy sör legendás-e vagy sem.
	 * 
	 * @return igaz ha legendás, hamis ha nem.
	 */
	public boolean isLegendary() {
		return legendary;
	}

	/**
	 * Beállítja a sör legendás státuszát.
	 * 
	 * @param legendary
	 *            a sör legendás státusza, logikai értékkel.
	 */
	public void setLegendary(boolean legendary) {
		this.legendary = legendary;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alcoholLevel == null) ? 0 : alcoholLevel.hashCode());
		result = prime * result + ((capacity == null) ? 0 : capacity.hashCode());
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + ((discountAmount == null) ? 0 : discountAmount.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Beer other = (Beer) obj;
		if (alcoholLevel == null) {
			if (other.alcoholLevel != null)
				return false;
		} else if (!alcoholLevel.equals(other.alcoholLevel))
			return false;
		if (capacity == null) {
			if (other.capacity != null)
				return false;
		} else if (!capacity.equals(other.capacity))
			return false;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (discountAmount == null) {
			if (other.discountAmount != null)
				return false;
		} else if (!discountAmount.equals(other.discountAmount))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		return true;
	}

}
