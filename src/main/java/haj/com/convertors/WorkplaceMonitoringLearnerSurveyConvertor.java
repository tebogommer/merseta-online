package haj.com.convertors;

import haj.com.entity.WorkplaceMonitoringLearnerSurvey;
import haj.com.service.WorkplaceMonitoringLearnerSurveyService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "WorkplaceMonitoringLearnerSurveyConvertor")
public class WorkplaceMonitoringLearnerSurveyConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a WorkplaceMonitoringLearnerSurvey
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringLearnerSurvey
 	 * @return WorkplaceMonitoringLearnerSurvey
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new WorkplaceMonitoringLearnerSurveyService()
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
	 * Convert WorkplaceMonitoringLearnerSurvey key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((WorkplaceMonitoringLearnerSurvey)arg2).getId();
	}

/*
       <p:selectOneMenu id="WorkplaceMonitoringLearnerSurveyId" value="#{xxxUI.WorkplaceMonitoringLearnerSurvey.xxxType}" converter="WorkplaceMonitoringLearnerSurveyConvertor" style="width:95%">
         <f:selectItems value="#{WorkplaceMonitoringLearnerSurveyUI.WorkplaceMonitoringLearnerSurveyList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="WorkplaceMonitoringLearnerSurvey" for="WorkplaceMonitoringLearnerSurveyID"/>
        <p:autoComplete id="WorkplaceMonitoringLearnerSurveyID" value="#{WorkplaceMonitoringLearnerSurveyUI.WorkplaceMonitoringLearnerSurvey.municipality}" completeMethod="#{WorkplaceMonitoringLearnerSurveyUI.completeWorkplaceMonitoringLearnerSurvey}"
                            var="rv" itemLabel="#{rv.WorkplaceMonitoringLearnerSurveyDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="WorkplaceMonitoringLearnerSurveyConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="WorkplaceMonitoringLearnerSurvey" style="white-space: nowrap">#{rv.WorkplaceMonitoringLearnerSurveyDescription}</p:column>
       </p:autoComplete>         
       
*/

}
