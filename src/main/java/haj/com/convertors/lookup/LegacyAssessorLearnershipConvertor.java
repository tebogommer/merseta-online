package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyAssessorLearnership;
import haj.com.service.lookup.LegacyAssessorLearnershipService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyAssessorLearnershipConvertor")
public class LegacyAssessorLearnershipConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyAssessorLearnership
 	 * @author TechFinium 
 	 * @see    LegacyAssessorLearnership
 	 * @return LegacyAssessorLearnership
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyAssessorLearnershipService()
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
	 * Convert LegacyAssessorLearnership key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyAssessorLearnership)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyAssessorLearnershipId" value="#{xxxUI.LegacyAssessorLearnership.xxxType}" converter="LegacyAssessorLearnershipConvertor" style="width:95%">
         <f:selectItems value="#{LegacyAssessorLearnershipUI.LegacyAssessorLearnershipList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyAssessorLearnership" for="LegacyAssessorLearnershipID"/>
        <p:autoComplete id="LegacyAssessorLearnershipID" value="#{LegacyAssessorLearnershipUI.LegacyAssessorLearnership.municipality}" completeMethod="#{LegacyAssessorLearnershipUI.completeLegacyAssessorLearnership}"
                            var="rv" itemLabel="#{rv.LegacyAssessorLearnershipDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyAssessorLearnershipConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyAssessorLearnership" style="white-space: nowrap">#{rv.LegacyAssessorLearnershipDescription}</p:column>
       </p:autoComplete>         
       
*/

}
