package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.DgVerificationSites;
import haj.com.service.DgVerificationSitesService;

@FacesConverter(value = "DgVerificationSitesConvertor")
public class DgVerificationSitesConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a DgVerificationSites
 	 * @author TechFinium 
 	 * @see    DgVerificationSites
 	 * @return DgVerificationSites
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new DgVerificationSitesService()
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
	 * Convert DgVerificationSites key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((DgVerificationSites)arg2).getId();
	}

/*
       <p:selectOneMenu id="DgVerificationSitesId" value="#{xxxUI.DgVerificationSites.xxxType}" converter="DgVerificationSitesConvertor" style="width:95%">
         <f:selectItems value="#{DgVerificationSitesUI.DgVerificationSitesList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="DgVerificationSites" for="DgVerificationSitesID"/>
        <p:autoComplete id="DgVerificationSitesID" value="#{DgVerificationSitesUI.DgVerificationSites.municipality}" completeMethod="#{DgVerificationSitesUI.completeDgVerificationSites}"
                            var="rv" itemLabel="#{rv.DgVerificationSitesDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="DgVerificationSitesConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="DgVerificationSites" style="white-space: nowrap">#{rv.DgVerificationSitesDescription}</p:column>
       </p:autoComplete>         
       
*/

}
