package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.PurposeOfSiteVisitSupport;
import haj.com.service.lookup.PurposeOfSiteVisitSupportService;

@FacesConverter(value = "PurposeOfSiteVisitSupportConvertor")
public class PurposeOfSiteVisitSupportConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a PurposeOfSiteVisitSupport
 	 * @author TechFinium 
 	 * @see    PurposeOfSiteVisitSupport
 	 * @return PurposeOfSiteVisitSupport
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new PurposeOfSiteVisitSupportService()
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
	 * Convert PurposeOfSiteVisitSupport key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((PurposeOfSiteVisitSupport)arg2).getId();
	}

/*
       <p:selectOneMenu id="PurposeOfSiteVisitSupportId" value="#{xxxUI.PurposeOfSiteVisitSupport.xxxType}" converter="PurposeOfSiteVisitSupportConvertor" style="width:95%">
         <f:selectItems value="#{PurposeOfSiteVisitSupportUI.PurposeOfSiteVisitSupportList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="PurposeOfSiteVisitSupport" for="PurposeOfSiteVisitSupportID"/>
        <p:autoComplete id="PurposeOfSiteVisitSupportID" value="#{PurposeOfSiteVisitSupportUI.PurposeOfSiteVisitSupport.municipality}" completeMethod="#{PurposeOfSiteVisitSupportUI.completePurposeOfSiteVisitSupport}"
                            var="rv" itemLabel="#{rv.PurposeOfSiteVisitSupportDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="PurposeOfSiteVisitSupportConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="PurposeOfSiteVisitSupport" style="white-space: nowrap">#{rv.PurposeOfSiteVisitSupportDescription}</p:column>
       </p:autoComplete>         
       
*/

}
