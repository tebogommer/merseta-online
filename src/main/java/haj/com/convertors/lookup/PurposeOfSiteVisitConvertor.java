package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.PurposeOfSiteVisit;
import haj.com.service.lookup.PurposeOfSiteVisitService;

@FacesConverter(value = "PurposeOfSiteVisitConvertor")
public class PurposeOfSiteVisitConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a PurposeOfSiteVisit
 	 * @author TechFinium 
 	 * @see    PurposeOfSiteVisit
 	 * @return PurposeOfSiteVisit
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new PurposeOfSiteVisitService()
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
	 * Convert PurposeOfSiteVisit key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((PurposeOfSiteVisit)arg2).getId();
	}

/*
       <p:selectOneMenu id="PurposeOfSiteVisitId" value="#{xxxUI.PurposeOfSiteVisit.xxxType}" converter="PurposeOfSiteVisitConvertor" style="width:95%">
         <f:selectItems value="#{PurposeOfSiteVisitUI.PurposeOfSiteVisitList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="PurposeOfSiteVisit" for="PurposeOfSiteVisitID"/>
        <p:autoComplete id="PurposeOfSiteVisitID" value="#{PurposeOfSiteVisitUI.PurposeOfSiteVisit.municipality}" completeMethod="#{PurposeOfSiteVisitUI.completePurposeOfSiteVisit}"
                            var="rv" itemLabel="#{rv.PurposeOfSiteVisitDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="PurposeOfSiteVisitConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="PurposeOfSiteVisit" style="white-space: nowrap">#{rv.PurposeOfSiteVisitDescription}</p:column>
       </p:autoComplete>         
       
*/

}
