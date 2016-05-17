package hu.hnk.beershop.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * A jogkör leírását tartalmazó osztály.
 * 
 * A jogkörnek van egy neve {@value Role#name}.
 * 
 * @author Nandi
 *
 */
@Entity
@NamedQuery(name = "Role.findByName", query = "SELECT r FROM Role r WHERE r.name = :name")
@Builder
@AllArgsConstructor
public class Role extends BaseEntity implements Serializable {

	/**
	 * A szerializáció során használt egyedi azonosító.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A jogkör neve.
	 */
	@Column(name = "name")
	private String name;

	/**
	 * A jogkör osztály konstuktora.
	 */
	public Role() {

	}

	/**
	 * Visszaadja a jogkör nevét.
	 * 
	 * @return a jogkör neve.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Beállítja a jogkör nevét.
	 * 
	 * @param name
	 *            a jogkör új neve.
	 */
	public void setName(String name) {
		this.name = name;
	}

}
