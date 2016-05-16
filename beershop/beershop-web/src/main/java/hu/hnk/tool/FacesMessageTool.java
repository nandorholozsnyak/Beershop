package hu.hnk.tool;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * A felhasználói felületre való üzenetküldési osztály.
 * 
 * Egy segédosztály ami különböző metódusokkal próbálja meg a felhasználót
 * tájékoztatni bizonyos eseményekről.
 * 
 * {@value FacesMessageTool#createInfoMessage(String)} létrehoz egy INFO értékű
 * üzenetet és kirakja a felasználói felületre.
 * {@value FacesMessageTool#createWarnMessage(String)} létrehoz egy WARN értékű
 * üzenetet és kirakja a felasználói felületre.
 * {@value FacesMessageTool#createErrorMessage(String)} létrehoz egy ERROR
 * értékű üzenetet és kirakja a felasználói felületre.
 * 
 * @author Nandi
 *
 */
public class FacesMessageTool {

	/**
	 * Privát konstruktor.
	 */
	private FacesMessageTool() {
	}

	/**
	 * A paraméterül kapott üzenet kiírása a felhaszálói felületre.
	 * 
	 * @param msg
	 *            a kiírandó üzenet.
	 */
	public static void publishMessage(FacesMessage msg) {
		if (msg != null) {
			FacesContext.getCurrentInstance()
					.addMessage(null, msg);
		}
	}

	/**
	 * A paraméterül kapott üzenetet (<code>msg</code>) a második paraméter (
	 * <code>fieldname</code>) azonosítóval rendelkező komponensre írja ki.
	 * 
	 * @param msg
	 *            a kiírandó üzenet.
	 * @param fieldName
	 *            a komponens azonosítója.
	 */
	public static void publishMessageToField(FacesMessage msg, String fieldName) {
		if (msg != null) {
			FacesContext.getCurrentInstance()
					.addMessage(fieldName, msg);
		}
	}

	/**
	 * Kíír egy INFO értékű üzenetet a felhasználói felületre.
	 * 
	 * @param msg
	 *            a kiírandó üzenet.
	 */
	public static void createInfoMessage(String msg) {
		publishMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
	}

	/**
	 * Kíír egy WARN értékű üzenetet a felhasználói felületre.
	 * 
	 * @param msg
	 *            a kiírandó üzenet.
	 */
	public static void createWarnMessage(String msg) {
		publishMessage(new FacesMessage(FacesMessage.SEVERITY_WARN, msg, msg));
	}

	/**
	 * Kíír egy ERROR értékű üzenetet a felhasználói felületre.
	 * 
	 * @param msg
	 *            a kiírandó üzenet.
	 */
	public static void createErrorMessage(String msg) {
		publishMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
	}

	/**
	 * Kíír egy INFO értékű üzenetet a felhasználói felületre a paraméterül
	 * kapott azonosítójú komponensre.
	 * 
	 * @param msg
	 *            a kiírandó üzenet.
	 * @param fieldName
	 *            a komponens azonosítója.
	 * 
	 */
	public static void createInfoMessageToField(String msg, String fieldName) {
		publishMessageToField(new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg), fieldName);
	}

	/**
	 * Kíír egy WARN értékű üzenetet a felhasználói felületre a paraméterül
	 * kapott azonosítójú komponensre.
	 * 
	 * @param msg
	 *            a kiírandó üzenet.
	 * @param fieldName
	 *            a komponens azonosítója.
	 * 
	 */
	public static void createWarnMessageToField(String msg, String fieldName) {
		publishMessageToField(new FacesMessage(FacesMessage.SEVERITY_WARN, msg, msg), fieldName);
	}

	/**
	 * Kíír egy ERROR értékű üzenetet a felhasználói felületre a paraméterül
	 * kapott azonosítójú komponensre.
	 * 
	 * @param msg
	 *            a kiírandó üzenet.
	 * @param fieldName
	 *            a komponens azonosítója.
	 * 
	 */
	public static void createErrorMessageToField(String msg, String fieldName) {
		publishMessageToField(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg), fieldName);
	}

}
