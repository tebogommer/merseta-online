package haj.com.convertors;

import haj.com.entity.WorkplaceMonitoringSiteVisit;
import haj.com.service.WorkplaceMonitoringSiteVisitService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "WorkplaceMonitoringSiteVisitConvertor")
public class WorkplaceMonitoringSiteVisitConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a WorkplaceMonitoringSiteVisit
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringSiteVisit
 	 * @return WorkplaceMonitoringSiteVisit
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new WorkplaceMonitoringSiteVisitService()
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
	 * Convert WorkplaceMonitoringSiteVisit key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((WorkplaceMonitoringSiteVisit)arg2).getId();
	}

/*
       <p:selectOneMenu id="WorkplaceMonitoringSiteVisitId" value="#{xxxUI.WorkplaceMonitoringSiteVisit.xxxType}" converter="WorkplaceMonitoringSiteVisitConvertor" style="width:95%">
         <f:selectItems value="#{WorkplaceMonitoringSiteVisitUI.WorkplaceMonitoringSiteVisitList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="WorkplaceMonitoringSiteVisit" for="WorkplaceMonitoringSiteVisitID"/>
        <p:autoComplete id="WorkplaceMonitoringSiteVisitID" value="#{WorkplaceMonitoringSiteVisitUI.WorkplaceMonitoringSiteVisit.municipality}" completeMethod="#{WorkplaceMonitoringSiteVisitUI.completeWorkplaceMonitoringSiteVisit}"
                            var="rv" itemLabel="#{rv.WorkplaceMonitoringSiteVisitDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="WorkplaceMonitoringSiteVisitConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="WorkplaceMonitoringSiteVisit" style="white-space: nowrap">#{rv.WorkplaceMonitoringSiteVisitDescription}</p:column>
       </p:autoComplete>         
       
*/

}
