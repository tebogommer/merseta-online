package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.HostingCompanyDepartments;
import haj.com.service.HostingCompanyDepartmentsService;

// TODO: Auto-generated Javadoc
/**
 * The Class HostingCompanyDepartmentsConvertor.
 */
@FacesConverter(value = "HostingCompanyDepartmentsConvertor")
public class HostingCompanyDepartmentsConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a HostingCompanyDepartments.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return HostingCompanyDepartments
	 * @see    HostingCompanyDepartments
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new HostingCompanyDepartmentsService()
						.findByKey(Long.valueOf(value));
			} catch (NumberFormatException e) {
				logger.fatal(e);
			} catch (Exception e) {
				logger.fatal(e);
			}

		}
	    return null;
	}


	/**
	 * Convert HostingCompanyDepartments key to String object.
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
		if (arg2!=null)
		    { 
			return ""+((HostingCompanyDepartments)arg2).getId(); 
			}
		else {
			return null;
		}
	}

/*
       <p:selectOneMenu id="HostingCompanyDepartmentsId" value="#{xxxUI.HostingCompanyDepartments.xxxType}" converter="HostingCompanyDepartmentsConvertor" style="width:95%">
         <f:selectItems value="#{HostingCompanyDepartmentsUI.HostingCompanyDepartmentsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="HostingCompanyDepartments" for="HostingCompanyDepartmentsID"/>
        <p:autoComplete id="HostingCompanyDepartmentsID" value="#{HostingCompanyDepartmentsUI.HostingCompanyDepartments.municipality}" completeMethod="#{HostingCompanyDepartmentsUI.completeHostingCompanyDepartments}"
                            var="rv" itemLabel="#{rv.HostingCompanyDepartmentsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="HostingCompanyDepartmentsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="HostingCompanyDepartments" style="white-space: nowrap">#{rv.HostingCompanyDepartmentsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
