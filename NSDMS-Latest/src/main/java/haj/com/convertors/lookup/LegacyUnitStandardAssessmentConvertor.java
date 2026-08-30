package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyUnitStandardAssessment;
import haj.com.service.lookup.LegacyUnitStandardAssessmentService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyUnitStandardAssessmentConvertor")
public class LegacyUnitStandardAssessmentConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyUnitStandardAssessment
 	 * @author TechFinium 
 	 * @see    LegacyUnitStandardAssessment
 	 * @return LegacyUnitStandardAssessment
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyUnitStandardAssessmentService()
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
	 * Convert LegacyUnitStandardAssessment key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyUnitStandardAssessment)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyUnitStandardAssessmentId" value="#{xxxUI.LegacyUnitStandardAssessment.xxxType}" converter="LegacyUnitStandardAssessmentConvertor" style="width:95%">
         <f:selectItems value="#{LegacyUnitStandardAssessmentUI.LegacyUnitStandardAssessmentList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyUnitStandardAssessment" for="LegacyUnitStandardAssessmentID"/>
        <p:autoComplete id="LegacyUnitStandardAssessmentID" value="#{LegacyUnitStandardAssessmentUI.LegacyUnitStandardAssessment.municipality}" completeMethod="#{LegacyUnitStandardAssessmentUI.completeLegacyUnitStandardAssessment}"
                            var="rv" itemLabel="#{rv.LegacyUnitStandardAssessmentDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyUnitStandardAssessmentConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyUnitStandardAssessment" style="white-space: nowrap">#{rv.LegacyUnitStandardAssessmentDescription}</p:column>
       </p:autoComplete>         
       
*/

}
