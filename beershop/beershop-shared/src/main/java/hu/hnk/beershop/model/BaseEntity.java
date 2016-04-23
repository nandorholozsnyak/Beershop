package hu.hnk.beershop.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Az entitások fõosztálya, ami tartalmazza az egyedi azonosítójukat.
 * 
 * @author Nandi
 *
 */
@MappedSuperclass
public class BaseEntity {

	/**
	 * Az entitások egyedi azonosítója.
	 */
	@Id
	@GeneratedValue
	@Column(name = "id")
	protected Long id;

	/**
	 * Visszaadja az entitás egyedi azonosítóját.
	 * 
	 * @return az egyedi azonosító.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Beállítja az entitás egyedi azonosítóját.
	 * 
	 * @param id
	 *            az azonosító amit be kell állítani.
	 */
	public void setId(Long id) {
		this.id = id;
	}
}
