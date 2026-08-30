package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.TrainingProviderMonitoring;
import haj.com.service.TrainingProviderMonitoringService;

@FacesConverter(value = "TrainingProviderMonitoringConvertor")
public class TrainingProviderMonitoringConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a TrainingProviderMonitoring
 	 * @author TechFinium 
 	 * @see    TrainingProviderMonitoring
 	 * @return TrainingProviderMonitoring
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new TrainingProviderMonitoringService()
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
	 * Convert TrainingProviderMonitoring key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((TrainingProviderMonitoring)arg2).getId();
	}

/*
       <p:selectOneMenu id="TrainingProviderMonitoringId" value="#{xxxUI.TrainingProviderMonitoring.xxxType}" converter="TrainingProviderMonitoringConvertor" style="width:95%">
         <f:selectItems value="#{TrainingProviderMonitoringUI.TrainingProviderMonitoringList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="TrainingProviderMonitoring" for="TrainingProviderMonitoringID"/>
        <p:autoComplete id="TrainingProviderMonitoringID" value="#{TrainingProviderMonitoringUI.TrainingProviderMonitoring.municipality}" completeMethod="#{TrainingProviderMonitoringUI.completeTrainingProviderMonitoring}"
                            var="rv" itemLabel="#{rv.TrainingProviderMonitoringDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="TrainingProviderMonitoringConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="TrainingProviderMonitoring" style="white-space: nowrap">#{rv.TrainingProviderMonitoringDescription}</p:column>
       </p:autoComplete>         
       
*/

}
