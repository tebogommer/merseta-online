package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.EmployerApprovalStatus;
import haj.com.service.lookup.EmployerApprovalStatusService;

// TODO: Auto-generated Javadoc
/**
 * The Class EmployerApprovalStatusConvertor.
 */
@FacesConverter(value = "EmployerApprovalStatusConvertor")
public class EmployerApprovalStatusConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a EmployerApprovalStatus.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return EmployerApprovalStatus
	 * @see    EmployerApprovalStatus
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new EmployerApprovalStatusService()
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
	 * Convert EmployerApprovalStatus key to String object.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param arg2 the arg 2
	 * @return String
	 * @see    String
	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return ""+((EmployerApprovalStatus)arg2).getId();
	}

/*
       <p:selectOneMenu id="EmployerApprovalStatusId" value="#{xxxUI.EmployerApprovalStatus.xxxType}" converter="EmployerApprovalStatusConvertor" style="width:95%">
         <f:selectItems value="#{EmployerApprovalStatusUI.EmployerApprovalStatusList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="EmployerApprovalStatus" for="EmployerApprovalStatusID"/>
        <p:autoComplete id="EmployerApprovalStatusID" value="#{EmployerApprovalStatusUI.EmployerApprovalStatus.municipality}" completeMethod="#{EmployerApprovalStatusUI.completeEmployerApprovalStatus}"
                            var="rv" itemLabel="#{rv.EmployerApprovalStatusDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="EmployerApprovalStatusConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="EmployerApprovalStatus" style="white-space: nowrap">#{rv.EmployerApprovalStatusDescription}</p:column>
       </p:autoComplete>         
       
*/

}
