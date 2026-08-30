package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyLearnershipAssessment3;
import haj.com.service.lookup.LegacyLearnershipAssessment3Service;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyLearnershipAssessment3Convertor")
public class LegacyLearnershipAssessment3Convertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyLearnershipAssessment3
 	 * @author TechFinium 
 	 * @see    LegacyLearnershipAssessment3
 	 * @return LegacyLearnershipAssessment3
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyLearnershipAssessment3Service()
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
	 * Convert LegacyLearnershipAssessment3 key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyLearnershipAssessment3)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyLearnershipAssessment3Id" value="#{xxxUI.LegacyLearnershipAssessment3.xxxType}" converter="LegacyLearnershipAssessment3Convertor" style="width:95%">
         <f:selectItems value="#{LegacyLearnershipAssessment3UI.LegacyLearnershipAssessment3List}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyLearnershipAssessment3" for="LegacyLearnershipAssessment3ID"/>
        <p:autoComplete id="LegacyLearnershipAssessment3ID" value="#{LegacyLearnershipAssessment3UI.LegacyLearnershipAssessment3.municipality}" completeMethod="#{LegacyLearnershipAssessment3UI.completeLegacyLearnershipAssessment3}"
                            var="rv" itemLabel="#{rv.LegacyLearnershipAssessment3Description}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyLearnershipAssessment3Convertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyLearnershipAssessment3" style="white-space: nowrap">#{rv.LegacyLearnershipAssessment3Description}</p:column>
       </p:autoComplete>         
       
*/

}
