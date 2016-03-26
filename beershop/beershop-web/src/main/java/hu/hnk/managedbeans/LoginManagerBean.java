package hu.hnk.managedbeans;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.interfaces.UserService;

@ManagedBean(name = "userManagerBean")
@ViewScoped
public class LoginManagerBean implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2856400278569714670L;

	@EJB
	UserService userService;

	private String username;
	private String password;
		
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
		User newUser = new User();
		System.out.println("save button pushed!");
		System.out.println(username);
		System.out.println(password);
		newUser = new User();
		newUser.setPassword(password);
		newUser.setUsername(username);
		if (newUser != null) {
			userService.save(newUser);
			System.out.println("save !");
		}
	}

}
