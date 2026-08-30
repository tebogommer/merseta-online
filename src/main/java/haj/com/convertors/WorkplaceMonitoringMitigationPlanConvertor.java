package haj.com.convertors;

import haj.com.entity.WorkplaceMonitoringMitigationPlan;
import haj.com.service.WorkplaceMonitoringMitigationPlanService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "WorkplaceMonitoringMitigationPlanConvertor")
public class WorkplaceMonitoringMitigationPlanConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a WorkplaceMonitoringMitigationPlan
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringMitigationPlan
 	 * @return WorkplaceMonitoringMitigationPlan
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new WorkplaceMonitoringMitigationPlanService()
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
	 * Convert WorkplaceMonitoringMitigationPlan key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((WorkplaceMonitoringMitigationPlan)arg2).getId();
	}

/*
       <p:selectOneMenu id="WorkplaceMonitoringMitigationPlanId" value="#{xxxUI.WorkplaceMonitoringMitigationPlan.xxxType}" converter="WorkplaceMonitoringMitigationPlanConvertor" style="width:95%">
         <f:selectItems value="#{WorkplaceMonitoringMitigationPlanUI.WorkplaceMonitoringMitigationPlanList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="WorkplaceMonitoringMitigationPlan" for="WorkplaceMonitoringMitigationPlanID"/>
        <p:autoComplete id="WorkplaceMonitoringMitigationPlanID" value="#{WorkplaceMonitoringMitigationPlanUI.WorkplaceMonitoringMitigationPlan.municipality}" completeMethod="#{WorkplaceMonitoringMitigationPlanUI.completeWorkplaceMonitoringMitigationPlan}"
                            var="rv" itemLabel="#{rv.WorkplaceMonitoringMitigationPlanDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="WorkplaceMonitoringMitigationPlanConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="WorkplaceMonitoringMitigationPlan" style="white-space: nowrap">#{rv.WorkplaceMonitoringMitigationPlanDescription}</p:column>
       </p:autoComplete>         
       
*/

}
