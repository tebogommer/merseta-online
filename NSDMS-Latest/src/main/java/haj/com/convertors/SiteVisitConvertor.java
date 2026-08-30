package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.SiteVisit;
import haj.com.service.SiteVisitService;

@FacesConverter(value = "SiteVisitConvertor")
public class SiteVisitConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SiteVisit
 	 * @author TechFinium 
 	 * @see    SiteVisit
 	 * @return SiteVisit
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SiteVisitService()
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
	 * Convert SiteVisit key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((SiteVisit)arg2).getId();
	}

/*
       <p:selectOneMenu id="SiteVisitId" value="#{xxxUI.SiteVisit.xxxType}" converter="SiteVisitConvertor" style="width:95%">
         <f:selectItems value="#{SiteVisitUI.SiteVisitList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SiteVisit" for="SiteVisitID"/>
        <p:autoComplete id="SiteVisitID" value="#{SiteVisitUI.SiteVisit.municipality}" completeMethod="#{SiteVisitUI.completeSiteVisit}"
                            var="rv" itemLabel="#{rv.SiteVisitDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SiteVisitConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SiteVisit" style="white-space: nowrap">#{rv.SiteVisitDescription}</p:column>
       </p:autoComplete>         
       
*/

}
