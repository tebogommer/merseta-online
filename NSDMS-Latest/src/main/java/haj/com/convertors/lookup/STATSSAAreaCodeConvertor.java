package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.STATSSAAreaCode;
import haj.com.service.lookup.STATSSAAreaCodeService;

// TODO: Auto-generated Javadoc
/**
 * The Class STATSSAAreaCodeConvertor.
 */
@FacesConverter(value = "STATSSAAreaCodeConvertor")
public class STATSSAAreaCodeConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a STATSSAAreaCode.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return STATSSAAreaCode
	 * @see    STATSSAAreaCode
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new STATSSAAreaCodeService()
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
	 * Convert STATSSAAreaCode key to String object.
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
		return ""+((STATSSAAreaCode)arg2).getId();
	}

/*
       <p:selectOneMenu id="STATSSAAreaCodeId" value="#{xxxUI.STATSSAAreaCode.xxxType}" converter="STATSSAAreaCodeConvertor" style="width:95%">
         <f:selectItems value="#{STATSSAAreaCodeUI.STATSSAAreaCodeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="STATSSAAreaCode" for="STATSSAAreaCodeID"/>
        <p:autoComplete id="STATSSAAreaCodeID" value="#{STATSSAAreaCodeUI.STATSSAAreaCode.municipality}" completeMethod="#{STATSSAAreaCodeUI.completeSTATSSAAreaCode}"
                            var="rv" itemLabel="#{rv.STATSSAAreaCodeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="STATSSAAreaCodeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="STATSSAAreaCode" style="white-space: nowrap">#{rv.STATSSAAreaCodeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
