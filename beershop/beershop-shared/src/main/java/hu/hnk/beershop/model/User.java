package hu.hnk.beershop.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * A felhasználó osztály tartalmazza egy felhasználó adatait.
 * 
 * @author Nandi
 *
 */
@Table(name = "users")
@Entity
@NamedQueries({ @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :name"),
		@NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
		@NamedQuery(name = "User.findUsername", query = "SELECT u.username FROM User u WHERE u.username = :name"),
		@NamedQuery(name = "User.findEmail", query = "SELECT u.email FROM User u WHERE u.email = :email") })
@Builder
@AllArgsConstructor
@DynamicInsert
public class User extends BaseEntity implements Serializable {

	/**
	 * A szerializáció során használt egyedi azonosító.
	 */
	private static final long serialVersionUID = 8256608378786882228L;

	/**
	 * A felhasználó felhasználóneve.
	 */
	@Column(name = "username", nullable = false)
	private String username;

	/**
	 * A felhasználó jelszava.
	 */
	@Column(name = "password", nullable = false)
	private String password;

	/**
	 * A felhasználó jogkörei.
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Role> roles;

	/**
	 * A felhasználó e-mail címe.
	 */
	@Column(name = "email")
	private String email;

	/**
	 * A felhasználó pontjai.
	 */
	@Column(name = "points")
	@ColumnDefault(value = "0.0")
	private Double points;

	/**
	 * A felhasználó születési dátuma.
	 */
	@Column(name = "dateOfBirth")
	private LocalDate dateOfBirth;

	/**
	 * A felhasználó tapasztalati pontja, amivel több kedvezményt illetve a
	 * rangját ez alapaján számoljuk majd ki.
	 */
	@Column(name = "experiencePoints", nullable = false)
	@ColumnDefault(value = "0.0")
	private Double experiencePoints;

	/**
	 * A felhasználó saját kosara amelybe termékeket tud majd elhelyezni.
	 */
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Cart cart;

	/**
	 * A felhasználó egyenlege amellyel majd söröket vásárolhat.
	 */
	@Column(name = "money")
	@ColumnDefault(value = "0.0")
	private Double money;

	/**
	 * A felhasználó osztály konstuktora.
	 */
	public User() {
	}

	/**
	 * Visszaadja a felhasználó születési dátumát.
	 * 
	 * @return a felhasználó születési dátuma.
	 */
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * Beállítja a felhasználó születési dátumát.
	 * 
	 * @param dateOfBirth
	 *            a beállítandó születési dátum.
	 */
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * Visszaadja a felhasználó e-mail címét.
	 * 
	 * @return a felhasználó e-mail címe.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Beállítja a felhasználó e-mail címét.
	 * 
	 * @param email
	 *            a beállítandó e-mail cím.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Visszaadja a felhasználó jögköreit egy listában.
	 * 
	 * @return a felhasználó jogkörei.
	 */
	public List<Role> getRoles() {
		return roles;
	}

	/**
	 * Beállítja a felhasználó jogköreit.
	 * 
	 * @param roles
	 *            a beállítandó jogkörök listája.
	 */
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	/**
	 * Visszaadja a felhasználó felhasználónevét.
	 * 
	 * @return a felhasználóneve.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Beállítja a felhasználó felhasználónevét.
	 * 
	 * @param username
	 *            a felhasználó új felhasználóneve.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Visszaadja a felhasználó jelszavát, titkosítva.
	 * 
	 * @return a felhasználó jelszava, titkosítva.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Beállítja a felhasználó jelszavát.
	 * 
	 * @param password
	 *            a beállítandó jelszó.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Visszaadja a felhasználó bónusz pontjait.
	 * 
	 * @return a felhasználó bónusz pontjai.
	 */
	public Double getPoints() {
		return points;
	}

	/**
	 * Beállítja a felhasználó bónusz pontjait.
	 * 
	 * @param points
	 *            a felhasználó új bónusz pontjai.
	 */
	public void setPoints(Double points) {
		this.points = points;
	}

	/**
	 * Visszaadja a felhasználó tapasztalatpontjait.
	 * 
	 * @return a felhasználó tapasztalat pontjai.
	 */
	public Double getExperiencePoints() {
		return experiencePoints;
	}

	/**
	 * Beállítja a felhasználó tapasztalatpontjait.
	 * 
	 * @param experiencePoints
	 *            a felhasználó új tapasztalatpontja.
	 */
	public void setExperiencePoints(Double experiencePoints) {
		this.experiencePoints = experiencePoints;
	}

	/**
	 * Visszaadja a felhasználó kosarát.
	 * 
	 * @return a felhasználó kosara.
	 */
	public Cart getCart() {
		return cart;
	}

	/**
	 * Beállítja a felhasználó kosarát.
	 * 
	 * @param cart
	 *            a beállítandó felhasználói kosár.
	 */
	public void setCart(Cart cart) {
		this.cart = cart;
	}

	/**
	 * Visszaadja a felhasználó pénzét.
	 * 
	 * @return a felhasználó pénze.
	 */
	public Double getMoney() {
		return money;
	}

	/**
	 * Beállítja a felhasználó pénzét.
	 * 
	 * @param money
	 *            a beállítandó pénzmennyiség.
	 */
	public void setMoney(Double money) {
		this.money = money;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", roles=" + roles + ", email="
				+ email + ", points=" + points + ", dateOfBirth=" + dateOfBirth + "]";
	}

}
