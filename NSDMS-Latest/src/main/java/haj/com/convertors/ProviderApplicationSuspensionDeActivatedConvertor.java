package haj.com.convertors;

import haj.com.entity.ProviderApplicationSuspensionDeActivated;
import haj.com.service.ProviderApplicationSuspensionDeActivatedService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "ProviderApplicationSuspensionDeActivatedConvertor")
public class ProviderApplicationSuspensionDeActivatedConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a ProviderApplicationSuspensionDeActivated
 	 * @author TechFinium 
 	 * @see    ProviderApplicationSuspensionDeActivated
 	 * @return ProviderApplicationSuspensionDeActivated
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new ProviderApplicationSuspensionDeActivatedService()
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
	 * Convert ProviderApplicationSuspensionDeActivated key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((ProviderApplicationSuspensionDeActivated)arg2).getId();
	}

/*
       <p:selectOneMenu id="ProviderApplicationSuspensionDeActivatedId" value="#{xxxUI.ProviderApplicationSuspensionDeActivated.xxxType}" converter="ProviderApplicationSuspensionDeActivatedConvertor" style="width:95%">
         <f:selectItems value="#{ProviderApplicationSuspensionDeActivatedUI.ProviderApplicationSuspensionDeActivatedList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="ProviderApplicationSuspensionDeActivated" for="ProviderApplicationSuspensionDeActivatedID"/>
        <p:autoComplete id="ProviderApplicationSuspensionDeActivatedID" value="#{ProviderApplicationSuspensionDeActivatedUI.ProviderApplicationSuspensionDeActivated.municipality}" completeMethod="#{ProviderApplicationSuspensionDeActivatedUI.completeProviderApplicationSuspensionDeActivated}"
                            var="rv" itemLabel="#{rv.ProviderApplicationSuspensionDeActivatedDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="ProviderApplicationSuspensionDeActivatedConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="ProviderApplicationSuspensionDeActivated" style="white-space: nowrap">#{rv.ProviderApplicationSuspensionDeActivatedDescription}</p:column>
       </p:autoComplete>         
       
*/

}
