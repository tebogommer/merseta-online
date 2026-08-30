package haj.com.convertors;

import haj.com.entity.CompanyCommunication;
import haj.com.service.CompanyCommunicationService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "CompanyCommunicationConvertor")
public class CompanyCommunicationConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a CompanyCommunication
 	 * @author TechFinium 
 	 * @see    CompanyCommunication
 	 * @return CompanyCommunication
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new CompanyCommunicationService()
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
	 * Convert CompanyCommunication key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((CompanyCommunication)arg2).getId();
	}

/*
       <p:selectOneMenu id="CompanyCommunicationId" value="#{xxxUI.CompanyCommunication.xxxType}" converter="CompanyCommunicationConvertor" style="width:95%">
         <f:selectItems value="#{CompanyCommunicationUI.CompanyCommunicationList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="CompanyCommunication" for="CompanyCommunicationID"/>
        <p:autoComplete id="CompanyCommunicationID" value="#{CompanyCommunicationUI.CompanyCommunication.municipality}" completeMethod="#{CompanyCommunicationUI.completeCompanyCommunication}"
                            var="rv" itemLabel="#{rv.CompanyCommunicationDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="CompanyCommunicationConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="CompanyCommunication" style="white-space: nowrap">#{rv.CompanyCommunicationDescription}</p:column>
       </p:autoComplete>         
       
*/

}
