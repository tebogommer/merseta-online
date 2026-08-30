package haj.com.convertors;

import haj.com.entity.AddressChange;
import haj.com.service.AddressChangeService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "AddressChangeConvertor")
public class AddressChangeConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a AddressChange
 	 * @author TechFinium 
 	 * @see    AddressChange
 	 * @return AddressChange
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new AddressChangeService()
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
	 * Convert AddressChange key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((AddressChange)arg2).getId();
	}

/*
       <p:selectOneMenu id="AddressChangeId" value="#{xxxUI.AddressChange.xxxType}" converter="AddressChangeConvertor" style="width:95%">
         <f:selectItems value="#{AddressChangeUI.AddressChangeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="AddressChange" for="AddressChangeID"/>
        <p:autoComplete id="AddressChangeID" value="#{AddressChangeUI.AddressChange.municipality}" completeMethod="#{AddressChangeUI.completeAddressChange}"
                            var="rv" itemLabel="#{rv.AddressChangeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="AddressChangeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="AddressChange" style="white-space: nowrap">#{rv.AddressChangeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
