package haj.com.convertors;

import haj.com.entity.WorkplaceMonitoringDgMonitoring;
import haj.com.service.WorkplaceMonitoringDgMonitoringService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "WorkplaceMonitoringDgMonitoringConvertor")
public class WorkplaceMonitoringDgMonitoringConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a WorkplaceMonitoringDgMonitoring
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringDgMonitoring
 	 * @return WorkplaceMonitoringDgMonitoring
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new WorkplaceMonitoringDgMonitoringService()
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
	 * Convert WorkplaceMonitoringDgMonitoring key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((WorkplaceMonitoringDgMonitoring)arg2).getId();
	}

/*
       <p:selectOneMenu id="WorkplaceMonitoringDgMonitoringId" value="#{xxxUI.WorkplaceMonitoringDgMonitoring.xxxType}" converter="WorkplaceMonitoringDgMonitoringConvertor" style="width:95%">
         <f:selectItems value="#{WorkplaceMonitoringDgMonitoringUI.WorkplaceMonitoringDgMonitoringList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="WorkplaceMonitoringDgMonitoring" for="WorkplaceMonitoringDgMonitoringID"/>
        <p:autoComplete id="WorkplaceMonitoringDgMonitoringID" value="#{WorkplaceMonitoringDgMonitoringUI.WorkplaceMonitoringDgMonitoring.municipality}" completeMethod="#{WorkplaceMonitoringDgMonitoringUI.completeWorkplaceMonitoringDgMonitoring}"
                            var="rv" itemLabel="#{rv.WorkplaceMonitoringDgMonitoringDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="WorkplaceMonitoringDgMonitoringConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="WorkplaceMonitoringDgMonitoring" style="white-space: nowrap">#{rv.WorkplaceMonitoringDgMonitoringDescription}</p:column>
       </p:autoComplete>         
       
*/

}
