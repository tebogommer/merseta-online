package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.FLC;
import haj.com.service.lookup.FLCService;

// TODO: Auto-generated Javadoc
/**
 * The Class FLCConvertor.
 */
@FacesConverter(value = "FLCConvertor")
public class FLCConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a FLC.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return FLC
	 * @see    FLC
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new FLCService()
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
	 * Convert FLC key to String object.
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
		return ""+((FLC)arg2).getId();
	}

/*
       <p:selectOneMenu id="FLCId" value="#{xxxUI.FLC.xxxType}" converter="FLCConvertor" style="width:95%">
         <f:selectItems value="#{FLCUI.FLCList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="FLC" for="FLCID"/>
        <p:autoComplete id="FLCID" value="#{FLCUI.FLC.municipality}" completeMethod="#{FLCUI.completeFLC}"
                            var="rv" itemLabel="#{rv.FLCDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="FLCConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="FLC" style="white-space: nowrap">#{rv.FLCDescription}</p:column>
       </p:autoComplete>         
       
*/

}
