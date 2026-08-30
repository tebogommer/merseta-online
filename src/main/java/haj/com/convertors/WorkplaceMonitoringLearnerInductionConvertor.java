package haj.com.convertors;

import haj.com.entity.WorkplaceMonitoringLearnerInduction;
import haj.com.service.WorkplaceMonitoringLearnerInductionService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "WorkplaceMonitoringLearnerInductionConvertor")
public class WorkplaceMonitoringLearnerInductionConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a WorkplaceMonitoringLearnerInduction
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringLearnerInduction
 	 * @return WorkplaceMonitoringLearnerInduction
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new WorkplaceMonitoringLearnerInductionService()
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
	 * Convert WorkplaceMonitoringLearnerInduction key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((WorkplaceMonitoringLearnerInduction)arg2).getId();
	}

/*
       <p:selectOneMenu id="WorkplaceMonitoringLearnerInductionId" value="#{xxxUI.WorkplaceMonitoringLearnerInduction.xxxType}" converter="WorkplaceMonitoringLearnerInductionConvertor" style="width:95%">
         <f:selectItems value="#{WorkplaceMonitoringLearnerInductionUI.WorkplaceMonitoringLearnerInductionList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="WorkplaceMonitoringLearnerInduction" for="WorkplaceMonitoringLearnerInductionID"/>
        <p:autoComplete id="WorkplaceMonitoringLearnerInductionID" value="#{WorkplaceMonitoringLearnerInductionUI.WorkplaceMonitoringLearnerInduction.municipality}" completeMethod="#{WorkplaceMonitoringLearnerInductionUI.completeWorkplaceMonitoringLearnerInduction}"
                            var="rv" itemLabel="#{rv.WorkplaceMonitoringLearnerInductionDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="WorkplaceMonitoringLearnerInductionConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="WorkplaceMonitoringLearnerInduction" style="white-space: nowrap">#{rv.WorkplaceMonitoringLearnerInductionDescription}</p:column>
       </p:autoComplete>         
       
*/

}
