package hu.hnk.beershop.service.interfaces;

import hu.hnk.beershop.model.Rank;
import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.restrictions.BuyActionRestrictions;
import hu.hnk.beershop.service.restrictions.MoneyTransferRestrictions;

/**
 * A felhasználó tevékenységeket korlátozó interfész.
 * 
 * Minden felhasználónak be kell tartania bizonyos szabályokat, rangtól függően
 * vásárolhat napi szinten, ezeket a korlátozásokat a
 * {@link BuyActionRestrictions} osztály írja le.
 * 
 * A pénzfeltöltéssel kapcsolatban ugyan ilyen szabályok élnek amiket a
 * {@link MoneyTransferRestrictions} osztály definiál.
 * 
 * Létezik egy bizonyos típusa a söröknek amelyeket csak <b>Legendás</b> néven
 * hívunk, ezeket csak a {@link Rank#LEGENDA} ranggal rendelkező felhasználó
 * vásárolhatja meg.
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
	 * @throws Exception
	 *             adatbázis illetve más nem várt kivétel esetén
	 */
	public boolean checkIfUserCanTransferMoney(User user) throws Exception;

	/**
	 * Ellenőrzi hogy a paraméterként megadott felhasználó vásárolhat-e még
	 * magának sört a nap folyamán.
	 * 
	 * @param user
	 *            az ellenőrizendő felhasználó.
	 * @return igaz ha még vásárolhat, hamis ha már nem
	 * @throws Exception
	 *             adatbázis illetve más nem várt kivétel esetén
	 */
	public boolean checkIfUserCanBuyMoreBeer(User user) throws Exception;

	/**
	 * Ellenőrzi hogy a paraméterként megadott felhasználó megvásárolhatja-e már
	 * a legendásként feltüntetett sört.
	 * 
	 * @param user
	 *            az ellenőrizendő felhasználó.
	 * @return igaz ha megvásárolhatja, hamis ha már nem
	 * @throws Exception
	 *             adatbázis illetve más nem várt kivétel esetén
	 */
	public boolean checkIfUserCanBuyLegendBeer(User user) throws Exception;

}
