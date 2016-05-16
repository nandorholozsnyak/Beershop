package hu.hnk.managedbeans;

import java.io.IOException;
import java.io.Serializable;
import java.time.ZoneId;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
	 * Beállítja a felhasználó születési dátumát.
	 * 
	 * @param dateOfBirth
	 *            a felhasználó születési dátuma.
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * Visszaadja a másodjára beírt jelszót.
	 * 
	 * @return a másodjára beírt jelszó.
	 */
	public String getRePassword() {
		return rePassword;
	}

	/**
	 * Beállítja a másodjára beírt jelszót.
	 * 
	 * @param rePassword
	 *            a másodjára beírásra kerülendő jelszó.
	 */
	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	/**
	 * Visszaadja a felhasználó által beírt e-mailt.
	 * 
	 * @return a felhasználó által beírt e-mail.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Beállítja a felhasználó által beírt e-mailt.
	 * 
	 * @param email
	 *            a felhasználó által begépelendő e-mail.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Visszaadja a felhasználó által begépelt választott felhasználónevet.
	 * 
	 * @return a felhasználó által begépelt felhasználónév.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Beállítja a felhasználó által begépelendő felhasználónevet.
	 * 
	 * @param username
	 *            a felhasználó által begépelendő felhasználónév.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Visszaadja az elsőnek beírt jelszót.
	 * 
	 * @return az elsőnek beírt jelszó.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Beállítja az elsőnek begépelendő jelszót.
	 * 
	 * @param password
	 *            az elsőnek begépelendő jelszó.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Inicializáló metódus, a managed bean létrejöttekor.
	 */
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

					announceUserAboutRegistrationStatus();
				} catch (Exception e) {
					FacesMessageTool.createErrorMessage("Hiba regisztráció közben.");
					logger.warn(e.getMessage(), e);
				}
			}
		} else {
			FacesMessageTool.createWarnMessage("Regisztráció nem lehetséges.");
		}
	}

	/**
	 * A felhasználó értesítése a sikeres regisztráció után.
	 * 
	 * A sikeres regisztráció után elnavigálunk az index lapra amelyen egy
	 * felugró ablakban közöljük a felhasználót a sikeres regisztráció
	 * státuszáról.
	 * 
	 * @throws IOException
	 *             ha nem található az átnavigálása oldal.
	 */
	private void announceUserAboutRegistrationStatus() throws IOException {
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
	}

	/**
	 * Új felhasználó entitás létrehozása a felhasználó által begépelt
	 * adatokkal.
	 * 
	 * @return az újonnan létrehozott felhasználó entitása.
	 */
	private User createNewUser() {
		User newUser;
		newUser = new User();
		newUser.setPassword(encoder.encode(password));
		newUser.setUsername(username);
		newUser.setEmail(email);
		newUser.setPoints(0.0);
		newUser.setDateOfBirth(dateOfBirth.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate());
		newUser.setExperiencePoints(0.0);
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
