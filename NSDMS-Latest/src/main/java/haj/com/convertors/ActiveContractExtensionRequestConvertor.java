package haj.com.convertors;

import haj.com.entity.ActiveContractExtensionRequest;
import haj.com.service.ActiveContractExtensionRequestService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "ActiveContractExtensionRequestConvertor")
public class ActiveContractExtensionRequestConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a ActiveContractExtensionRequest
 	 * @author TechFinium 
 	 * @see    ActiveContractExtensionRequest
 	 * @return ActiveContractExtensionRequest
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new ActiveContractExtensionRequestService()
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
	 * Convert ActiveContractExtensionRequest key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((ActiveContractExtensionRequest)arg2).getId();
	}

/*
       <p:selectOneMenu id="ActiveContractExtensionRequestId" value="#{xxxUI.ActiveContractExtensionRequest.xxxType}" converter="ActiveContractExtensionRequestConvertor" style="width:95%">
         <f:selectItems value="#{ActiveContractExtensionRequestUI.ActiveContractExtensionRequestList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="ActiveContractExtensionRequest" for="ActiveContractExtensionRequestID"/>
        <p:autoComplete id="ActiveContractExtensionRequestID" value="#{ActiveContractExtensionRequestUI.ActiveContractExtensionRequest.municipality}" completeMethod="#{ActiveContractExtensionRequestUI.completeActiveContractExtensionRequest}"
                            var="rv" itemLabel="#{rv.ActiveContractExtensionRequestDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="ActiveContractExtensionRequestConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="ActiveContractExtensionRequest" style="white-space: nowrap">#{rv.ActiveContractExtensionRequestDescription}</p:column>
       </p:autoComplete>         
       
*/

}
