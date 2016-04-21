package hu.hnk.tool;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class FacesMessageTool {

	public static void publishMessage(FacesMessage msg) {
		if (msg != null) {
			FacesContext.getCurrentInstance().addMessage(null, msg);
			msg = null;
		}
	}

}
