package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyModeratorUnitStandard;
import haj.com.service.lookup.LegacyModeratorUnitStandardService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyModeratorUnitStandardConvertor")
public class LegacyModeratorUnitStandardConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyModeratorUnitStandard
 	 * @author TechFinium 
 	 * @see    LegacyModeratorUnitStandard
 	 * @return LegacyModeratorUnitStandard
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyModeratorUnitStandardService()
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
	 * Convert LegacyModeratorUnitStandard key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyModeratorUnitStandard)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyModeratorUnitStandardId" value="#{xxxUI.LegacyModeratorUnitStandard.xxxType}" converter="LegacyModeratorUnitStandardConvertor" style="width:95%">
         <f:selectItems value="#{LegacyModeratorUnitStandardUI.LegacyModeratorUnitStandardList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyModeratorUnitStandard" for="LegacyModeratorUnitStandardID"/>
        <p:autoComplete id="LegacyModeratorUnitStandardID" value="#{LegacyModeratorUnitStandardUI.LegacyModeratorUnitStandard.municipality}" completeMethod="#{LegacyModeratorUnitStandardUI.completeLegacyModeratorUnitStandard}"
                            var="rv" itemLabel="#{rv.LegacyModeratorUnitStandardDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyModeratorUnitStandardConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyModeratorUnitStandard" style="white-space: nowrap">#{rv.LegacyModeratorUnitStandardDescription}</p:column>
       </p:autoComplete>         
       
*/

}
