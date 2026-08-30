package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.EmploymentType;
import haj.com.service.lookup.EmploymentTypeService;

// TODO: Auto-generated Javadoc
/**
 * The Class EmploymentTypeConvertor.
 */
@FacesConverter(value = "EmploymentTypeConvertor")
public class EmploymentTypeConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a EmploymentType.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return EmploymentType
	 * @see    EmploymentType
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new EmploymentTypeService()
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
	 * Convert EmploymentType key to String object.
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
		return ""+((EmploymentType)arg2).getId();
	}

/*
       <p:selectOneMenu id="EmploymentTypeId" value="#{xxxUI.EmploymentType.xxxType}" converter="EmploymentTypeConvertor" style="width:95%">
         <f:selectItems value="#{EmploymentTypeUI.EmploymentTypeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="EmploymentType" for="EmploymentTypeID"/>
        <p:autoComplete id="EmploymentTypeID" value="#{EmploymentTypeUI.EmploymentType.municipality}" completeMethod="#{EmploymentTypeUI.completeEmploymentType}"
                            var="rv" itemLabel="#{rv.EmploymentTypeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="EmploymentTypeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="EmploymentType" style="white-space: nowrap">#{rv.EmploymentTypeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
