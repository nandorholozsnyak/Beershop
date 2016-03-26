package hu.hnk.beershop.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Egy rendelés adatait tartalmazó osztály.
 * 
 * @author Nandi
 *
 */
@Table(name = "order")
@Entity
public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4761818681252091051L;

	/**
	 * A rendelés egyedi azonosítója.
	 */
	@Id
	@Column(name = "id")
	@GeneratedValue
	private Long id;

	/**
	 * A rendelt sörök listája.
	 */
	@OneToMany
	private List<Beer> beers;

	/**
	 * A felhasználó aki leadta a rendelést.
	 */
	@Column(name = "userid")
	@ManyToOne
	private User user;

	/**
	 * A rendelés leadásának dátuma.
	 */
	@Column(name = "orderdate")
	private Date orderDate;

	/**
	 * A rendelés teljes összege.
	 */
	@Column(name = "totalprice")
	private Double totalPrice;

	/**
	 * A rendelõ vendég szállítási címe.
	 */
	@Column(name = "address")
	private String address;

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
	 * @return the beers
	 */
	public List<Beer> getBeers() {
		return beers;
	}

	/**
	 * @param beers
	 *            the beers to set
	 */
	public void setBeers(List<Beer> beers) {
		this.beers = beers;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the orderDate
	 */
	public Date getOrderDate() {
		return orderDate;
	}

	/**
	 * @param orderDate
	 *            the orderDate to set
	 */
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	/**
	 * @return the totalPrice
	 */
	public Double getTotalPrice() {
		return totalPrice;
	}

	/**
	 * @param totalPrice
	 *            the totalPrice to set
	 */
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

}
