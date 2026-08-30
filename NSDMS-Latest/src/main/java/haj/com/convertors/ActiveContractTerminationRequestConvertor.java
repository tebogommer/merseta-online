package haj.com.convertors;

import haj.com.entity.ActiveContractTerminationRequest;
import haj.com.service.ActiveContractTerminationRequestService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "ActiveContractTerminationRequestConvertor")
public class ActiveContractTerminationRequestConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a ActiveContractTerminationRequest
 	 * @author TechFinium 
 	 * @see    ActiveContractTerminationRequest
 	 * @return ActiveContractTerminationRequest
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new ActiveContractTerminationRequestService()
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
	 * Convert ActiveContractTerminationRequest key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((ActiveContractTerminationRequest)arg2).getId();
	}

/*
       <p:selectOneMenu id="ActiveContractTerminationRequestId" value="#{xxxUI.ActiveContractTerminationRequest.xxxType}" converter="ActiveContractTerminationRequestConvertor" style="width:95%">
         <f:selectItems value="#{ActiveContractTerminationRequestUI.ActiveContractTerminationRequestList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="ActiveContractTerminationRequest" for="ActiveContractTerminationRequestID"/>
        <p:autoComplete id="ActiveContractTerminationRequestID" value="#{ActiveContractTerminationRequestUI.ActiveContractTerminationRequest.municipality}" completeMethod="#{ActiveContractTerminationRequestUI.completeActiveContractTerminationRequest}"
                            var="rv" itemLabel="#{rv.ActiveContractTerminationRequestDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="ActiveContractTerminationRequestConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="ActiveContractTerminationRequest" style="white-space: nowrap">#{rv.ActiveContractTerminationRequestDescription}</p:column>
       </p:autoComplete>         
       
*/

}
