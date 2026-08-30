package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.UserBrowserInformation;
import haj.com.service.UserBrowserInformationService;

@FacesConverter(value = "UserBrowserInformationConvertor")
public class UserBrowserInformationConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a UserBrowserInformation
 	 * @author TechFinium 
 	 * @see    UserBrowserInformation
 	 * @return UserBrowserInformation
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new UserBrowserInformationService()
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
	 * Convert UserBrowserInformation key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((UserBrowserInformation)arg2).getId();
	}

/*
       <p:selectOneMenu id="UserBrowserInformationId" value="#{xxxUI.UserBrowserInformation.xxxType}" converter="UserBrowserInformationConvertor" style="width:95%">
         <f:selectItems value="#{UserBrowserInformationUI.UserBrowserInformationList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="UserBrowserInformation" for="UserBrowserInformationID"/>
        <p:autoComplete id="UserBrowserInformationID" value="#{UserBrowserInformationUI.UserBrowserInformation.municipality}" completeMethod="#{UserBrowserInformationUI.completeUserBrowserInformation}"
                            var="rv" itemLabel="#{rv.UserBrowserInformationDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="UserBrowserInformationConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="UserBrowserInformation" style="white-space: nowrap">#{rv.UserBrowserInformationDescription}</p:column>
       </p:autoComplete>         
       
*/

}
