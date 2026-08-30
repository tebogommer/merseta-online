package haj.com.convertors.lookup;

import haj.com.entity.lookup.LearnerMonitoringSurvey;
import haj.com.service.lookup.LearnerMonitoringSurveyService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LearnerMonitoringSurveyConvertor")
public class LearnerMonitoringSurveyConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LearnerMonitoringSurvey
 	 * @author TechFinium 
 	 * @see    LearnerMonitoringSurvey
 	 * @return LearnerMonitoringSurvey
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LearnerMonitoringSurveyService()
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
	 * Convert LearnerMonitoringSurvey key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LearnerMonitoringSurvey)arg2).getId();
	}

/*
       <p:selectOneMenu id="LearnerMonitoringSurveyId" value="#{xxxUI.LearnerMonitoringSurvey.xxxType}" converter="LearnerMonitoringSurveyConvertor" style="width:95%">
         <f:selectItems value="#{LearnerMonitoringSurveyUI.LearnerMonitoringSurveyList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LearnerMonitoringSurvey" for="LearnerMonitoringSurveyID"/>
        <p:autoComplete id="LearnerMonitoringSurveyID" value="#{LearnerMonitoringSurveyUI.LearnerMonitoringSurvey.municipality}" completeMethod="#{LearnerMonitoringSurveyUI.completeLearnerMonitoringSurvey}"
                            var="rv" itemLabel="#{rv.LearnerMonitoringSurveyDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LearnerMonitoringSurveyConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LearnerMonitoringSurvey" style="white-space: nowrap">#{rv.LearnerMonitoringSurveyDescription}</p:column>
       </p:autoComplete>         
       
*/

}
