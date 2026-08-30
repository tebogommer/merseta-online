package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyProviderAccreditation;
import haj.com.service.lookup.LegacyProviderAccreditationService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyProviderAccreditationConvertor")
public class LegacyProviderAccreditationConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyProviderAccreditation
 	 * @author TechFinium 
 	 * @see    LegacyProviderAccreditation
 	 * @return LegacyProviderAccreditation
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyProviderAccreditationService()
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
	 * Convert LegacyProviderAccreditation key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyProviderAccreditation)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyProviderAccreditationId" value="#{xxxUI.LegacyProviderAccreditation.xxxType}" converter="LegacyProviderAccreditationConvertor" style="width:95%">
         <f:selectItems value="#{LegacyProviderAccreditationUI.LegacyProviderAccreditationList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyProviderAccreditation" for="LegacyProviderAccreditationID"/>
        <p:autoComplete id="LegacyProviderAccreditationID" value="#{LegacyProviderAccreditationUI.LegacyProviderAccreditation.municipality}" completeMethod="#{LegacyProviderAccreditationUI.completeLegacyProviderAccreditation}"
                            var="rv" itemLabel="#{rv.LegacyProviderAccreditationDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyProviderAccreditationConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyProviderAccreditation" style="white-space: nowrap">#{rv.LegacyProviderAccreditationDescription}</p:column>
       </p:autoComplete>         
       
*/

}
