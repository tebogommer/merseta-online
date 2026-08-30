package haj.com.convertors.lookup;

import haj.com.entity.lookup.MaritalStatus;
import haj.com.service.lookup.MaritalStatusService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "MaritalStatusConvertor")
public class MaritalStatusConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a MaritalStatus
 	 * @author TechFinium 
 	 * @see    MaritalStatus
 	 * @return MaritalStatus
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new MaritalStatusService()
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
	 * Convert MaritalStatus key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((MaritalStatus)arg2).getId();
	}

/*
       <p:selectOneMenu id="MaritalStatusId" value="#{xxxUI.MaritalStatus.xxxType}" converter="MaritalStatusConvertor" style="width:95%">
         <f:selectItems value="#{MaritalStatusUI.MaritalStatusList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="MaritalStatus" for="MaritalStatusID"/>
        <p:autoComplete id="MaritalStatusID" value="#{MaritalStatusUI.MaritalStatus.municipality}" completeMethod="#{MaritalStatusUI.completeMaritalStatus}"
                            var="rv" itemLabel="#{rv.MaritalStatusDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="MaritalStatusConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="MaritalStatus" style="white-space: nowrap">#{rv.MaritalStatusDescription}</p:column>
       </p:autoComplete>         
       
*/

}
