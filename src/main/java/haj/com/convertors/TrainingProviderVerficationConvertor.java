package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.TrainingProviderVerfication;
import haj.com.service.TrainingProviderVerficationService;

@FacesConverter(value = "TrainingProviderVerficationConvertor")
public class TrainingProviderVerficationConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a TrainingProviderVerfication
 	 * @author TechFinium 
 	 * @see    TrainingProviderVerfication
 	 * @return TrainingProviderVerfication
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new TrainingProviderVerficationService()
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
	 * Convert TrainingProviderVerfication key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((TrainingProviderVerfication)arg2).getId();
	}

/*
       <p:selectOneMenu id="TrainingProviderVerficationId" value="#{xxxUI.TrainingProviderVerfication.xxxType}" converter="TrainingProviderVerficationConvertor" style="width:95%">
         <f:selectItems value="#{TrainingProviderVerficationUI.TrainingProviderVerficationList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="TrainingProviderVerfication" for="TrainingProviderVerficationID"/>
        <p:autoComplete id="TrainingProviderVerficationID" value="#{TrainingProviderVerficationUI.TrainingProviderVerfication.municipality}" completeMethod="#{TrainingProviderVerficationUI.completeTrainingProviderVerfication}"
                            var="rv" itemLabel="#{rv.TrainingProviderVerficationDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="TrainingProviderVerficationConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="TrainingProviderVerfication" style="white-space: nowrap">#{rv.TrainingProviderVerficationDescription}</p:column>
       </p:autoComplete>         
       
*/

}
