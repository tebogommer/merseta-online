package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.EmployeesImport;
import haj.com.service.EmployeesImportService;

// TODO: Auto-generated Javadoc
/**
 * The Class EmployeesImportConvertor.
 */
@FacesConverter(value = "EmployeesImportConvertor")
public class EmployeesImportConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a EmployeesImport.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return EmployeesImport
	 * @see    EmployeesImport
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new EmployeesImportService()
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
	 * Convert EmployeesImport key to String object.
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
		return ""+((EmployeesImport)arg2).getId();
	}

/*
       <p:selectOneMenu id="EmployeesImportId" value="#{xxxUI.EmployeesImport.xxxType}" converter="EmployeesImportConvertor" style="width:95%">
         <f:selectItems value="#{EmployeesImportUI.EmployeesImportList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="EmployeesImport" for="EmployeesImportID"/>
        <p:autoComplete id="EmployeesImportID" value="#{EmployeesImportUI.EmployeesImport.municipality}" completeMethod="#{EmployeesImportUI.completeEmployeesImport}"
                            var="rv" itemLabel="#{rv.EmployeesImportDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="EmployeesImportConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="EmployeesImport" style="white-space: nowrap">#{rv.EmployeesImportDescription}</p:column>
       </p:autoComplete>         
       
*/

}
