package haj.com.convertors;

import haj.com.entity.WorkplaceMonitoringLearnerPayments;
import haj.com.service.WorkplaceMonitoringLearnerPaymentsService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "WorkplaceMonitoringLearnerPaymentsConvertor")
public class WorkplaceMonitoringLearnerPaymentsConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a WorkplaceMonitoringLearnerPayments
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringLearnerPayments
 	 * @return WorkplaceMonitoringLearnerPayments
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new WorkplaceMonitoringLearnerPaymentsService()
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
	 * Convert WorkplaceMonitoringLearnerPayments key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((WorkplaceMonitoringLearnerPayments)arg2).getId();
	}

/*
       <p:selectOneMenu id="WorkplaceMonitoringLearnerPaymentsId" value="#{xxxUI.WorkplaceMonitoringLearnerPayments.xxxType}" converter="WorkplaceMonitoringLearnerPaymentsConvertor" style="width:95%">
         <f:selectItems value="#{WorkplaceMonitoringLearnerPaymentsUI.WorkplaceMonitoringLearnerPaymentsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="WorkplaceMonitoringLearnerPayments" for="WorkplaceMonitoringLearnerPaymentsID"/>
        <p:autoComplete id="WorkplaceMonitoringLearnerPaymentsID" value="#{WorkplaceMonitoringLearnerPaymentsUI.WorkplaceMonitoringLearnerPayments.municipality}" completeMethod="#{WorkplaceMonitoringLearnerPaymentsUI.completeWorkplaceMonitoringLearnerPayments}"
                            var="rv" itemLabel="#{rv.WorkplaceMonitoringLearnerPaymentsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="WorkplaceMonitoringLearnerPaymentsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="WorkplaceMonitoringLearnerPayments" style="white-space: nowrap">#{rv.WorkplaceMonitoringLearnerPaymentsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
