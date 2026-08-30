package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.ProviderType;
import haj.com.service.lookup.ProviderTypeService;

// TODO: Auto-generated Javadoc
/**
 * The Class ProviderTypeConvertor.
 */
@FacesConverter(value = "ProviderTypeConvertor")
public class ProviderTypeConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a ProviderType.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return ProviderType
	 * @see    ProviderType
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new ProviderTypeService()
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
	 * Convert ProviderType key to String object.
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
		return ""+((ProviderType)arg2).getId();
	}

/*
       <p:selectOneMenu id="ProviderTypeId" value="#{xxxUI.ProviderType.xxxType}" converter="ProviderTypeConvertor" style="width:95%">
         <f:selectItems value="#{ProviderTypeUI.ProviderTypeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="ProviderType" for="ProviderTypeID"/>
        <p:autoComplete id="ProviderTypeID" value="#{ProviderTypeUI.ProviderType.municipality}" completeMethod="#{ProviderTypeUI.completeProviderType}"
                            var="rv" itemLabel="#{rv.ProviderTypeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="ProviderTypeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="ProviderType" style="white-space: nowrap">#{rv.ProviderTypeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
