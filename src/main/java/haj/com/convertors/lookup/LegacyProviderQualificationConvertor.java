package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyProviderQualification;
import haj.com.service.lookup.LegacyProviderQualificationService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyProviderQualificationConvertor")
public class LegacyProviderQualificationConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyProviderQualification
 	 * @author TechFinium 
 	 * @see    LegacyProviderQualification
 	 * @return LegacyProviderQualification
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyProviderQualificationService()
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
	 * Convert LegacyProviderQualification key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyProviderQualification)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyProviderQualificationId" value="#{xxxUI.LegacyProviderQualification.xxxType}" converter="LegacyProviderQualificationConvertor" style="width:95%">
         <f:selectItems value="#{LegacyProviderQualificationUI.LegacyProviderQualificationList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyProviderQualification" for="LegacyProviderQualificationID"/>
        <p:autoComplete id="LegacyProviderQualificationID" value="#{LegacyProviderQualificationUI.LegacyProviderQualification.municipality}" completeMethod="#{LegacyProviderQualificationUI.completeLegacyProviderQualification}"
                            var="rv" itemLabel="#{rv.LegacyProviderQualificationDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyProviderQualificationConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyProviderQualification" style="white-space: nowrap">#{rv.LegacyProviderQualificationDescription}</p:column>
       </p:autoComplete>         
       
*/

}
