package hu.hnk.beershop.service.interfaces;

import hu.hnk.beershop.model.User;

/**
 * A felhasználó tevékenységeket korlátozó interfész. Minden felhasználónak be
 * kell tartania bizonyos szabályokat, rangtól függően vásárolhat napi szinten
 * kezdetben csak napi 3 vásárlást bonyolíthat le, a következő szinten már napi
 * 5 darabot, ettől nagyobb szinteken már napi 20-at. Az egyenlegfeltöltés is
 * hasonló rendszer szerint működik. A legendés söröket csak az
 * <p>
 * Expert
 * </p>
 * rangú felhasználók rakhatják a kosarukba illetve vásárolhatják utána meg.
 * 
 * @author Nandi
 *
 */
public interface RestrictionCheckerService {

	/**
	 * Ellenőrzi hogy a paraméterként megadott felhasználó utalhat-e még magának
	 * pént a mai nap folyamán.
	 * 
	 * @param user
	 *            az ellenőrizendő felhasználó.
	 * @return igaz ha még utalhat, hamis ha már nem
	 */
	public boolean checkIfUserCanTransferMoney(User user);

	/**
	 * Ellenőrzi hogy a paraméterként megadott felhasználó vásárolhat-e még
	 * magának sört a nap folyamán.
	 * 
	 * @param user
	 *            az ellenőrizendő felhasználó.
	 * @return igaz ha még vásárolhat, hamis ha már nem
	 */
	public boolean checkIfUserCanBuyMoreBeer(User user);

	/**
	 * Ellenőrzi hogy a paraméterként megadott felhasználó megvásárolhatja-e már
	 * a legendásként feltüntetett sört.
	 * 
	 * @param user
	 *            az ellenőrizendő felhasználó.
	 * @return igaz ha megvásárolhatja, hamis ha már nem
	 */
	public boolean checkIfUserCanBuyLegendBeer(User user);

}
