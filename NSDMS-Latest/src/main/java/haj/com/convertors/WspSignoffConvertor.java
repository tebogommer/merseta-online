package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.WspSignoff;
import haj.com.service.WspSignoffService;

// TODO: Auto-generated Javadoc
/**
 * The Class WspSignoffConvertor.
 */
@FacesConverter(value = "WspSignoffConvertor")
public class WspSignoffConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a WspSignoff.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return WspSignoff
	 * @see    WspSignoff
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new WspSignoffService()
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
	 * Convert WspSignoff key to String object.
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
		return ""+((WspSignoff)arg2).getId();
	}

/*
       <p:selectOneMenu id="WspSignoffId" value="#{xxxUI.WspSignoff.xxxType}" converter="WspSignoffConvertor" style="width:95%">
         <f:selectItems value="#{WspSignoffUI.WspSignoffList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="WspSignoff" for="WspSignoffID"/>
        <p:autoComplete id="WspSignoffID" value="#{WspSignoffUI.WspSignoff.municipality}" completeMethod="#{WspSignoffUI.completeWspSignoff}"
                            var="rv" itemLabel="#{rv.WspSignoffDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="WspSignoffConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="WspSignoff" style="white-space: nowrap">#{rv.WspSignoffDescription}</p:column>
       </p:autoComplete>         
       
*/

}
