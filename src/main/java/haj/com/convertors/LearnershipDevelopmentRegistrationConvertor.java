package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.LearnershipDevelopmentRegistration;
import haj.com.service.LearnershipDevelopmentRegistrationService;

@FacesConverter(value = "LearnershipDevelopmentRegistrationConvertor")
public class LearnershipDevelopmentRegistrationConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LearnershipDevelopmentRegistration
 	 * @author TechFinium 
 	 * @see    LearnershipDevelopmentRegistration
 	 * @return LearnershipDevelopmentRegistration
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LearnershipDevelopmentRegistrationService()
						.findByKey(Integer.valueOf(value));
			} catch (NumberFormatException e) {
				logger.fatal(e);
			} catch (Exception e) {
				logger.fatal(e);
			}

		}
	    return null;
	}


	/**
	 * Convert LearnershipDevelopmentRegistration key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LearnershipDevelopmentRegistration)arg2).getId();
	}

/*
       <p:selectOneMenu id="LearnershipDevelopmentRegistrationId" value="#{xxxUI.LearnershipDevelopmentRegistration.xxxType}" converter="LearnershipDevelopmentRegistrationConvertor" style="width:95%">
         <f:selectItems value="#{LearnershipDevelopmentRegistrationUI.LearnershipDevelopmentRegistrationList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LearnershipDevelopmentRegistration" for="LearnershipDevelopmentRegistrationID"/>
        <p:autoComplete id="LearnershipDevelopmentRegistrationID" value="#{LearnershipDevelopmentRegistrationUI.LearnershipDevelopmentRegistration.municipality}" completeMethod="#{LearnershipDevelopmentRegistrationUI.completeLearnershipDevelopmentRegistration}"
                            var="rv" itemLabel="#{rv.LearnershipDevelopmentRegistrationDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LearnershipDevelopmentRegistrationConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LearnershipDevelopmentRegistration" style="white-space: nowrap">#{rv.LearnershipDevelopmentRegistrationDescription}</p:column>
       </p:autoComplete>         
       
*/

}
