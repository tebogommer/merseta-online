package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.Employees;
import haj.com.service.EmployeesService;

// TODO: Auto-generated Javadoc
/**
 * The Class EmployeesConvertor.
 */
@FacesConverter(value = "EmployeesConvertor")
public class EmployeesConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a Employees.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return Employees
	 * @see    Employees
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new EmployeesService()
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
	 * Convert Employees key to String object.
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
		return ""+((Employees)arg2).getId();
	}

/*
       <p:selectOneMenu id="EmployeesId" value="#{xxxUI.Employees.xxxType}" converter="EmployeesConvertor" style="width:95%">
         <f:selectItems value="#{EmployeesUI.EmployeesList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="Employees" for="EmployeesID"/>
        <p:autoComplete id="EmployeesID" value="#{EmployeesUI.Employees.municipality}" completeMethod="#{EmployeesUI.completeEmployees}"
                            var="rv" itemLabel="#{rv.EmployeesDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="EmployeesConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="Employees" style="white-space: nowrap">#{rv.EmployeesDescription}</p:column>
       </p:autoComplete>         
       
*/

}
