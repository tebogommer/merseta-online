package haj.com.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

// TODO: Auto-generated Javadoc
/**
 * The Class NoNumericsInString.
 */
@FacesValidator("f9.com.validators.NoNumericsInString")
public class NoNumericsInString implements Validator{
	 
		/* (non-Javadoc)
		 * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
		 */
		public void validate(FacesContext context, UIComponent component,
				Object value) throws ValidatorException {
			String str = (String)value;
			if(str.matches(".*\\d.*")){
				FacesMessage msg = 
						new FacesMessage("Field may not contain numbers!", "Field may not contain numbers! Please remove the numbers.");
					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				   throw new ValidatorException(msg);
			} 
			//	   else{
			//		 System.out.println("does  NOT contains a number");
			//}
		}

}
