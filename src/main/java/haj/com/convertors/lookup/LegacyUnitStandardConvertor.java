package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyUnitStandard;
import haj.com.service.lookup.LegacyUnitStandardService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyUnitStandardConvertor")
public class LegacyUnitStandardConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyUnitStandard
 	 * @author TechFinium 
 	 * @see    LegacyUnitStandard
 	 * @return LegacyUnitStandard
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyUnitStandardService()
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
	 * Convert LegacyUnitStandard key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyUnitStandard)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyUnitStandardId" value="#{xxxUI.LegacyUnitStandard.xxxType}" converter="LegacyUnitStandardConvertor" style="width:95%">
         <f:selectItems value="#{LegacyUnitStandardUI.LegacyUnitStandardList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyUnitStandard" for="LegacyUnitStandardID"/>
        <p:autoComplete id="LegacyUnitStandardID" value="#{LegacyUnitStandardUI.LegacyUnitStandard.municipality}" completeMethod="#{LegacyUnitStandardUI.completeLegacyUnitStandard}"
                            var="rv" itemLabel="#{rv.LegacyUnitStandardDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyUnitStandardConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyUnitStandard" style="white-space: nowrap">#{rv.LegacyUnitStandardDescription}</p:column>
       </p:autoComplete>         
       
*/

}
