package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyOrganisationSites;
import haj.com.service.lookup.LegacyOrganisationSitesService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyOrganisationSitesConvertor")
public class LegacyOrganisationSitesConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyOrganisationSites
 	 * @author TechFinium 
 	 * @see    LegacyOrganisationSites
 	 * @return LegacyOrganisationSites
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyOrganisationSitesService()
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
	 * Convert LegacyOrganisationSites key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyOrganisationSites)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyOrganisationSitesId" value="#{xxxUI.LegacyOrganisationSites.xxxType}" converter="LegacyOrganisationSitesConvertor" style="width:95%">
         <f:selectItems value="#{LegacyOrganisationSitesUI.LegacyOrganisationSitesList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyOrganisationSites" for="LegacyOrganisationSitesID"/>
        <p:autoComplete id="LegacyOrganisationSitesID" value="#{LegacyOrganisationSitesUI.LegacyOrganisationSites.municipality}" completeMethod="#{LegacyOrganisationSitesUI.completeLegacyOrganisationSites}"
                            var="rv" itemLabel="#{rv.LegacyOrganisationSitesDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyOrganisationSitesConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyOrganisationSites" style="white-space: nowrap">#{rv.LegacyOrganisationSitesDescription}</p:column>
       </p:autoComplete>         
       
*/

}
