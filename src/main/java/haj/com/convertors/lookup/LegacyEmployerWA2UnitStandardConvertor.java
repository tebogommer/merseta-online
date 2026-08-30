package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyEmployerWA2UnitStandard;
import haj.com.service.lookup.LegacyEmployerWA2UnitStandardService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyEmployerWA2UnitStandardConvertor")
public class LegacyEmployerWA2UnitStandardConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyEmployerWA2UnitStandard
 	 * @author TechFinium 
 	 * @see    LegacyEmployerWA2UnitStandard
 	 * @return LegacyEmployerWA2UnitStandard
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyEmployerWA2UnitStandardService()
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
	 * Convert LegacyEmployerWA2UnitStandard key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyEmployerWA2UnitStandard)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyEmployerWA2UnitStandardId" value="#{xxxUI.LegacyEmployerWA2UnitStandard.xxxType}" converter="LegacyEmployerWA2UnitStandardConvertor" style="width:95%">
         <f:selectItems value="#{LegacyEmployerWA2UnitStandardUI.LegacyEmployerWA2UnitStandardList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyEmployerWA2UnitStandard" for="LegacyEmployerWA2UnitStandardID"/>
        <p:autoComplete id="LegacyEmployerWA2UnitStandardID" value="#{LegacyEmployerWA2UnitStandardUI.LegacyEmployerWA2UnitStandard.municipality}" completeMethod="#{LegacyEmployerWA2UnitStandardUI.completeLegacyEmployerWA2UnitStandard}"
                            var="rv" itemLabel="#{rv.LegacyEmployerWA2UnitStandardDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyEmployerWA2UnitStandardConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyEmployerWA2UnitStandard" style="white-space: nowrap">#{rv.LegacyEmployerWA2UnitStandardDescription}</p:column>
       </p:autoComplete>         
       
*/

}
