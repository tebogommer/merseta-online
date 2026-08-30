package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.UserResponsibility;
import haj.com.service.lookup.UserResponsibilityService;

@FacesConverter(value = "UserResponsibilityConvertor")
public class UserResponsibilityConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a UserResponsibility
 	 * @author TechFinium 
 	 * @see    UserResponsibility
 	 * @return UserResponsibility
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new UserResponsibilityService()
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
	 * Convert UserResponsibility key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((UserResponsibility)arg2).getId();
	}

/*
       <p:selectOneMenu id="UserResponsibilityId" value="#{xxxUI.UserResponsibility.xxxType}" converter="UserResponsibilityConvertor" style="width:95%">
         <f:selectItems value="#{UserResponsibilityUI.UserResponsibilityList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="UserResponsibility" for="UserResponsibilityID"/>
        <p:autoComplete id="UserResponsibilityID" value="#{UserResponsibilityUI.UserResponsibility.municipality}" completeMethod="#{UserResponsibilityUI.completeUserResponsibility}"
                            var="rv" itemLabel="#{rv.UserResponsibilityDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="UserResponsibilityConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="UserResponsibility" style="white-space: nowrap">#{rv.UserResponsibilityDescription}</p:column>
       </p:autoComplete>         
       
*/

}
