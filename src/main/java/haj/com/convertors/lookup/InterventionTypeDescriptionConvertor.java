package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.InterventionTypeDescription;
import haj.com.service.lookup.InterventionTypeDescriptionService;

@FacesConverter(value = "InterventionTypeDescriptionConvertor")
public class InterventionTypeDescriptionConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a InterventionTypeDescription
 	 * @author TechFinium 
 	 * @see    InterventionTypeDescription
 	 * @return InterventionTypeDescription
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new InterventionTypeDescriptionService()
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
	 * Convert InterventionTypeDescription key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return ""+((InterventionTypeDescription)arg2).getId();
	}

/*
       <p:selectOneMenu id="InterventionTypeDescriptionId" value="#{xxxUI.InterventionTypeDescription.xxxType}" converter="InterventionTypeDescriptionConvertor" style="width:95%">
         <f:selectItems value="#{InterventionTypeDescriptionUI.InterventionTypeDescriptionList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="InterventionTypeDescription" for="InterventionTypeDescriptionID"/>
        <p:autoComplete id="InterventionTypeDescriptionID" value="#{InterventionTypeDescriptionUI.InterventionTypeDescription.municipality}" completeMethod="#{InterventionTypeDescriptionUI.completeInterventionTypeDescription}"
                            var="rv" itemLabel="#{rv.InterventionTypeDescriptionDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="InterventionTypeDescriptionConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="InterventionTypeDescription" style="white-space: nowrap">#{rv.InterventionTypeDescriptionDescription}</p:column>
       </p:autoComplete>         
       
*/

}
