package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyProviderUnitStandard;
import haj.com.service.lookup.LegacyProviderUnitStandardService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyProviderUnitStandardConvertor")
public class LegacyProviderUnitStandardConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyProviderUnitStandard
 	 * @author TechFinium 
 	 * @see    LegacyProviderUnitStandard
 	 * @return LegacyProviderUnitStandard
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyProviderUnitStandardService()
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
	 * Convert LegacyProviderUnitStandard key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyProviderUnitStandard)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyProviderUnitStandardId" value="#{xxxUI.LegacyProviderUnitStandard.xxxType}" converter="LegacyProviderUnitStandardConvertor" style="width:95%">
         <f:selectItems value="#{LegacyProviderUnitStandardUI.LegacyProviderUnitStandardList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyProviderUnitStandard" for="LegacyProviderUnitStandardID"/>
        <p:autoComplete id="LegacyProviderUnitStandardID" value="#{LegacyProviderUnitStandardUI.LegacyProviderUnitStandard.municipality}" completeMethod="#{LegacyProviderUnitStandardUI.completeLegacyProviderUnitStandard}"
                            var="rv" itemLabel="#{rv.LegacyProviderUnitStandardDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyProviderUnitStandardConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyProviderUnitStandard" style="white-space: nowrap">#{rv.LegacyProviderUnitStandardDescription}</p:column>
       </p:autoComplete>         
       
*/

}
