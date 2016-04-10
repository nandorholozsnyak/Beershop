package hu.hnk.managedbeans;

import java.io.Serializable;
import java.util.Date;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import hu.hnk.beershop.exception.AgeNotAcceptable;
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
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	String username;
	String password;
	String rePassword;
	String email;
	Date dateOfBirth;
	Boolean isUsernameFree = true;
	Boolean isEmailFree = true;
	Boolean isOlderThanEighteen = true;
	
	
	
	public BCryptPasswordEncoder getEncoder() {
		return encoder;
	}

	public void setEncoder(BCryptPasswordEncoder encoder) {
		this.encoder = encoder;
	}

	public Boolean getIsUsernameFree() {
		return isUsernameFree;
	}

	public void setIsUsernameFree(Boolean isUsernameFree) {
		this.isUsernameFree = isUsernameFree;
	}

	public Boolean getIsEmailFree() {
		return isEmailFree;
	}

	public void setIsEmailFree(Boolean isEmailFree) {
		this.isEmailFree = isEmailFree;
	}

	public Boolean getIsOlderThanEighteen() {
		return isOlderThanEighteen;
	}

	public void setIsOlderThanEighteen(Boolean isOlderThanEighteen) {
		this.isOlderThanEighteen = isOlderThanEighteen;
	}

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

	public void saveUser(ActionEvent actionEvent) throws AgeNotAcceptable {
		Boolean canRegister = isOlderThanEighteen && isEmailFree && isUsernameFree;
		if (canRegister) {
			User newUser = new User();
			logger.info("Mentés gomb lenyomva.");
			newUser = new User();
			newUser.setPassword(encoder.encode(password));
			newUser.setUsername(username);
			newUser.setEmail(email);
			newUser.setPoints((double) 0);
			newUser.setDateOfBirth(dateOfBirth);
			if (newUser != null) {
				try {
					userService.save(newUser);
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO, "Sikeres regisztráció.", ""));
					FacesContext.getCurrentInstance().getExternalContext().redirect("/public/index.xhtml");
				} catch (Exception e) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hiba regisztráció közben.", "Hiba!"));
				}
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Regisztráció nem lehetséges.", "Hiba!"));
		}
	}
	
	
	public void usernameListener() {
		if(userService.isUsernameAlreadyTaken(username)) {
			logger.info("Felhasználónév már foglalt!");
			FacesContext.getCurrentInstance().addMessage("registration:username",
			new FacesMessage(FacesMessage.SEVERITY_WARN, "Ez a felhasználónév már foglalt!", "Ez a felhasználónév már foglalt!"));
			isUsernameFree = false;
		} else {
			isUsernameFree = true;
		}
	}
	
	public void emailListener() {
		if(userService.isEmailAlreadyTaken(email)) {
			logger.info("E-mail cím már foglalt!");
			FacesContext.getCurrentInstance().addMessage("registration:email",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Ez az e-mail cím már foglalt!", "Ez az e-mail cím már foglalt!"));
			isEmailFree = false;
		} else {
			isEmailFree = true;
		}
	}
	
	public void ageListener() {
		if(!userService.isOlderThanEighteen(dateOfBirth)) {
			logger.info("Csak 18 év fölött lehetséges a regisztráció.");
			FacesContext.getCurrentInstance().addMessage("registration:dateOfBirth",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Csak 18 fölött lehetséges a regisztráció.", "Csak 18 fölött lehetséges a regisztráció."));
			isOlderThanEighteen = true;
		} else {
			isOlderThanEighteen = false;
		}
	}

}
