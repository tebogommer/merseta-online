package haj.com.convertors;

import haj.com.entity.SiteVisitReport;
import haj.com.service.SiteVisitReportService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "SiteVisitReportConvertor")
public class SiteVisitReportConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SiteVisitReport
 	 * @author TechFinium 
 	 * @see    SiteVisitReport
 	 * @return SiteVisitReport
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SiteVisitReportService()
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
	 * Convert SiteVisitReport key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((SiteVisitReport)arg2).getId();
	}

/*
       <p:selectOneMenu id="SiteVisitReportId" value="#{xxxUI.SiteVisitReport.xxxType}" converter="SiteVisitReportConvertor" style="width:95%">
         <f:selectItems value="#{SiteVisitReportUI.SiteVisitReportList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SiteVisitReport" for="SiteVisitReportID"/>
        <p:autoComplete id="SiteVisitReportID" value="#{SiteVisitReportUI.SiteVisitReport.municipality}" completeMethod="#{SiteVisitReportUI.completeSiteVisitReport}"
                            var="rv" itemLabel="#{rv.SiteVisitReportDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SiteVisitReportConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SiteVisitReport" style="white-space: nowrap">#{rv.SiteVisitReportDescription}</p:column>
       </p:autoComplete>         
       
*/

}
