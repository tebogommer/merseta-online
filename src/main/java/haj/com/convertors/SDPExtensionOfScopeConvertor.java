package haj.com.convertors;

import haj.com.entity.SDPExtensionOfScope;
import haj.com.service.SDPExtensionOfScopeService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "SDPExtensionOfScopeConvertor")
public class SDPExtensionOfScopeConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SDPExtensionOfScope
 	 * @author TechFinium 
 	 * @see    SDPExtensionOfScope
 	 * @return SDPExtensionOfScope
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SDPExtensionOfScopeService()
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
	 * Convert SDPExtensionOfScope key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((SDPExtensionOfScope)arg2).getId();
	}

/*
       <p:selectOneMenu id="SDPExtensionOfScopeId" value="#{xxxUI.SDPExtensionOfScope.xxxType}" converter="SDPExtensionOfScopeConvertor" style="width:95%">
         <f:selectItems value="#{SDPExtensionOfScopeUI.SDPExtensionOfScopeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SDPExtensionOfScope" for="SDPExtensionOfScopeID"/>
        <p:autoComplete id="SDPExtensionOfScopeID" value="#{SDPExtensionOfScopeUI.SDPExtensionOfScope.municipality}" completeMethod="#{SDPExtensionOfScopeUI.completeSDPExtensionOfScope}"
                            var="rv" itemLabel="#{rv.SDPExtensionOfScopeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SDPExtensionOfScopeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SDPExtensionOfScope" style="white-space: nowrap">#{rv.SDPExtensionOfScopeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
