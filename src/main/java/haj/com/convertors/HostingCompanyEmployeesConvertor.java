package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.HostingCompanyEmployees;
import haj.com.service.HostingCompanyEmployeesService;

// TODO: Auto-generated Javadoc
/**
 * The Class HostingCompanyEmployeesConvertor.
 */
@FacesConverter(value = "HostingCompanyEmployeesConvertor")
public class HostingCompanyEmployeesConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a HostingCompanyEmployees.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return HostingCompanyEmployees
	 * @see    HostingCompanyEmployees
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new HostingCompanyEmployeesService()
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
	 * Convert HostingCompanyEmployees key to String object.
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
		if (arg2 == null)
			return "";
		if (arg2 instanceof String)
			return arg2.toString();
		return ""+((HostingCompanyEmployees)arg2).getId();
	}

/*
       <p:selectOneMenu id="HostingCompanyEmployeesId" value="#{xxxUI.HostingCompanyEmployees.xxxType}" converter="HostingCompanyEmployeesConvertor" style="width:95%">
         <f:selectItems value="#{HostingCompanyEmployeesUI.HostingCompanyEmployeesList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="HostingCompanyEmployees" for="HostingCompanyEmployeesID"/>
        <p:autoComplete id="HostingCompanyEmployeesID" value="#{HostingCompanyEmployeesUI.HostingCompanyEmployees.municipality}" completeMethod="#{HostingCompanyEmployeesUI.completeHostingCompanyEmployees}"
                            var="rv" itemLabel="#{rv.HostingCompanyEmployeesDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="HostingCompanyEmployeesConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="HostingCompanyEmployees" style="white-space: nowrap">#{rv.HostingCompanyEmployeesDescription}</p:column>
       </p:autoComplete>         
       
*/

}
