package hu.hnk.beershop.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


/**
 * Egy eseménynek a logját leíró entitást.
 * 
 * @author Nandi
 *
 */
@Entity
@NamedQueries({ @NamedQuery(name = "EventLog.findByUser", query = "SELECT e FROM EventLog e WHERE user = :user") })
public class EventLog extends BaseEntity implements Serializable {

	/**
	 * 
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
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(String action) {
		this.action = action;
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
	 * @return the date
	 */
	public LocalDateTime getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

}
