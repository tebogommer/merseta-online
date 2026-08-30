package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.UsersRole;
import haj.com.service.UsersRoleService;

// TODO: Auto-generated Javadoc
/**
 * The Class UsersRoleConvertor.
 */
@FacesConverter(value = "UsersRoleConvertor")
public class UsersRoleConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a UsersRole.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return UsersRole
	 * @see    UsersRole
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new UsersRoleService()
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
	 * Convert UsersRole key to String object.
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
		return ""+((UsersRole)arg2).getId();
	}

/*
       <p:selectOneMenu id="UsersRoleId" value="#{xxxUI.UsersRole.xxxType}" converter="UsersRoleConvertor" style="width:95%">
         <f:selectItems value="#{UsersRoleUI.UsersRoleList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="UsersRole" for="UsersRoleID"/>
        <p:autoComplete id="UsersRoleID" value="#{UsersRoleUI.UsersRole.municipality}" completeMethod="#{UsersRoleUI.completeUsersRole}"
                            var="rv" itemLabel="#{rv.UsersRoleDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="UsersRoleConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="UsersRole" style="white-space: nowrap">#{rv.UsersRoleDescription}</p:column>
       </p:autoComplete>         
       
*/

}
