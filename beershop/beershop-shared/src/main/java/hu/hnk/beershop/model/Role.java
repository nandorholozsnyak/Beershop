package hu.hnk.beershop.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;


/**
 * A jogkör leírását tartalmazó osztály.
 * 
 * @author Nandi
 *
 */
@Entity
@NamedQuery(name = "Role.findByName", query = "SELECT r FROM Role r WHERE r.name = :name")
public class Role extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	 private static final long serialVersionUID = 1L;

	/**
	 * A jogkör neve.
	 */
	@Column(name = "name")
	private String name;


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
