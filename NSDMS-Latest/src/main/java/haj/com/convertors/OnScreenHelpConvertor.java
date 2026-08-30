package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.OnScreenHelp;
import haj.com.service.OnScreenHelpService;

@FacesConverter(value = "OnScreenHelpConvertor")
public class OnScreenHelpConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a OnScreenHelp
 	 * @author TechFinium 
 	 * @see    OnScreenHelp
 	 * @return OnScreenHelp
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new OnScreenHelpService()
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
	 * Convert OnScreenHelp key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((OnScreenHelp)arg2).getId();
	}

/*
       <p:selectOneMenu id="OnScreenHelpId" value="#{xxxUI.OnScreenHelp.xxxType}" converter="OnScreenHelpConvertor" style="width:95%">
         <f:selectItems value="#{OnScreenHelpUI.OnScreenHelpList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="OnScreenHelp" for="OnScreenHelpID"/>
        <p:autoComplete id="OnScreenHelpID" value="#{OnScreenHelpUI.OnScreenHelp.municipality}" completeMethod="#{OnScreenHelpUI.completeOnScreenHelp}"
                            var="rv" itemLabel="#{rv.OnScreenHelpDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="OnScreenHelpConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="OnScreenHelp" style="white-space: nowrap">#{rv.OnScreenHelpDescription}</p:column>
       </p:autoComplete>         
       
*/

}
