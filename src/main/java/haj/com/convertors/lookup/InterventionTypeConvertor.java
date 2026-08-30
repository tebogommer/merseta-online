package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.InterventionType;
import haj.com.service.lookup.InterventionTypeService;

// TODO: Auto-generated Javadoc
/**
 * The Class InterventionTypeConvertor.
 */
@FacesConverter(value = "InterventionTypeConvertor")
public class InterventionTypeConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a InterventionType.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return InterventionType
	 * @see    InterventionType
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new InterventionTypeService()
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
	 * Convert InterventionType key to String object.
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
		if (arg2 instanceof String) return arg2.toString();
		return ""+((InterventionType)arg2).getId();
	}

/*
       <p:selectOneMenu id="InterventionTypeId" value="#{xxxUI.InterventionType.xxxType}" converter="InterventionTypeConvertor" style="width:95%">
         <f:selectItems value="#{InterventionTypeUI.InterventionTypeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="InterventionType" for="InterventionTypeID"/>
        <p:autoComplete id="InterventionTypeID" value="#{InterventionTypeUI.InterventionType.municipality}" completeMethod="#{InterventionTypeUI.completeInterventionType}"
                            var="rv" itemLabel="#{rv.InterventionTypeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="InterventionTypeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="InterventionType" style="white-space: nowrap">#{rv.InterventionTypeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
