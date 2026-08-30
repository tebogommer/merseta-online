package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyDTTCUnitStandard;
import haj.com.service.lookup.LegacyDTTCUnitStandardService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyDTTCUnitStandardConvertor")
public class LegacyDTTCUnitStandardConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyDTTCUnitStandard
 	 * @author TechFinium 
 	 * @see    LegacyDTTCUnitStandard
 	 * @return LegacyDTTCUnitStandard
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyDTTCUnitStandardService()
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
	 * Convert LegacyDTTCUnitStandard key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyDTTCUnitStandard)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyDTTCUnitStandardId" value="#{xxxUI.LegacyDTTCUnitStandard.xxxType}" converter="LegacyDTTCUnitStandardConvertor" style="width:95%">
         <f:selectItems value="#{LegacyDTTCUnitStandardUI.LegacyDTTCUnitStandardList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyDTTCUnitStandard" for="LegacyDTTCUnitStandardID"/>
        <p:autoComplete id="LegacyDTTCUnitStandardID" value="#{LegacyDTTCUnitStandardUI.LegacyDTTCUnitStandard.municipality}" completeMethod="#{LegacyDTTCUnitStandardUI.completeLegacyDTTCUnitStandard}"
                            var="rv" itemLabel="#{rv.LegacyDTTCUnitStandardDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyDTTCUnitStandardConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyDTTCUnitStandard" style="white-space: nowrap">#{rv.LegacyDTTCUnitStandardDescription}</p:column>
       </p:autoComplete>         
       
*/

}
