package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyOrganisationLevyPaying;
import haj.com.service.lookup.LegacyOrganisationLevyPayingService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyOrganisationLevyPayingConvertor")
public class LegacyOrganisationLevyPayingConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyOrganisationLevyPaying
 	 * @author TechFinium 
 	 * @see    LegacyOrganisationLevyPaying
 	 * @return LegacyOrganisationLevyPaying
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyOrganisationLevyPayingService()
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
	 * Convert LegacyOrganisationLevyPaying key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyOrganisationLevyPaying)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyOrganisationLevyPayingId" value="#{xxxUI.LegacyOrganisationLevyPaying.xxxType}" converter="LegacyOrganisationLevyPayingConvertor" style="width:95%">
         <f:selectItems value="#{LegacyOrganisationLevyPayingUI.LegacyOrganisationLevyPayingList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyOrganisationLevyPaying" for="LegacyOrganisationLevyPayingID"/>
        <p:autoComplete id="LegacyOrganisationLevyPayingID" value="#{LegacyOrganisationLevyPayingUI.LegacyOrganisationLevyPaying.municipality}" completeMethod="#{LegacyOrganisationLevyPayingUI.completeLegacyOrganisationLevyPaying}"
                            var="rv" itemLabel="#{rv.LegacyOrganisationLevyPayingDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyOrganisationLevyPayingConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyOrganisationLevyPaying" style="white-space: nowrap">#{rv.LegacyOrganisationLevyPayingDescription}</p:column>
       </p:autoComplete>         
       
*/

}
