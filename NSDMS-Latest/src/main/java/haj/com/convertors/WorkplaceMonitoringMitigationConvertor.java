package haj.com.convertors;

import haj.com.entity.WorkplaceMonitoringMitigation;
import haj.com.service.WorkplaceMonitoringMitigationService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "WorkplaceMonitoringMitigationConvertor")
public class WorkplaceMonitoringMitigationConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a WorkplaceMonitoringMitigation
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringMitigation
 	 * @return WorkplaceMonitoringMitigation
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new WorkplaceMonitoringMitigationService()
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
	 * Convert WorkplaceMonitoringMitigation key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((WorkplaceMonitoringMitigation)arg2).getId();
	}

/*
       <p:selectOneMenu id="WorkplaceMonitoringMitigationId" value="#{xxxUI.WorkplaceMonitoringMitigation.xxxType}" converter="WorkplaceMonitoringMitigationConvertor" style="width:95%">
         <f:selectItems value="#{WorkplaceMonitoringMitigationUI.WorkplaceMonitoringMitigationList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="WorkplaceMonitoringMitigation" for="WorkplaceMonitoringMitigationID"/>
        <p:autoComplete id="WorkplaceMonitoringMitigationID" value="#{WorkplaceMonitoringMitigationUI.WorkplaceMonitoringMitigation.municipality}" completeMethod="#{WorkplaceMonitoringMitigationUI.completeWorkplaceMonitoringMitigation}"
                            var="rv" itemLabel="#{rv.WorkplaceMonitoringMitigationDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="WorkplaceMonitoringMitigationConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="WorkplaceMonitoringMitigation" style="white-space: nowrap">#{rv.WorkplaceMonitoringMitigationDescription}</p:column>
       </p:autoComplete>         
       
*/

}
