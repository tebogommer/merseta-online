package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.OfoCodes;
import haj.com.service.OfoCodesService;

// TODO: Auto-generated Javadoc
/**
 * The Class OfoCodesConvertor.
 */
@FacesConverter(value = "OfoCodesConvertor")
public class OfoCodesConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a OfoCodes.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return OfoCodes
	 * @see    OfoCodes
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new OfoCodesService()
						.findByKey(Long.valueOf(value));
			} catch (NumberFormatException e) {
				logger.fatal(e);
			} catch (Exception e) {
				logger.fatal(e);
			}

		}
	    return null;
	}


	/**
	 * Convert OfoCodes key to String object.
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
			arg2.toString();
		return ""+((OfoCodes)arg2).getId();
	}

/*
       <p:selectOneMenu id="OfoCodesId" value="#{xxxUI.OfoCodes.xxxType}" converter="OfoCodesConvertor" style="width:95%">
         <f:selectItems value="#{OfoCodesUI.OfoCodesList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="OfoCodes" for="OfoCodesID"/>
        <p:autoComplete id="OfoCodesID" value="#{OfoCodesUI.OfoCodes.municipality}" completeMethod="#{OfoCodesUI.completeOfoCodes}"
                            var="rv" itemLabel="#{rv.OfoCodesDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="OfoCodesConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="OfoCodes" style="white-space: nowrap">#{rv.OfoCodesDescription}</p:column>
       </p:autoComplete>         
       
*/

}
