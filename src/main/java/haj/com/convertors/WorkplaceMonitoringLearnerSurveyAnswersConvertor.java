package haj.com.convertors;

import haj.com.entity.WorkplaceMonitoringLearnerSurveyAnswers;
import haj.com.service.WorkplaceMonitoringLearnerSurveyAnswersService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "WorkplaceMonitoringLearnerSurveyAnswersConvertor")
public class WorkplaceMonitoringLearnerSurveyAnswersConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a WorkplaceMonitoringLearnerSurveyAnswers
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringLearnerSurveyAnswers
 	 * @return WorkplaceMonitoringLearnerSurveyAnswers
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new WorkplaceMonitoringLearnerSurveyAnswersService()
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
	 * Convert WorkplaceMonitoringLearnerSurveyAnswers key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((WorkplaceMonitoringLearnerSurveyAnswers)arg2).getId();
	}

/*
       <p:selectOneMenu id="WorkplaceMonitoringLearnerSurveyAnswersId" value="#{xxxUI.WorkplaceMonitoringLearnerSurveyAnswers.xxxType}" converter="WorkplaceMonitoringLearnerSurveyAnswersConvertor" style="width:95%">
         <f:selectItems value="#{WorkplaceMonitoringLearnerSurveyAnswersUI.WorkplaceMonitoringLearnerSurveyAnswersList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="WorkplaceMonitoringLearnerSurveyAnswers" for="WorkplaceMonitoringLearnerSurveyAnswersID"/>
        <p:autoComplete id="WorkplaceMonitoringLearnerSurveyAnswersID" value="#{WorkplaceMonitoringLearnerSurveyAnswersUI.WorkplaceMonitoringLearnerSurveyAnswers.municipality}" completeMethod="#{WorkplaceMonitoringLearnerSurveyAnswersUI.completeWorkplaceMonitoringLearnerSurveyAnswers}"
                            var="rv" itemLabel="#{rv.WorkplaceMonitoringLearnerSurveyAnswersDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="WorkplaceMonitoringLearnerSurveyAnswersConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="WorkplaceMonitoringLearnerSurveyAnswers" style="white-space: nowrap">#{rv.WorkplaceMonitoringLearnerSurveyAnswersDescription}</p:column>
       </p:autoComplete>         
       
*/

}
