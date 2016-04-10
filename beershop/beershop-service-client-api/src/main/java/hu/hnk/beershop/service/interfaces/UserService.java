package hu.hnk.beershop.service.interfaces;

import java.util.Date;

import hu.hnk.beershop.model.User;

public interface UserService {

	public void save(User user);

	public boolean isOlderThanEighteen(Date dateOfBirth);

	public User findByUsername(String username);
	
	public boolean isUsernameAlreadyTaken(String username);
	
	public boolean isEmailAlreadyTaken(String email);
}
