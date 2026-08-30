package haj.com.validators;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

// TODO: Auto-generated Javadoc
/**
 * The Class DateCantBeInFuture.
 */
@FacesValidator("f9.com.validators.DateCantBeInFuture")
public class DateCantBeInFuture implements Validator{
	 
		/* (non-Javadoc)
		 * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
		 */
		public void validate(FacesContext context, UIComponent component,
				Object value) throws ValidatorException {
			if (value instanceof Date) {
				Date d = (Date)value;
				if (d.getTime()> new Date().getTime()) {
					   throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Date can't be in future!", "Date can't be in future! Please change the date."));
				}
			}
		}

}
