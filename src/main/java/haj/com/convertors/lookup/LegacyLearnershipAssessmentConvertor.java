package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyLearnershipAssessment;
import haj.com.service.lookup.LegacyLearnershipAssessmentService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyLearnershipAssessmentConvertor")
public class LegacyLearnershipAssessmentConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyLearnershipAssessment
 	 * @author TechFinium 
 	 * @see    LegacyLearnershipAssessment
 	 * @return LegacyLearnershipAssessment
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyLearnershipAssessmentService()
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
	 * Convert LegacyLearnershipAssessment key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyLearnershipAssessment)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyLearnershipAssessmentId" value="#{xxxUI.LegacyLearnershipAssessment.xxxType}" converter="LegacyLearnershipAssessmentConvertor" style="width:95%">
         <f:selectItems value="#{LegacyLearnershipAssessmentUI.LegacyLearnershipAssessmentList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyLearnershipAssessment" for="LegacyLearnershipAssessmentID"/>
        <p:autoComplete id="LegacyLearnershipAssessmentID" value="#{LegacyLearnershipAssessmentUI.LegacyLearnershipAssessment.municipality}" completeMethod="#{LegacyLearnershipAssessmentUI.completeLegacyLearnershipAssessment}"
                            var="rv" itemLabel="#{rv.LegacyLearnershipAssessmentDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyLearnershipAssessmentConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyLearnershipAssessment" style="white-space: nowrap">#{rv.LegacyLearnershipAssessmentDescription}</p:column>
       </p:autoComplete>         
       
*/

}
