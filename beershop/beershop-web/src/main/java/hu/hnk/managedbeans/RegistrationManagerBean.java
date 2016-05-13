package hu.hnk.managedbeans;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import hu.hnk.beershop.model.Cart;
import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.interfaces.UserService;
import hu.hnk.tool.FacesMessageTool;

/**
 * A regisztrációs szolgáltatást megvalósító managed bean.
 * 
 * @author Nandi
 *
 */
@ManagedBean(name = "registrationManagerBean")
@ViewScoped
public class RegistrationManagerBean implements Serializable {

	/**
	 * Az osztály loggere.
	 */
	public static final Logger logger = LoggerFactory.getLogger(RegistrationManagerBean.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 2856400278569714670L;

	/**
	 * A felhasználókat kezelő szolgáltatás.
	 */
	@EJB
	private UserService userService;

	/**
	 * A jelszavak titkosításához használt BCryptPasswordEncoder.
	 */
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	/**
	 * A választott felhasználónév.
	 */
	private String username;

	/**
	 * A választott jelszó.
	 */
	private String password;

	/**
	 * A választott jelszó újabb megadása elgépelési célok miatt.
	 */
	private String rePassword;

	/**
	 * A választott e-mail cím.
	 */
	private String email;

	/**
	 * A felhasználó születési dátuma.
	 */
	private Date dateOfBirth;

	/**
	 * Szabad-e még a felhasználónév.
	 */
	private Boolean isUsernameFree = true;

	/**
	 * Szabad-e még az e-mail cím.
	 */
	private Boolean isEmailFree = true;

	/**
	 * Betöltötte-e már a 18. életévét.
	 */
	private Boolean isOlderThanEighteen = true;

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

	@PostConstruct
	public void init() {
		dateOfBirth = new Date();
	}

	/**
	 * Felhasználó mentése gomb eseménye.
	 * 
	 * @param actionEvent
	 *            maga az esemény.
	 */
	public void saveUser(ActionEvent actionEvent) {
		Boolean canRegister = isOlderThanEighteen && isEmailFree && isUsernameFree;
		if (canRegister) {
			User newUser = createNewUser();
			if (newUser != null) {
				try {
					userService.save(newUser);
					FacesContext.getCurrentInstance()
							.getExternalContext()
							.getFlash()
							.setKeepMessages(true);
					FacesMessageTool.createInfoMessage("Sikeres regisztráció.");
					FacesContext.getCurrentInstance()
							.getExternalContext()
							.redirect("index.xhtml");

					FacesContext.getCurrentInstance()
							.getPartialViewContext()
							.getRenderIds()
							.add("mainPageMsg");
				} catch (Exception e) {
					FacesMessageTool.createErrorMessage("Hiba regisztráció közben.");
					logger.error(e.getMessage(), e);
				}
			}
		} else {
			FacesMessageTool.createWarnMessage("Regisztráció nem lehetséges.");
		}
	}

	private User createNewUser() {
		User newUser;
		newUser = new User();
		newUser.setPassword(encoder.encode(password));
		newUser.setUsername(username);
		newUser.setEmail(email);
		newUser.setPoints((double) 0);
		newUser.setDateOfBirth(dateOfBirth.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate());
		newUser.setExperiencePoints((double) 0);
		newUser.setMoney(0.0);
		newUser.setCart(new Cart());
		return newUser;
	}

	/**
	 * A regisztráció során a felhasználói név foglaltáságát vizsgáló metódus.
	 */
	public void usernameListener() {
		if (userService.isUsernameAlreadyTaken(username)) {
			logger.info("Felhasználónév már foglalt!");
			FacesMessageTool.createWarnMessageToField("Ez a felhasználónév már foglalt!", "registration:username");
			isUsernameFree = false;
		} else {
			isUsernameFree = true;
		}
	}

	/**
	 * A regisztráció során az e-mail cím foglaltáságát vizsgáló metódus.
	 */
	public void emailListener() {
		if (userService.isEmailAlreadyTaken(email)) {
			logger.info("E-mail cím már foglalt!");
			FacesMessageTool.createWarnMessageToField("Ez az e-mail cím már foglalt!", "registration:email");
			isEmailFree = false;
		} else {
			isEmailFree = true;
		}
	}

	/**
	 * A regisztráció során a 18. életévét betöltött regisztráló validálásáért
	 * felelő metódus.
	 */
	public void ageListener() {
		if (!userService.isOlderThanEighteen(dateOfBirth)) {
			logger.info("Csak 18 év fölött lehetséges a regisztráció.");
			FacesMessageTool.createWarnMessageToField("Csak 18 fölött lehetséges a regisztráció.",
					"registration:dateOfBirth");
			isOlderThanEighteen = false;
		} else {
			isOlderThanEighteen = true;
		}
	}

}
