package haj.com.convertors;

import haj.com.entity.UserPermissions;
import haj.com.service.UserPermissionsService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "UserPermissionsConvertor")
public class UserPermissionsConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a UserPermissions
 	 * @author TechFinium 
 	 * @see    UserPermissions
 	 * @return UserPermissions
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new UserPermissionsService()
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
	 * Convert UserPermissions key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((UserPermissions)arg2).getId();
	}

/*
       <p:selectOneMenu id="UserPermissionsId" value="#{xxxUI.UserPermissions.xxxType}" converter="UserPermissionsConvertor" style="width:95%">
         <f:selectItems value="#{UserPermissionsUI.UserPermissionsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="UserPermissions" for="UserPermissionsID"/>
        <p:autoComplete id="UserPermissionsID" value="#{UserPermissionsUI.UserPermissions.municipality}" completeMethod="#{UserPermissionsUI.completeUserPermissions}"
                            var="rv" itemLabel="#{rv.UserPermissionsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="UserPermissionsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="UserPermissions" style="white-space: nowrap">#{rv.UserPermissionsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
