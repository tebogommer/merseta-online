package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.SkillsRegistration;
import haj.com.service.SkillsRegistrationService;

@FacesConverter(value = "SkillsRegistrationConvertor")
public class SkillsRegistrationConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SkillsRegistration
 	 * @author TechFinium 
 	 * @see    SkillsRegistration
 	 * @return SkillsRegistration
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SkillsRegistrationService()
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
	 * Convert SkillsRegistration key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((SkillsRegistration)arg2).getId();
	}

/*
       <p:selectOneMenu id="SkillsRegistrationId" value="#{xxxUI.SkillsRegistration.xxxType}" converter="SkillsRegistrationConvertor" style="width:95%">
         <f:selectItems value="#{SkillsRegistrationUI.SkillsRegistrationList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SkillsRegistration" for="SkillsRegistrationID"/>
        <p:autoComplete id="SkillsRegistrationID" value="#{SkillsRegistrationUI.SkillsRegistration.municipality}" completeMethod="#{SkillsRegistrationUI.completeSkillsRegistration}"
                            var="rv" itemLabel="#{rv.SkillsRegistrationDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SkillsRegistrationConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SkillsRegistration" style="white-space: nowrap">#{rv.SkillsRegistrationDescription}</p:column>
       </p:autoComplete>         
       
*/

}
