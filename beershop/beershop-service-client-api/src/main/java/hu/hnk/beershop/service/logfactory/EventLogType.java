package hu.hnk.beershop.service.logfactory;

/**
 * Az események típusait tartalmazó enumeráció.
 * 
 * Három darab eseményt tudunk megkülönböztetni.
 * <ul>
 * <li>Egy regisztrációs eseményt, {@link EventLogType#REGISTRATION} , ami akkor
 * jön létre amikor a felhasználó beregisztrál a shop-ba.</li>
 * 
 * <li>Az egyenlegfeltöltés során egy {@link EventLogType#MONEYTRANSFER} típusú
 * esemény jön létre, ennek a segítségével tudjuk validálni hogy a felhasználó
 * melyik napokon hányszor töltötte fel a számláját.</li>
 * 
 * <li>A harmadik eseményt jelző típus a vásárlást jelzi,
 * {@link EventLogType#BUY}, a napi korlátozásokat tudjuk vele ellenőrizni.</li>
 * </ul>
 * 
 * @author Nandi
 *
 */
public enum EventLogType {
	/**
	 * Regisztráció során létrejövő regisztrációs eseményt jelző érték.
	 */
	REGISTRATION,
	/**
	 * Vásárlás során létrejövő vásárlási eseményt jelző érték.
	 */
	BUY,
	/**
	 * Készpénzfeltöltés során létrejövő készpénzfeltöltési eseményt jelző
	 * érték.
	 */
	MONEYTRANSFER
}
