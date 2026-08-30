package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyLearnershipAssessment2;
import haj.com.service.lookup.LegacyLearnershipAssessment2Service;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyLearnershipAssessment2Convertor")
public class LegacyLearnershipAssessment2Convertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyLearnershipAssessment2
 	 * @author TechFinium 
 	 * @see    LegacyLearnershipAssessment2
 	 * @return LegacyLearnershipAssessment2
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyLearnershipAssessment2Service()
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
	 * Convert LegacyLearnershipAssessment2 key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyLearnershipAssessment2)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyLearnershipAssessment2Id" value="#{xxxUI.LegacyLearnershipAssessment2.xxxType}" converter="LegacyLearnershipAssessment2Convertor" style="width:95%">
         <f:selectItems value="#{LegacyLearnershipAssessment2UI.LegacyLearnershipAssessment2List}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyLearnershipAssessment2" for="LegacyLearnershipAssessment2ID"/>
        <p:autoComplete id="LegacyLearnershipAssessment2ID" value="#{LegacyLearnershipAssessment2UI.LegacyLearnershipAssessment2.municipality}" completeMethod="#{LegacyLearnershipAssessment2UI.completeLegacyLearnershipAssessment2}"
                            var="rv" itemLabel="#{rv.LegacyLearnershipAssessment2Description}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyLearnershipAssessment2Convertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyLearnershipAssessment2" style="white-space: nowrap">#{rv.LegacyLearnershipAssessment2Description}</p:column>
       </p:autoComplete>         
       
*/

}
