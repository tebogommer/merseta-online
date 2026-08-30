package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.AssessorModeratorApplication;
import haj.com.service.AssessorModeratorApplicationService;

// TODO: Auto-generated Javadoc
/**
 * The Class AssessorModeratorApplicationConvertor.
 */
@FacesConverter(value = "AssessorModeratorApplicationConvertor")
public class AssessorModeratorApplicationConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a AssessorModeratorApplication.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return AssessorModeratorApplication
	 * @see    AssessorModeratorApplication
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new AssessorModeratorApplicationService()
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
	 * Convert AssessorModeratorApplication key to String object.
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
		if (arg2 == null)
			return "";
		if (arg2 instanceof String)
			return arg2.toString();
		return ""+((AssessorModeratorApplication)arg2).getId();
	}

/*
       <p:selectOneMenu id="AssessorModeratorApplicationId" value="#{xxxUI.AssessorModeratorApplication.xxxType}" converter="AssessorModeratorApplicationConvertor" style="width:95%">
         <f:selectItems value="#{AssessorModeratorApplicationUI.AssessorModeratorApplicationList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="AssessorModeratorApplication" for="AssessorModeratorApplicationID"/>
        <p:autoComplete id="AssessorModeratorApplicationID" value="#{AssessorModeratorApplicationUI.AssessorModeratorApplication.municipality}" completeMethod="#{AssessorModeratorApplicationUI.completeAssessorModeratorApplication}"
                            var="rv" itemLabel="#{rv.AssessorModeratorApplicationDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="AssessorModeratorApplicationConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="AssessorModeratorApplication" style="white-space: nowrap">#{rv.AssessorModeratorApplicationDescription}</p:column>
       </p:autoComplete>         
       
*/

}
