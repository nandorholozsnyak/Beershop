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

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * Egy rendelés adatait tartalmazó osztály.
 * 
 * A leadott rendelés tartalmazza a szállítandó termékek listáját:
 * {@value Cargo#items}, a szállítási hely címét: {@value Cargo#address}, a
 * felhasználót aki leadta a rendelést: {@value Cargo#user}, a rendelés dátumát:
 * {@value Cargo#orderDate}, a fizetendő összeget: {@value Cargo#totalPrice}
 * illetve a fizetés módját {@value Cargo#paymentMode}.
 * 
 * 
 * @author Nandi
 *
 */
// @Table(name = "cargo")
@Entity
@Builder
@AllArgsConstructor
public class Cargo extends BaseEntity implements Serializable {

	/**
	 * A szerializáció során használt egyedi azonosító.
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
	 * A szállítás osztály konstuktora.
	 */
	public Cargo() {
	}

	/**
	 * Visszaadja a szállítás dátumát.
	 * 
	 * @return a szállítás dátuma.
	 */
	public Date getOrderDate() {
		return orderDate;
	}

	/**
	 * Beállítja a szállítás dátumát.
	 * 
	 * @param orderDate
	 *            a szállítás új dátuma.
	 */
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	/**
	 * Visszaadja a szállítás teljes árát, ebbe bele tartozik a termékek
	 * összesített ára is.
	 * 
	 * @return a teljes fizetendő összeg.
	 */
	public Double getTotalPrice() {
		return totalPrice;
	}

	/**
	 * Beállítja a teljes fizetendő összeget.
	 * 
	 * @param totalPrice
	 *            a teljes fizetendő összeg.
	 */
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * Visszaadja a teljes kiszámított vásárlás értéke,a szállítási költség
	 * nélkül.
	 * 
	 * @return a teljes kiszámított vásárlás értéke, a szállítási költség
	 *         nélkül.
	 */
	public Double getCargoTotalPrice() {
		return items.stream()
				.filter(p -> p.getActive())
				.mapToDouble(e -> e.getBeer()
						.getPrice() * e.getQuantity()
						* (100 - e.getBeer()
								.getDiscountAmount())
						/ 100)
				.sum();
	}

	/**
	 * Visszaadja a szállítás pontos címét.
	 * 
	 * @return a szállítás pontos címe.
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Beállítja a szállítás pontos címét.
	 * 
	 * @param address
	 *            a szállítás új címe.
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Visszaadja a szállításban szereplő termékek listáját.
	 * 
	 * @return a szállításban szereplő termékek listája.
	 */
	public List<CartItem> getItems() {
		return items;
	}

	/**
	 * Beállítja a szállításban résztvevő termékek listáját.
	 * 
	 * @param items
	 *            a termékek listája.
	 */
	public void setItems(List<CartItem> items) {
		this.items = items;
	}

	/**
	 * Visszaadja a szállítást kezdeményező felhasználót.
	 * 
	 * @return a szállítást kezdeményező felhasználó.
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Beállítja a szállítást kezdeményező felhasználót.
	 * 
	 * @param user
	 *            a szállítást kezdeményező felhasználó.
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Visszaadja a fizetési módot.
	 * 
	 * @return a fizetési mód.
	 */
	public String getPaymentMode() {
		return paymentMode;
	}

	/**
	 * Beállítja a fizetési módot.
	 * 
	 * @param paymentMode
	 *            a szállítás fizetésének módja.
	 */
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

}
