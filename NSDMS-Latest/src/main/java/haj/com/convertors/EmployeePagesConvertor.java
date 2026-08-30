package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.EmployeePages;
import haj.com.service.EmployeePagesService;

@FacesConverter(value = "EmployeePagesConvertor")
public class EmployeePagesConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a EmployeePages
 	 * @author TechFinium 
 	 * @see    EmployeePages
 	 * @return EmployeePages
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new EmployeePagesService()
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
	 * Convert EmployeePages key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((EmployeePages)arg2).getId();
	}

/*
       <p:selectOneMenu id="EmployeePagesId" value="#{xxxUI.EmployeePages.xxxType}" converter="EmployeePagesConvertor" style="width:95%">
         <f:selectItems value="#{EmployeePagesUI.EmployeePagesList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="EmployeePages" for="EmployeePagesID"/>
        <p:autoComplete id="EmployeePagesID" value="#{EmployeePagesUI.EmployeePages.municipality}" completeMethod="#{EmployeePagesUI.completeEmployeePages}"
                            var="rv" itemLabel="#{rv.EmployeePagesDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="EmployeePagesConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="EmployeePages" style="white-space: nowrap">#{rv.EmployeePagesDescription}</p:column>
       </p:autoComplete>         
       
*/

}
