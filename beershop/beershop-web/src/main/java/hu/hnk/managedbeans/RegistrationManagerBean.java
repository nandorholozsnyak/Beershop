package hu.hnk.managedbeans;

import java.io.Serializable;
import java.util.Date;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import hu.hnk.beershop.model.Rank;
import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.interfaces.UserService;

@ManagedBean(name = "registrationManagerBean")
@ViewScoped
public class RegistrationManagerBean implements Serializable {
	public static final Logger logger = Logger.getLogger(RegistrationManagerBean.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 2856400278569714670L;

	@EJB
	UserService userService;

	String username;
	String password;
	String rePassword;
	String email;
	Date dateOfBirth;

	/**
	 * @return the dateOfBirth
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth
	 *            the dateOfBirth to set
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return the rePassword
	 */
	public String getRePassword() {
		return rePassword;
	}

	/**
	 * @param rePassword
	 *            the rePassword to set
	 */
	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public void saveUser(ActionEvent actionEvent) {
		if (userService.isOlderThanEighteen(dateOfBirth)) {
			User newUser = new User();
			logger.info("Mentés gomb lenyomva.");

			newUser = new User();
			newUser.setPassword(password);
			newUser.setUsername(username);
			newUser.setEmail(email);
			newUser.setRank(Rank.Amatuer);
			newUser.setPoints((double) 0);
			newUser.setDateOfBirth(dateOfBirth);
			if (newUser != null) {
				try {
					userService.save(newUser);
				} catch (Exception e) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hiba regisztráció közben.", "Hiba!"));
				}
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "Csak 18 fölött lehetséges a regisztráció.", "Hiba!"));
		}
	}

}
