package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.AlternativeIdType;
import haj.com.service.lookup.AlternativeIdTypeService;

// TODO: Auto-generated Javadoc
/**
 * The Class AlternativeIdTypeConvertor.
 */
@FacesConverter(value = "AlternativeIdTypeConvertor")
public class AlternativeIdTypeConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a AlternativeIdType.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return AlternativeIdType
	 * @see    AlternativeIdType
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new AlternativeIdTypeService()
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
	 * Convert AlternativeIdType key to String object.
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
		return ""+((AlternativeIdType)arg2).getId();
	}

/*
       <p:selectOneMenu id="AlternativeIdTypeId" value="#{xxxUI.AlternativeIdType.xxxType}" converter="AlternativeIdTypeConvertor" style="width:95%">
         <f:selectItems value="#{AlternativeIdTypeUI.AlternativeIdTypeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="AlternativeIdType" for="AlternativeIdTypeID"/>
        <p:autoComplete id="AlternativeIdTypeID" value="#{AlternativeIdTypeUI.AlternativeIdType.municipality}" completeMethod="#{AlternativeIdTypeUI.completeAlternativeIdType}"
                            var="rv" itemLabel="#{rv.AlternativeIdTypeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="AlternativeIdTypeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="AlternativeIdType" style="white-space: nowrap">#{rv.AlternativeIdTypeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
