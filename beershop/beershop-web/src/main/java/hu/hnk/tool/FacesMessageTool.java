package hu.hnk.tool;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * @author Nandi
 *
 */
public class FacesMessageTool {

	/**
	 * @param msg
	 */
	public static void publishMessage(FacesMessage msg) {
		if (msg != null) {
			FacesContext.getCurrentInstance()
					.addMessage(null, msg);
		}
	}

	public static void publishMessageToField(FacesMessage msg, String fieldName) {
		if (msg != null) {
			FacesContext.getCurrentInstance()
					.addMessage(fieldName, msg);
		}
	}

	/**
	 * @param msg
	 */
	public static void createInfoMessage(String msg) {
		publishMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
	}

	/**
	 * @param msg
	 */
	public static void createWarnMessage(String msg) {
		publishMessage(new FacesMessage(FacesMessage.SEVERITY_WARN, msg, msg));
	}

	/**
	 * @param msg
	 */
	public static void createErrorMessage(String msg) {
		publishMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
	}

	/**
	 * @param msg
	 */
	public static void createInfoMessageToField(String msg, String fieldName) {
		publishMessageToField(new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg), fieldName);
	}

	/**
	 * @param msg
	 */
	public static void createWarnMessageToField(String msg, String fieldName) {
		publishMessageToField(new FacesMessage(FacesMessage.SEVERITY_WARN, msg, msg), fieldName);
	}

	/**
	 * @param msg
	 */
	public static void createErrorMessageToField(String msg, String fieldName) {
		publishMessageToField(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg), fieldName);
	}

}
