package haj.com.convertors;

import haj.com.entity.UsersDisability;
import haj.com.service.UsersDisabilityService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "UsersDisabilityConvertor")
public class UsersDisabilityConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a UsersDisability
 	 * @author TechFinium 
 	 * @see    UsersDisability
 	 * @return UsersDisability
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new UsersDisabilityService()
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
	 * Convert UsersDisability key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((UsersDisability)arg2).getId();
	}

/*
       <p:selectOneMenu id="UsersDisabilityId" value="#{xxxUI.UsersDisability.xxxType}" converter="UsersDisabilityConvertor" style="width:95%">
         <f:selectItems value="#{UsersDisabilityUI.UsersDisabilityList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="UsersDisability" for="UsersDisabilityID"/>
        <p:autoComplete id="UsersDisabilityID" value="#{UsersDisabilityUI.UsersDisability.municipality}" completeMethod="#{UsersDisabilityUI.completeUsersDisability}"
                            var="rv" itemLabel="#{rv.UsersDisabilityDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="UsersDisabilityConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="UsersDisability" style="white-space: nowrap">#{rv.UsersDisabilityDescription}</p:column>
       </p:autoComplete>         
       
*/

}
