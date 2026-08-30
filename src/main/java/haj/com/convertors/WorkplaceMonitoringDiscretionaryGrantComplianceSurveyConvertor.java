package haj.com.convertors;

import haj.com.entity.WorkplaceMonitoringDiscretionaryGrantComplianceSurvey;
import haj.com.service.WorkplaceMonitoringDiscretionaryGrantComplianceSurveyService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "WorkplaceMonitoringDiscretionaryGrantComplianceSurveyConvertor")
public class WorkplaceMonitoringDiscretionaryGrantComplianceSurveyConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a WorkplaceMonitoringDiscretionaryGrantComplianceSurvey
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringDiscretionaryGrantComplianceSurvey
 	 * @return WorkplaceMonitoringDiscretionaryGrantComplianceSurvey
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new WorkplaceMonitoringDiscretionaryGrantComplianceSurveyService()
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
	 * Convert WorkplaceMonitoringDiscretionaryGrantComplianceSurvey key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((WorkplaceMonitoringDiscretionaryGrantComplianceSurvey)arg2).getId();
	}

/*
       <p:selectOneMenu id="WorkplaceMonitoringDiscretionaryGrantComplianceSurveyId" value="#{xxxUI.WorkplaceMonitoringDiscretionaryGrantComplianceSurvey.xxxType}" converter="WorkplaceMonitoringDiscretionaryGrantComplianceSurveyConvertor" style="width:95%">
         <f:selectItems value="#{WorkplaceMonitoringDiscretionaryGrantComplianceSurveyUI.WorkplaceMonitoringDiscretionaryGrantComplianceSurveyList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="WorkplaceMonitoringDiscretionaryGrantComplianceSurvey" for="WorkplaceMonitoringDiscretionaryGrantComplianceSurveyID"/>
        <p:autoComplete id="WorkplaceMonitoringDiscretionaryGrantComplianceSurveyID" value="#{WorkplaceMonitoringDiscretionaryGrantComplianceSurveyUI.WorkplaceMonitoringDiscretionaryGrantComplianceSurvey.municipality}" completeMethod="#{WorkplaceMonitoringDiscretionaryGrantComplianceSurveyUI.completeWorkplaceMonitoringDiscretionaryGrantComplianceSurvey}"
                            var="rv" itemLabel="#{rv.WorkplaceMonitoringDiscretionaryGrantComplianceSurveyDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="WorkplaceMonitoringDiscretionaryGrantComplianceSurveyConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="WorkplaceMonitoringDiscretionaryGrantComplianceSurvey" style="white-space: nowrap">#{rv.WorkplaceMonitoringDiscretionaryGrantComplianceSurveyDescription}</p:column>
       </p:autoComplete>         
       
*/

}
