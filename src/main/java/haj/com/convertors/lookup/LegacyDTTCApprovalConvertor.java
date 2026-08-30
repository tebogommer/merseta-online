package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyDTTCApproval;
import haj.com.service.lookup.LegacyDTTCApprovalService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyDTTCApprovalConvertor")
public class LegacyDTTCApprovalConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyDTTCApproval
 	 * @author TechFinium 
 	 * @see    LegacyDTTCApproval
 	 * @return LegacyDTTCApproval
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyDTTCApprovalService()
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
	 * Convert LegacyDTTCApproval key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyDTTCApproval)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyDTTCApprovalId" value="#{xxxUI.LegacyDTTCApproval.xxxType}" converter="LegacyDTTCApprovalConvertor" style="width:95%">
         <f:selectItems value="#{LegacyDTTCApprovalUI.LegacyDTTCApprovalList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyDTTCApproval" for="LegacyDTTCApprovalID"/>
        <p:autoComplete id="LegacyDTTCApprovalID" value="#{LegacyDTTCApprovalUI.LegacyDTTCApproval.municipality}" completeMethod="#{LegacyDTTCApprovalUI.completeLegacyDTTCApproval}"
                            var="rv" itemLabel="#{rv.LegacyDTTCApprovalDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyDTTCApprovalConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyDTTCApproval" style="white-space: nowrap">#{rv.LegacyDTTCApprovalDescription}</p:column>
       </p:autoComplete>         
       
*/

}
