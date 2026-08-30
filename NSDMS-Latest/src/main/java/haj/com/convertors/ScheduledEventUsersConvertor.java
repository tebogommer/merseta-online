package haj.com.convertors;

import haj.com.entity.ScheduledEventUsers;
import haj.com.service.ScheduledEventUsersService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "ScheduledEventUsersConvertor")
public class ScheduledEventUsersConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a ScheduledEventUsers
 	 * @author TechFinium 
 	 * @see    ScheduledEventUsers
 	 * @return ScheduledEventUsers
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new ScheduledEventUsersService()
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
	 * Convert ScheduledEventUsers key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((ScheduledEventUsers)arg2).getId();
	}

/*
       <p:selectOneMenu id="ScheduledEventUsersId" value="#{xxxUI.ScheduledEventUsers.xxxType}" converter="ScheduledEventUsersConvertor" style="width:95%">
         <f:selectItems value="#{ScheduledEventUsersUI.ScheduledEventUsersList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="ScheduledEventUsers" for="ScheduledEventUsersID"/>
        <p:autoComplete id="ScheduledEventUsersID" value="#{ScheduledEventUsersUI.ScheduledEventUsers.municipality}" completeMethod="#{ScheduledEventUsersUI.completeScheduledEventUsers}"
                            var="rv" itemLabel="#{rv.ScheduledEventUsersDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="ScheduledEventUsersConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="ScheduledEventUsers" style="white-space: nowrap">#{rv.ScheduledEventUsersDescription}</p:column>
       </p:autoComplete>         
       
*/

}
