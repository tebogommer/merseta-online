package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.SICCode;
import haj.com.service.lookup.SICCodeService;

// TODO: Auto-generated Javadoc
/**
 * The Class SICCodeConvertor.
 */
@FacesConverter(value = "SICCodeConvertor")
public class SICCodeConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SICCode.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return SICCode
	 * @see    SICCode
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SICCodeService()
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
	 * Convert SICCode key to String object.
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
		return ""+((SICCode)arg2).getId();
	}

/*
       <p:selectOneMenu id="SICCodeId" value="#{xxxUI.SICCode.xxxType}" converter="SICCodeConvertor" style="width:95%">
         <f:selectItems value="#{SICCodeUI.SICCodeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SICCode" for="SICCodeID"/>
        <p:autoComplete id="SICCodeID" value="#{SICCodeUI.SICCode.municipality}" completeMethod="#{SICCodeUI.completeSICCode}"
                            var="rv" itemLabel="#{rv.SICCodeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SICCodeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SICCode" style="white-space: nowrap">#{rv.SICCodeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
