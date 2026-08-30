package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.HostingCompanyDepartmentsEmployees;
import haj.com.service.HostingCompanyDepartmentsEmployeesService;

// TODO: Auto-generated Javadoc
/**
 * The Class HostingCompanyDepartmentsEmployeesConvertor.
 */
@FacesConverter(value = "HostingCompanyDepartmentsEmployeesConvertor")
public class HostingCompanyDepartmentsEmployeesConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a HostingCompanyDepartmentsEmployees.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return HostingCompanyDepartmentsEmployees
	 * @see    HostingCompanyDepartmentsEmployees
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new HostingCompanyDepartmentsEmployeesService()
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
	 * Convert HostingCompanyDepartmentsEmployees key to String object.
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
		return ""+((HostingCompanyDepartmentsEmployees)arg2).getId();
	}

/*
       <p:selectOneMenu id="HostingCompanyDepartmentsEmployeesId" value="#{xxxUI.HostingCompanyDepartmentsEmployees.xxxType}" converter="HostingCompanyDepartmentsEmployeesConvertor" style="width:95%">
         <f:selectItems value="#{HostingCompanyDepartmentsEmployeesUI.HostingCompanyDepartmentsEmployeesList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="HostingCompanyDepartmentsEmployees" for="HostingCompanyDepartmentsEmployeesID"/>
        <p:autoComplete id="HostingCompanyDepartmentsEmployeesID" value="#{HostingCompanyDepartmentsEmployeesUI.HostingCompanyDepartmentsEmployees.municipality}" completeMethod="#{HostingCompanyDepartmentsEmployeesUI.completeHostingCompanyDepartmentsEmployees}"
                            var="rv" itemLabel="#{rv.HostingCompanyDepartmentsEmployeesDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="HostingCompanyDepartmentsEmployeesConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="HostingCompanyDepartmentsEmployees" style="white-space: nowrap">#{rv.HostingCompanyDepartmentsEmployeesDescription}</p:column>
       </p:autoComplete>         
       
*/

}
