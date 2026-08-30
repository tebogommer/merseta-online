package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.UserQualifications;
import haj.com.service.UserQualificationsService;

// TODO: Auto-generated Javadoc
/**
 * The Class UserQualificationsConvertor.
 */
@FacesConverter(value = "UserQualificationsConvertor")
public class UserQualificationsConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a UserQualifications.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return UserQualifications
	 * @see    UserQualifications
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new UserQualificationsService()
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
	 * Convert UserQualifications key to String object.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param arg2 the arg 2
	 * @return String
	 * @see    String
	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return ""+((UserQualifications)arg2).getId();
	}

/*
       <p:selectOneMenu id="UserQualificationsId" value="#{xxxUI.UserQualifications.xxxType}" converter="UserQualificationsConvertor" style="width:95%">
         <f:selectItems value="#{UserQualificationsUI.UserQualificationsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="UserQualifications" for="UserQualificationsID"/>
        <p:autoComplete id="UserQualificationsID" value="#{UserQualificationsUI.UserQualifications.municipality}" completeMethod="#{UserQualificationsUI.completeUserQualifications}"
                            var="rv" itemLabel="#{rv.UserQualificationsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="UserQualificationsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="UserQualifications" style="white-space: nowrap">#{rv.UserQualificationsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
