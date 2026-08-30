package haj.com.convertors;

import haj.com.entity.WorkplaceMonitoringActionPlan;
import haj.com.service.WorkplaceMonitoringActionPlanService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "WorkplaceMonitoringActionPlanConvertor")
public class WorkplaceMonitoringActionPlanConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a WorkplaceMonitoringActionPlan
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringActionPlan
 	 * @return WorkplaceMonitoringActionPlan
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new WorkplaceMonitoringActionPlanService().findByKey(Integer.valueOf(value));
			} catch (NumberFormatException e) {
				logger.fatal(e);
			} catch (Exception e) {
				logger.fatal(e);
			}

		}
	    return null;
	}


	/**
	 * Convert WorkplaceMonitoringActionPlan key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((WorkplaceMonitoringActionPlan)arg2).getId();
	}

/*
       <p:selectOneMenu id="WorkplaceMonitoringActionPlanId" value="#{xxxUI.WorkplaceMonitoringActionPlan.xxxType}" converter="WorkplaceMonitoringActionPlanConvertor" style="width:95%">
         <f:selectItems value="#{WorkplaceMonitoringActionPlanUI.WorkplaceMonitoringActionPlanList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="WorkplaceMonitoringActionPlan" for="WorkplaceMonitoringActionPlanID"/>
        <p:autoComplete id="WorkplaceMonitoringActionPlanID" value="#{WorkplaceMonitoringActionPlanUI.WorkplaceMonitoringActionPlan.municipality}" completeMethod="#{WorkplaceMonitoringActionPlanUI.completeWorkplaceMonitoringActionPlan}"
                            var="rv" itemLabel="#{rv.WorkplaceMonitoringActionPlanDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="WorkplaceMonitoringActionPlanConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="WorkplaceMonitoringActionPlan" style="white-space: nowrap">#{rv.WorkplaceMonitoringActionPlanDescription}</p:column>
       </p:autoComplete>         
       
*/

}
