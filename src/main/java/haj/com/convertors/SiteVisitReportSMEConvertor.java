package haj.com.convertors;

import haj.com.entity.SiteVisitReportSME;
import haj.com.service.SiteVisitReportSMEService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "SiteVisitReportSMEConvertor")
public class SiteVisitReportSMEConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SiteVisitReportSME
 	 * @author TechFinium 
 	 * @see    SiteVisitReportSME
 	 * @return SiteVisitReportSME
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SiteVisitReportSMEService()
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
	 * Convert SiteVisitReportSME key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((SiteVisitReportSME)arg2).getId();
	}

/*
       <p:selectOneMenu id="SiteVisitReportSMEId" value="#{xxxUI.SiteVisitReportSME.xxxType}" converter="SiteVisitReportSMEConvertor" style="width:95%">
         <f:selectItems value="#{SiteVisitReportSMEUI.SiteVisitReportSMEList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SiteVisitReportSME" for="SiteVisitReportSMEID"/>
        <p:autoComplete id="SiteVisitReportSMEID" value="#{SiteVisitReportSMEUI.SiteVisitReportSME.municipality}" completeMethod="#{SiteVisitReportSMEUI.completeSiteVisitReportSME}"
                            var="rv" itemLabel="#{rv.SiteVisitReportSMEDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SiteVisitReportSMEConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SiteVisitReportSME" style="white-space: nowrap">#{rv.SiteVisitReportSMEDescription}</p:column>
       </p:autoComplete>         
       
*/

}
