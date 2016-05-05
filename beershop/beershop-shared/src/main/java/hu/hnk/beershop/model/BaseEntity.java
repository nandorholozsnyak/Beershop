package hu.hnk.beershop.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * Az entitások főosztálya, ami tartalmazza az egyedi azonosítójukat.
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseEntity other = (BaseEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
