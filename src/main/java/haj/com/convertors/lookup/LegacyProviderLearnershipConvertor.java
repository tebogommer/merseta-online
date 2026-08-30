package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyProviderLearnership;
import haj.com.service.lookup.LegacyProviderLearnershipService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyProviderLearnershipConvertor")
public class LegacyProviderLearnershipConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyProviderLearnership
 	 * @author TechFinium 
 	 * @see    LegacyProviderLearnership
 	 * @return LegacyProviderLearnership
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyProviderLearnershipService()
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
	 * Convert LegacyProviderLearnership key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyProviderLearnership)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyProviderLearnershipId" value="#{xxxUI.LegacyProviderLearnership.xxxType}" converter="LegacyProviderLearnershipConvertor" style="width:95%">
         <f:selectItems value="#{LegacyProviderLearnershipUI.LegacyProviderLearnershipList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyProviderLearnership" for="LegacyProviderLearnershipID"/>
        <p:autoComplete id="LegacyProviderLearnershipID" value="#{LegacyProviderLearnershipUI.LegacyProviderLearnership.municipality}" completeMethod="#{LegacyProviderLearnershipUI.completeLegacyProviderLearnership}"
                            var="rv" itemLabel="#{rv.LegacyProviderLearnershipDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyProviderLearnershipConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyProviderLearnership" style="white-space: nowrap">#{rv.LegacyProviderLearnershipDescription}</p:column>
       </p:autoComplete>         
       
*/

}
