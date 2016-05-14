package hu.hnk.beershop.service.logfactory;

/**
 * Az események típusait tartalmazó enumeráció.
 * 
 * Három darab eseményt tudunk megkülönböztetni.
 * 
 * Egy regisztrációs eseményt, {@code EventLogType#REGISTRATION} , ami akkor jön
 * létre amikor a felhasználó beregisztrál a shop-ba.
 * 
 * Az egyenlegfeltöltés során egy {@code EventLogType#MONEYTRANSFER} típusú
 * esemény jön létre, ennek a segítségével tudjuk validálni hogy a felhasználó
 * melyik napokon hányszor töltötte fel a számláját.
 * 
 * A harmadik eseményt jelző típus a vásárlást jelzi, {@code EventLogType#BUY},
 * a napi korlátozásokat tudjuk vele ellenőrizni.
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
