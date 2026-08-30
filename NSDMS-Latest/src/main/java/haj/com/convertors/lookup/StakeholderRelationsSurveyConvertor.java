package haj.com.convertors.lookup;

import haj.com.entity.lookup.StakeholderRelationsSurvey;
import haj.com.service.lookup.StakeholderRelationsSurveyService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "StakeholderRelationsSurveyConvertor")
public class StakeholderRelationsSurveyConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a StakeholderRelationsSurvey
 	 * @author TechFinium 
 	 * @see    StakeholderRelationsSurvey
 	 * @return StakeholderRelationsSurvey
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new StakeholderRelationsSurveyService()
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
	 * Convert StakeholderRelationsSurvey key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((StakeholderRelationsSurvey)arg2).getId();
	}

/*
       <p:selectOneMenu id="StakeholderRelationsSurveyId" value="#{xxxUI.StakeholderRelationsSurvey.xxxType}" converter="StakeholderRelationsSurveyConvertor" style="width:95%">
         <f:selectItems value="#{StakeholderRelationsSurveyUI.StakeholderRelationsSurveyList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="StakeholderRelationsSurvey" for="StakeholderRelationsSurveyID"/>
        <p:autoComplete id="StakeholderRelationsSurveyID" value="#{StakeholderRelationsSurveyUI.StakeholderRelationsSurvey.municipality}" completeMethod="#{StakeholderRelationsSurveyUI.completeStakeholderRelationsSurvey}"
                            var="rv" itemLabel="#{rv.StakeholderRelationsSurveyDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="StakeholderRelationsSurveyConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="StakeholderRelationsSurvey" style="white-space: nowrap">#{rv.StakeholderRelationsSurveyDescription}</p:column>
       </p:autoComplete>         
       
*/

}
