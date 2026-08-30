package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.WorkplaceMonitoring;
import haj.com.service.WorkplaceMonitoringService;

@FacesConverter(value = "WorkplaceMonitoringConvertor")
public class WorkplaceMonitoringConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a WorkplaceMonitoring
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoring
 	 * @return WorkplaceMonitoring
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new WorkplaceMonitoringService()
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
	 * Convert WorkplaceMonitoring key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((WorkplaceMonitoring)arg2).getId();
	}

/*
       <p:selectOneMenu id="WorkplaceMonitoringId" value="#{xxxUI.WorkplaceMonitoring.xxxType}" converter="WorkplaceMonitoringConvertor" style="width:95%">
         <f:selectItems value="#{WorkplaceMonitoringUI.WorkplaceMonitoringList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="WorkplaceMonitoring" for="WorkplaceMonitoringID"/>
        <p:autoComplete id="WorkplaceMonitoringID" value="#{WorkplaceMonitoringUI.WorkplaceMonitoring.municipality}" completeMethod="#{WorkplaceMonitoringUI.completeWorkplaceMonitoring}"
                            var="rv" itemLabel="#{rv.WorkplaceMonitoringDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="WorkplaceMonitoringConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="WorkplaceMonitoring" style="white-space: nowrap">#{rv.WorkplaceMonitoringDescription}</p:column>
       </p:autoComplete>         
       
*/

}
