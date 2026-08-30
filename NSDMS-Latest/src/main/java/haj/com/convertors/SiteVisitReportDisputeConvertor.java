package haj.com.convertors;

import haj.com.entity.SiteVisitReportDispute;
import haj.com.service.SiteVisitReportDisputeService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "SiteVisitReportDisputeConvertor")
public class SiteVisitReportDisputeConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SiteVisitReportDispute
 	 * @author TechFinium 
 	 * @see    SiteVisitReportDispute
 	 * @return SiteVisitReportDispute
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SiteVisitReportDisputeService()
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
	 * Convert SiteVisitReportDispute key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((SiteVisitReportDispute)arg2).getId();
	}

/*
       <p:selectOneMenu id="SiteVisitReportDisputeId" value="#{xxxUI.SiteVisitReportDispute.xxxType}" converter="SiteVisitReportDisputeConvertor" style="width:95%">
         <f:selectItems value="#{SiteVisitReportDisputeUI.SiteVisitReportDisputeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SiteVisitReportDispute" for="SiteVisitReportDisputeID"/>
        <p:autoComplete id="SiteVisitReportDisputeID" value="#{SiteVisitReportDisputeUI.SiteVisitReportDispute.municipality}" completeMethod="#{SiteVisitReportDisputeUI.completeSiteVisitReportDispute}"
                            var="rv" itemLabel="#{rv.SiteVisitReportDisputeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SiteVisitReportDisputeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SiteVisitReportDispute" style="white-space: nowrap">#{rv.SiteVisitReportDisputeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
