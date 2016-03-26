package hu.hnk.interfaces;

import hu.hnk.beershop.model.Role;

public interface RoleDao {
	public Role findByName(String name);
	
	public Role save(Role role);
}
