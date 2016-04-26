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
			msg = null;
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

}
