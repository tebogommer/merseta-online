package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.ExtensionRequest;
import haj.com.service.ExtensionRequestService;

@FacesConverter(value = "ExtensionRequestConvertor")
public class ExtensionRequestConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a ExtensionRequest
 	 * @author TechFinium 
 	 * @see    ExtensionRequest
 	 * @return ExtensionRequest
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new ExtensionRequestService()
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
	 * Convert ExtensionRequest key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((ExtensionRequest)arg2).getId();
	}

/*
       <p:selectOneMenu id="ExtensionRequestId" value="#{xxxUI.ExtensionRequest.xxxType}" converter="ExtensionRequestConvertor" style="width:95%">
         <f:selectItems value="#{ExtensionRequestUI.ExtensionRequestList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="ExtensionRequest" for="ExtensionRequestID"/>
        <p:autoComplete id="ExtensionRequestID" value="#{ExtensionRequestUI.ExtensionRequest.municipality}" completeMethod="#{ExtensionRequestUI.completeExtensionRequest}"
                            var="rv" itemLabel="#{rv.ExtensionRequestDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="ExtensionRequestConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="ExtensionRequest" style="white-space: nowrap">#{rv.ExtensionRequestDescription}</p:column>
       </p:autoComplete>         
       
*/

}
