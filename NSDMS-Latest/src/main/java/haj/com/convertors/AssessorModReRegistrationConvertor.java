package haj.com.convertors;

import haj.com.entity.AssessorModReRegistration;
import haj.com.service.AssessorModReRegistrationService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "AssessorModReRegistrationConvertor")
public class AssessorModReRegistrationConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a AssessorModReRegistration
 	 * @author TechFinium 
 	 * @see    AssessorModReRegistration
 	 * @return AssessorModReRegistration
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new AssessorModReRegistrationService()
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
	 * Convert AssessorModReRegistration key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((AssessorModReRegistration)arg2).getId();
	}

/*
       <p:selectOneMenu id="AssessorModReRegistrationId" value="#{xxxUI.AssessorModReRegistration.xxxType}" converter="AssessorModReRegistrationConvertor" style="width:95%">
         <f:selectItems value="#{AssessorModReRegistrationUI.AssessorModReRegistrationList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="AssessorModReRegistration" for="AssessorModReRegistrationID"/>
        <p:autoComplete id="AssessorModReRegistrationID" value="#{AssessorModReRegistrationUI.AssessorModReRegistration.municipality}" completeMethod="#{AssessorModReRegistrationUI.completeAssessorModReRegistration}"
                            var="rv" itemLabel="#{rv.AssessorModReRegistrationDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="AssessorModReRegistrationConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="AssessorModReRegistration" style="white-space: nowrap">#{rv.AssessorModReRegistrationDescription}</p:column>
       </p:autoComplete>         
       
*/

}
