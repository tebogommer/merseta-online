package haj.com.convertors;

import haj.com.entity.TrainingProviderLearnership;
import haj.com.service.TrainingProviderLearnershipService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "TrainingProviderLearnershipConvertor")
public class TrainingProviderLearnershipConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a TrainingProviderLearnership
 	 * @author TechFinium 
 	 * @see    TrainingProviderLearnership
 	 * @return TrainingProviderLearnership
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new TrainingProviderLearnershipService()
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
	 * Convert TrainingProviderLearnership key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((TrainingProviderLearnership)arg2).getId();
	}

/*
       <p:selectOneMenu id="TrainingProviderLearnershipId" value="#{xxxUI.TrainingProviderLearnership.xxxType}" converter="TrainingProviderLearnershipConvertor" style="width:95%">
         <f:selectItems value="#{TrainingProviderLearnershipUI.TrainingProviderLearnershipList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="TrainingProviderLearnership" for="TrainingProviderLearnershipID"/>
        <p:autoComplete id="TrainingProviderLearnershipID" value="#{TrainingProviderLearnershipUI.TrainingProviderLearnership.municipality}" completeMethod="#{TrainingProviderLearnershipUI.completeTrainingProviderLearnership}"
                            var="rv" itemLabel="#{rv.TrainingProviderLearnershipDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="TrainingProviderLearnershipConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="TrainingProviderLearnership" style="white-space: nowrap">#{rv.TrainingProviderLearnershipDescription}</p:column>
       </p:autoComplete>         
       
*/

}
