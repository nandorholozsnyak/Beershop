package hu.hnk.beershop.service.interfaces;

import java.util.Date;

import hu.hnk.beershop.exception.InvalidPinCodeException;
import hu.hnk.beershop.exception.DailyMoneyTransferLimitExceededException;
import hu.hnk.beershop.model.Rank;
import hu.hnk.beershop.model.User;

/**
 * A felhasználó entitáshoz kapcsolódó szolgáltatások interfésze.
 * 
 * @author Nandi
 *
 */
public interface UserService {

	/**
	 * Felhasználó mentése.
	 * 
	 * @param user
	 *            a mentendő felhasználó.
	 * @throws Exception
	 *             adatbázis illetve más nem várt kivétel esetén
	 */
	public void save(User user) throws Exception;

	/**
	 * Ellenőrzi hogy a megadott dátum már "idősebb" mint 18 év.
	 * 
	 * @param dateOfBirth
	 *            a vizsgálandó dátum.
	 * @return igaz ha idősebb, hamis ha még nem.
	 */
	public boolean isOlderThanEighteen(Date dateOfBirth);

	/**
	 * Felhasználó keresése a felhasználóneve alapján.
	 * 
	 * @param username
	 *            a keresendő felhasználónév
	 * @return a megtalált felhasználó, ha nincs ilyen akkor null.
	 */
	public User findByUsername(String username);

	/**
	 * Felhasználónév ellenörzés, a kapott felhasználónevet ellenőrzi hogy
	 * válaszható-e még a regisztráció során.
	 * 
	 * @param username
	 *            az ellenőrizendő felhasználónév.
	 * @return igaz ha szabad a felhasználónév, hamis ha már nem.
	 */
	public boolean isUsernameAlreadyTaken(String username);

	/**
	 * E-mail cím ellenörzés, a kapott e-mail címet ellenőrzi hogy válaszható-e
	 * még a regisztráció során.
	 * 
	 * @param email
	 *            az ellenőrizendő e-mail cím.
	 * @return igaz ha szabad a email cím, hamis ha már nem.
	 */
	public boolean isEmailAlreadyTaken(String email);

	/**
	 * A felhasználó rangjának való számítása. A számítást segítő függvény a
	 * RankInterval nevű osztályt használja.
	 * 
	 * @param user
	 *            a felhasználó akinek a rangját ki szeretnénk számolni.
	 * @return a kiszámolt rang.
	 */
	public Rank countRankFromXp(User user);

	/**
	 * A felhasználó tapasztalatpontjának százalékos megjelenítéséhez való
	 * segédfüggvény.
	 * 
	 * @param experiencePoints
	 *            a tapasztalatpont.
	 * @return a kiszámolt százalék.
	 */
	public Integer countExperiencePointsInPercentage(Double experiencePoints);

	/**
	 * Felhasználó számára való pénzfeltöltési lehetőséget is biztosít az
	 * alkalmazás, ezzel a metódussal történik meg a tranzakció.
	 * 
	 * A felhasználó többféle érték közül választhat. A rendszer generál neki
	 * egy PIN kódot amelyet a <code>exceptedPin</code> változó mutat és neki
	 * ezt kell megadnia, <code>userPin</code>, illetve egy pénzmennyiséget.
	 * 
	 * Kétféle kivétellel jelezhetjük a tranzakció sikertelenségét. Az egyik az
	 * {@link InvalidPinCodeException} ha a felhasználó nem az elvárt PIN kódot
	 * adta meg, illetve a {@link DailyMoneyTransferLimitExceededException}
	 * kivétellel ha a felhasználó túllépte a napi korlátot.
	 * 
	 * @param userPin
	 *            a felhasználó által begépelt PIN kód.
	 * @param expectedPin
	 *            az alkalmazás által generált PIN kód.
	 * @param money
	 *            a feltöltendő összeg.
	 * @param loggedInUser
	 *            a bejelentkezett felhasználó.
	 * @throws InvalidPinCodeException
	 *             akkor dobjuk ha a felhasználó rossz PIN kódot adott meg.
	 * @throws DailyMoneyTransferLimitExceededException
	 *             akkor dobjuk ha a felhasználó túllépte a megadott napi
	 *             limitet.
	 * @throws Exception
	 *             adatbázis illetve más nem várt kivétel esetén
	 */
	public void transferMoney(String userPin, String expectedPin, Integer money, User loggedInUser)
			throws InvalidPinCodeException, DailyMoneyTransferLimitExceededException, Exception;
}
