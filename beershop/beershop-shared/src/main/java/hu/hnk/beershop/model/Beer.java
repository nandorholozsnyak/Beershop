package hu.hnk.beershop.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * A sör osztály ami tartalmazza a sör adatait.
 * 
 * @author Nandi
 *
 */
//@Table(name = "beer")
@Entity
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

}
