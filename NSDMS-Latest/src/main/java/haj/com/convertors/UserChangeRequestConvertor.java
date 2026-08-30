package haj.com.convertors;

import haj.com.entity.UserChangeRequest;
import haj.com.service.UserChangeRequestService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "UserChangeRequestConvertor")
public class UserChangeRequestConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a UserChangeRequest
 	 * @author TechFinium 
 	 * @see    UserChangeRequest
 	 * @return UserChangeRequest
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new UserChangeRequestService()
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
	 * Convert UserChangeRequest key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((UserChangeRequest)arg2).getId();
	}

/*
       <p:selectOneMenu id="UserChangeRequestId" value="#{xxxUI.UserChangeRequest.xxxType}" converter="UserChangeRequestConvertor" style="width:95%">
         <f:selectItems value="#{UserChangeRequestUI.UserChangeRequestList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="UserChangeRequest" for="UserChangeRequestID"/>
        <p:autoComplete id="UserChangeRequestID" value="#{UserChangeRequestUI.UserChangeRequest.municipality}" completeMethod="#{UserChangeRequestUI.completeUserChangeRequest}"
                            var="rv" itemLabel="#{rv.UserChangeRequestDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="UserChangeRequestConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="UserChangeRequest" style="white-space: nowrap">#{rv.UserChangeRequestDescription}</p:column>
       </p:autoComplete>         
       
*/

}
