package hu.hnk.beershop.service.interfaces;

import hu.hnk.beershop.model.User;

/**
 * A felhasználó tevékenységeket korlátozó interfész. Minden felhasználónak be
 * kell tartania bizonyos szabályokat, rangtól függõen vásárolhat napi szinten
 * kezdetben csak napi 3 vásárlást bonyolíthat le, a következõ szinten már napi
 * 5 darabot, ettõl nagyobb szinteken már napi 20-at. Az egyenlegfeltöltés is
 * hasonló rendszer szerint mûködik. A legendés söröket csak az
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
	 * Ellenõrzi hogy a paraméterként megadott felhasználó utalhat-e még magának
	 * pént a mai nap folyamán.
	 * 
	 * @param user
	 *            az ellenõrizendõ felhasználó.
	 * @return igaz ha még utalhat, hamis ha már nem
	 */
	public boolean checkIfUserCanTransferMoney(User user);

	/**
	 * Ellenõrzi hogy a paraméterként megadott felhasználó vásárolhat-e még
	 * magának sört a nap folyamán.
	 * 
	 * @param user
	 *            az ellenõrizendõ felhasználó.
	 * @return igaz ha még vásárolhat, hamis ha már nem
	 */
	public boolean checkIfUserCanBuyMoreBeer(User user);

	/**
	 * Ellenõrzi hogy a paraméterként megadott felhasználó megvásárolhatja-e már
	 * a legendásként feltüntetett sört.
	 * 
	 * @param user
	 *            az ellenõrizendõ felhasználó.
	 * @return igaz ha megvásárolhatja, hamis ha már nem
	 */
	public boolean checkIfUserCanBuyLegendBeer(User user);

	/**
	 * Ellenõrzi hogy a paraméterként megadott felhasználónak rendelkezésére áll
	 * a megfelelõ számú egyenleg a fizetéshez.
	 * 
	 * @param user
	 *            az ellenõrizendõ felhasználó.
	 * @return igaz ha még utalhat, hamis ha már nem
	 */
	public boolean checkIfUserCanPayBeers(User user);

}
