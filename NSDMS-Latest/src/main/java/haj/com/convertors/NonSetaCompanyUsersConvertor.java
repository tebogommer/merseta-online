package haj.com.convertors;

import haj.com.entity.NonSetaCompanyUsers;
import haj.com.service.NonSetaCompanyUsersService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "NonSetaCompanyUsersConvertor")
public class NonSetaCompanyUsersConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a NonSetaCompanyUsers
 	 * @author TechFinium 
 	 * @see    NonSetaCompanyUsers
 	 * @return NonSetaCompanyUsers
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new NonSetaCompanyUsersService()
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
	 * Convert NonSetaCompanyUsers key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((NonSetaCompanyUsers)arg2).getId();
	}

/*
       <p:selectOneMenu id="NonSetaCompanyUsersId" value="#{xxxUI.NonSetaCompanyUsers.xxxType}" converter="NonSetaCompanyUsersConvertor" style="width:95%">
         <f:selectItems value="#{NonSetaCompanyUsersUI.NonSetaCompanyUsersList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="NonSetaCompanyUsers" for="NonSetaCompanyUsersID"/>
        <p:autoComplete id="NonSetaCompanyUsersID" value="#{NonSetaCompanyUsersUI.NonSetaCompanyUsers.municipality}" completeMethod="#{NonSetaCompanyUsersUI.completeNonSetaCompanyUsers}"
                            var="rv" itemLabel="#{rv.NonSetaCompanyUsersDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="NonSetaCompanyUsersConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="NonSetaCompanyUsers" style="white-space: nowrap">#{rv.NonSetaCompanyUsersDescription}</p:column>
       </p:autoComplete>         
       
*/

}
