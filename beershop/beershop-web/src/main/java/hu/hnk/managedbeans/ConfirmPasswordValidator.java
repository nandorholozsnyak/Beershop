package hu.hnk.managedbeans;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("confirmPasswordValidator")
public class ConfirmPasswordValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String password = (String) value;
		UIInput confirmPassword = (UIInput) component.getAttributes().get("passwordComponent");

		if (password == null || confirmPassword.getSubmittedValue() == null) {
			return;
		}

		if (!password.equals((String) confirmPassword.getSubmittedValue())) {
			throw new ValidatorException(new FacesMessage("A két jelszónak egyeznie kell."));
		}
	}

}