package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.Tasks;
import haj.com.service.TasksService;

// TODO: Auto-generated Javadoc
/**
 * The Class TasksConvertor.
 */
@FacesConverter(value = "TasksConvertor")
public class TasksConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a Tasks.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return Tasks
	 * @see    Tasks
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return TasksService.instance().findByKey(Integer.valueOf(value));
			} catch (NumberFormatException e) {
				logger.fatal(e);
			} catch (Exception e) {
				logger.fatal(e);
			}

		}
	    return null;
	}


	/**
	 * Convert Tasks key to String object.
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
		return ""+((Tasks)arg2).getId();
	}

/*
       <p:selectOneMenu id="TasksId" value="#{xxxUI.Tasks.xxxType}" converter="TasksConvertor" style="width:95%">
         <f:selectItems value="#{TasksUI.TasksList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="Tasks" for="TasksID"/>
        <p:autoComplete id="TasksID" value="#{TasksUI.Tasks.municipality}" completeMethod="#{TasksUI.completeTasks}"
                            var="rv" itemLabel="#{rv.TasksDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="TasksConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="Tasks" style="white-space: nowrap">#{rv.TasksDescription}</p:column>
       </p:autoComplete>         
       
*/

}
