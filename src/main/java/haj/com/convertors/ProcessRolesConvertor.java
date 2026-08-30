package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.ProcessRoles;
import haj.com.service.ProcessRolesService;

// TODO: Auto-generated Javadoc
/**
 * The Class ProcessRolesConvertor.
 */
@FacesConverter(value = "ProcessRolesConvertor")
public class ProcessRolesConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a ProcessRoles.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return ProcessRoles
	 * @see    ProcessRoles
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new ProcessRolesService()
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
	 * Convert ProcessRoles key to String object.
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
		if (arg2 == null) return "";
		if (arg2 instanceof String) arg2.toString();
		return ""+((ProcessRoles)arg2).getId();
	}

/*
       <p:selectOneMenu id="ProcessRolesId" value="#{xxxUI.ProcessRoles.xxxType}" converter="ProcessRolesConvertor" style="width:95%">
         <f:selectItems value="#{ProcessRolesUI.ProcessRolesList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="ProcessRoles" for="ProcessRolesID"/>
        <p:autoComplete id="ProcessRolesID" value="#{ProcessRolesUI.ProcessRoles.municipality}" completeMethod="#{ProcessRolesUI.completeProcessRoles}"
                            var="rv" itemLabel="#{rv.ProcessRolesDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="ProcessRolesConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="ProcessRoles" style="white-space: nowrap">#{rv.ProcessRolesDescription}</p:column>
       </p:autoComplete>         
       
*/

}
