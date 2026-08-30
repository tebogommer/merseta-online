package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.MunicipalityType;
import haj.com.service.MunicipalityTypeService;

// TODO: Auto-generated Javadoc
/**
 * The Class MunicipalityTypeConvertor.
 */
@FacesConverter(value = "MunicipalityTypeConvertor")
public class MunicipalityTypeConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a MunicipalityType.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return MunicipalityType
	 * @see    MunicipalityType
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new MunicipalityTypeService().findByKey(Long.valueOf(value));
			} catch (NumberFormatException e) {
				logger.fatal(e);
			} catch (Exception e) {
				logger.fatal(e);
			}

		}
	    return null;
	}


	/**
	 * Convert MunicipalityType key to String object.
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
		return ""+((MunicipalityType)arg2).getId();
	}

/*
       <p:selectOneMenu id="MunicipalityTypeId" value="#{xxxUI.MunicipalityType.xxxType}" converter="MunicipalityTypeConvertor" style="width:95%">
         <f:selectItems value="#{MunicipalityTypeUI.MunicipalityTypeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="MunicipalityType" for="MunicipalityTypeID"/>
        <p:autoComplete id="MunicipalityTypeID" value="#{MunicipalityTypeUI.MunicipalityType.municipality}" completeMethod="#{MunicipalityTypeUI.completeMunicipalityType}"
                            var="rv" itemLabel="#{rv.MunicipalityTypeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="MunicipalityTypeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="MunicipalityType" style="white-space: nowrap">#{rv.MunicipalityTypeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
