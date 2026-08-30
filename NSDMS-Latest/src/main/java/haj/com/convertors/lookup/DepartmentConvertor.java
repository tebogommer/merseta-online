package haj.com.convertors.lookup;

import haj.com.entity.lookup.Department;
import haj.com.service.lookup.DepartmentService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "DepartmentConvertor")
public class DepartmentConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a Department
 	 * @author TechFinium 
 	 * @see    Department
 	 * @return Department
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new DepartmentService()
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
	 * Convert Department key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((Department)arg2).getId();
	}

/*
       <p:selectOneMenu id="DepartmentId" value="#{xxxUI.Department.xxxType}" converter="DepartmentConvertor" style="width:95%">
         <f:selectItems value="#{DepartmentUI.DepartmentList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="Department" for="DepartmentID"/>
        <p:autoComplete id="DepartmentID" value="#{DepartmentUI.Department.municipality}" completeMethod="#{DepartmentUI.completeDepartment}"
                            var="rv" itemLabel="#{rv.DepartmentDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="DepartmentConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="Department" style="white-space: nowrap">#{rv.DepartmentDescription}</p:column>
       </p:autoComplete>         
       
*/

}
