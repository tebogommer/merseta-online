package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.TaskUsers;
import haj.com.service.TaskUsersService;

// TODO: Auto-generated Javadoc
/**
 * The Class TaskUsersConvertor.
 */
@FacesConverter(value = "TaskUsersConvertor")
public class TaskUsersConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a TaskUsers.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return TaskUsers
	 * @see    TaskUsers
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new TaskUsersService()
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
	 * Convert TaskUsers key to String object.
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
		return ""+((TaskUsers)arg2).getId();
	}

/*
       <p:selectOneMenu id="TaskUsersId" value="#{xxxUI.TaskUsers.xxxType}" converter="TaskUsersConvertor" style="width:95%">
         <f:selectItems value="#{TaskUsersUI.TaskUsersList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="TaskUsers" for="TaskUsersID"/>
        <p:autoComplete id="TaskUsersID" value="#{TaskUsersUI.TaskUsers.municipality}" completeMethod="#{TaskUsersUI.completeTaskUsers}"
                            var="rv" itemLabel="#{rv.TaskUsersDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="TaskUsersConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="TaskUsers" style="white-space: nowrap">#{rv.TaskUsersDescription}</p:column>
       </p:autoComplete>         
       
*/

}
