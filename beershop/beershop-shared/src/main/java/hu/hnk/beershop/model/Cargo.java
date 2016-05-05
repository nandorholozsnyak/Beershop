package hu.hnk.beershop.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;


/**
 * Egy rendelés adatait tartalmazó osztály.
 * 
 * @author Nandi
 *
 */
// @Table(name = "cargo")
@Entity
public class Cargo extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4761818681252091051L;

	/**
	 * A rendelt sörök listája.
	 */
	@ManyToMany(cascade = CascadeType.MERGE)
	private List<CartItem> items;

	/**
	 * A felhasználó aki leadta a rendelést.
	 */
	@OneToOne
	private User user;

	/**
	 * A rendelés leadásának dátuma.
	 */
	@Column(name = "dateOfOrder")
	private Date orderDate;

	/**
	 * A rendelés teljes összege.
	 */
	@Column(name = "totalprice")
	private Double totalPrice;

	/**
	 * A rendelő vendég szállítási címe.
	 */
	@Column(name = "address")
	private String address;

	/**
	 * A rendelés fizetésének típusa, utalás vagy bónusz pont.
	 */
	@NotNull
	@Column(name = "paymentMode")
	private String paymentMode;

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

	public List<CartItem> getItems() {
		return items;
	}

	public void setItems(List<CartItem> items) {
		this.items = items;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

}
