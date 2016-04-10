package hu.hnk.beershop.service.interfaces;

import java.util.Date;

import hu.hnk.beershop.exception.EmailAlreadyTaken;
import hu.hnk.beershop.exception.UsernameAlreadyTaken;
import hu.hnk.beershop.model.User;

public interface UserService {

	public void save(User user);

	public boolean isOlderThanEighteen(Date dateOfBirth);

	public User findByUsername(String username);
	
	public boolean isUsernameAlreadyTaken(String username) throws UsernameAlreadyTaken;
	
	public boolean isEmailAlreadyTaken(String username) throws EmailAlreadyTaken;
}
