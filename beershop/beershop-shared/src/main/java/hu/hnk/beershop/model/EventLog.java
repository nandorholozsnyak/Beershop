package hu.hnk.beershop.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * Egy eseménynek a logját leíró entitást.
 * 
 * Az esemény az eseményhez társított felhasználót ({@value EventLog#user}), az
 * eseményhez kapcsolt akciót ({@value EventLog#action}) illetve az esemény
 * időpontját ({@value EventLog#date}) tárolják.
 * 
 * @author Nandi
 *
 */
@Entity
@NamedQueries({ @NamedQuery(name = "EventLog.findByUser", query = "SELECT e FROM EventLog e WHERE user = :user"),
		@NamedQuery(name = "EventLog.findByUserWhereDateIsToday", query = "SELECT e FROM EventLog e WHERE user = :user AND date BETWEEN :startDate AND :endDate") })
@Builder
@AllArgsConstructor
public class EventLog extends BaseEntity implements Serializable {

	/**
	 * A szerializáció során használt egyedi azonosító.
	 */
	private static final long serialVersionUID = -2757899869474041195L;

	/**
	 * Az eseménynek az akciója, azaz mi történt, egy esetleges vásárlás vagy
	 * pénzfeltöltés.
	 */
	@Column(name = "action")
	private String action;

	/**
	 * Az eseményben résztvevő felhasználó.
	 */
	@ManyToOne
	private User user;

	/**
	 * Az esemény dátuma.
	 */
	private LocalDateTime date;

	/**
	 * A felhasználói eseményeket rögzítő osztály konstuktora.
	 */
	public EventLog() {

	}

	/**
	 * Visszaadja az esemény akció mezőjének értékét.
	 * 
	 * @return az akció mezőjének értéke.
	 */
	public String getAction() {
		return action;
	}

	/**
	 * Beállítja az esemény akció mezőjének értékét.
	 * 
	 * @param action
	 *            az akció mező új értéke.
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * Visszaadja az eseményben résztvevő felhasználót.
	 * 
	 * @return the user az eseményben résztvető felhasználó.
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Beállítja az eseményben résztvevő felhasználót.
	 * 
	 * @param user
	 *            az eseményben résztvevő felhasználó.
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Visszaadja az esemény történésének időpontját.
	 * 
	 * @return az esemény történésének időpontja.
	 */
	public LocalDateTime getDate() {
		return date;
	}

	/**
	 * Beállítja az esemény történésének időpontját.
	 * 
	 * @param date
	 *            az esemény időpontja.
	 */
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

}
