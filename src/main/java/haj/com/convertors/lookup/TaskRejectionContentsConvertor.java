package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.TaskRejectionContents;
import haj.com.service.lookup.TaskRejectionContentsService;

// TODO: Auto-generated Javadoc
/**
 * The Class TaskRejectionContentsConvertor.
 */
@FacesConverter(value = "TaskRejectionContentsConvertor")
public class TaskRejectionContentsConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a TaskRejectionContents.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return TaskRejectionContents
	 * @see    TaskRejectionContents
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new TaskRejectionContentsService()
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
	 * Convert TaskRejectionContents key to String object.
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
		return ""+((TaskRejectionContents)arg2).getId();
	}

/*
       <p:selectOneMenu id="TaskRejectionContentsId" value="#{xxxUI.TaskRejectionContents.xxxType}" converter="TaskRejectionContentsConvertor" style="width:95%">
         <f:selectItems value="#{TaskRejectionContentsUI.TaskRejectionContentsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="TaskRejectionContents" for="TaskRejectionContentsID"/>
        <p:autoComplete id="TaskRejectionContentsID" value="#{TaskRejectionContentsUI.TaskRejectionContents.municipality}" completeMethod="#{TaskRejectionContentsUI.completeTaskRejectionContents}"
                            var="rv" itemLabel="#{rv.TaskRejectionContentsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="TaskRejectionContentsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="TaskRejectionContents" style="white-space: nowrap">#{rv.TaskRejectionContentsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
