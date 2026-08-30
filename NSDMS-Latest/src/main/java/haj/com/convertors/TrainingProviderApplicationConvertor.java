package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.TrainingProviderApplication;
import haj.com.service.TrainingProviderApplicationService;

@FacesConverter(value = "TrainingProviderApplicationConvertor")
public class TrainingProviderApplicationConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a TrainingProviderApplication
 	 * @author TechFinium 
 	 * @see    TrainingProviderApplication
 	 * @return TrainingProviderApplication
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new TrainingProviderApplicationService()
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
	 * Convert TrainingProviderApplication key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((TrainingProviderApplication)arg2).getId();
	}

/*
       <p:selectOneMenu id="TrainingProviderApplicationId" value="#{xxxUI.TrainingProviderApplication.xxxType}" converter="TrainingProviderApplicationConvertor" style="width:95%">
         <f:selectItems value="#{TrainingProviderApplicationUI.TrainingProviderApplicationList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="TrainingProviderApplication" for="TrainingProviderApplicationID"/>
        <p:autoComplete id="TrainingProviderApplicationID" value="#{TrainingProviderApplicationUI.TrainingProviderApplication.municipality}" completeMethod="#{TrainingProviderApplicationUI.completeTrainingProviderApplication}"
                            var="rv" itemLabel="#{rv.TrainingProviderApplicationDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="TrainingProviderApplicationConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="TrainingProviderApplication" style="white-space: nowrap">#{rv.TrainingProviderApplicationDescription}</p:column>
       </p:autoComplete>         
       
*/

}
