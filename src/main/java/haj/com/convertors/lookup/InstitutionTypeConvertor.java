package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.InstitutionType;
import haj.com.service.lookup.InstitutionTypeService;

// TODO: Auto-generated Javadoc
/**
 * The Class InstitutionTypeConvertor.
 */
@FacesConverter(value = "InstitutionTypeConvertor")
public class InstitutionTypeConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a InstitutionType.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return InstitutionType
	 * @see    InstitutionType
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new InstitutionTypeService()
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
	 * Convert InstitutionType InstitutionTypeString object.
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
		return ""+((InstitutionType)arg2).getId();
	}

/*
       <p:selectOneMenu id="InstitutionTypeId" value="#{xxxUI.InstitutionType.xxxType}" converter="InstitutionTypeConvertor" style="width:95%">
         <f:selectItems value="#{InstitutionTypeUI.InstitutionTypeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="InstitutionType" for="InstitutionTypeID"/>
        <p:autoComplete id="InstitutionTypeID" value="#{InstitutionTypeUI.InstitutionType.municipality}" completeMethod="#{InstitutionTypeUI.completeInstitutionType}"
                            var="rv" itemLabel="#{rv.InstitutionTypeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="InstitutionTypeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="InstitutionType" style="white-space: nowrap">#{rv.InstitutionTypeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
