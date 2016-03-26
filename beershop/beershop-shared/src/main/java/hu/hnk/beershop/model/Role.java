package hu.hnk.beershop.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "role")
@Entity
@NamedQuery(name = "Role.findByName", query = "SELECT r FROM Role r WHERE r.name = :name")
public class Role implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7180062495978991267L;

	/**
	 * A jogkör egyéni azonosítója.
	 */
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	/**
	 * A jogkör neve.
	 */
	@Column(name = "name")
	private String name;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

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
