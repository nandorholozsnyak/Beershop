package hu.hnk.beershop.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * A sör osztály ami tartalmazza a sör adatait.
 * 
 * @author Nandi
 *
 */

// @Table(name = "beer")
@Entity
@NamedQueries({ @NamedQuery(name = "Beer.findAll", query = "SELECT b FROM Beer b") })
public class Beer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4761818681252091051L;
	/**
	 * A felhasználó egyedi azonosítója.
	 */
	@Id
	@Column(name = "id")
	@GeneratedValue
	private Long id;

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
	 * A sör ûrtartalma.
	 */
	@Column(name = "capacity")
	private Double capacity;

	/**
	 * A sör akció során beállított kedvezménye.
	 */
	@Column(name = "discountAmount", nullable = false, columnDefinition = "int(5) default 0")
	private Integer discountAmount;

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

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the alcoholLevel
	 */
	public Double getAlcoholLevel() {
		return alcoholLevel;
	}

	/**
	 * @param alcoholLevel
	 *            the alcoholLevel to set
	 */
	public void setAlcoholLevel(Double alcoholLevel) {
		this.alcoholLevel = alcoholLevel;
	}

	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the capacity
	 */
	public Double getCapacity() {
		return capacity;
	}

	/**
	 * @param capacity
	 *            the capacity to set
	 */
	public void setCapacity(Double capacity) {
		this.capacity = capacity;
	}

	/**
	 * @return the discountAmount
	 */
	public Integer getDiscountAmount() {
		return discountAmount;
	}

	/**
	 * @param discountAmount
	 *            the discountAmount to set
	 */
	public void setDiscountAmount(Integer discountAmount) {
		this.discountAmount = discountAmount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alcoholLevel == null) ? 0 : alcoholLevel.hashCode());
		result = prime * result + ((capacity == null) ? 0 : capacity.hashCode());
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + ((discountAmount == null) ? 0 : discountAmount.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Beer [id=" + id + ", name=" + name + ", alcoholLevel=" + alcoholLevel + ", price=" + price
				+ ", comment=" + comment + ", capacity=" + capacity + ", discountAmount=" + discountAmount + "]";
	}
	
	

}
