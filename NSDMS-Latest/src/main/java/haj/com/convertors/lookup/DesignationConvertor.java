package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.Designation;
import haj.com.entity.lookup.Gender;
import haj.com.service.lookup.DesignationService;

// TODO: Auto-generated Javadoc
/**
 * The Class DesignationConvertor.
 */
@FacesConverter(value = "DesignationConvertor")
public class DesignationConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a Designation.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return Designation
	 * @see    Designation
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new DesignationService()
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
	 * Convert Designation key to String object.
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
		
		if (arg2 == null) {
			return "";
		}
		return "" + ((Designation) arg2).getId();
	}

/*
       <p:selectOneMenu id="DesignationId" value="#{xxxUI.Designation.xxxType}" converter="DesignationConvertor" style="width:95%">
         <f:selectItems value="#{DesignationUI.DesignationList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="Designation" for="DesignationID"/>
        <p:autoComplete id="DesignationID" value="#{DesignationUI.Designation.municipality}" completeMethod="#{DesignationUI.completeDesignation}"
                            var="rv" itemLabel="#{rv.DesignationDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="DesignationConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="Designation" style="white-space: nowrap">#{rv.DesignationDescription}</p:column>
       </p:autoComplete>         
       
*/

}
