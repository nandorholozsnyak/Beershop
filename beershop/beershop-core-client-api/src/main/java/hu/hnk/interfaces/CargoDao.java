package hu.hnk.interfaces;

import hu.hnk.beershop.model.Cargo;

/**
 * A szállításokat kezelõ adathozzáférési osztály interfésze.
 * 
 * @author Nandi
 *
 */
public interface CargoDao {

	/**
	 * @param cargo
	 * @return
	 */
	public Cargo save(Cargo cargo);
	
}
