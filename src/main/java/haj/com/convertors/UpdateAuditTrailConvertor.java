package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.UpdateAuditTrail;
import haj.com.service.UpdateAuditTrailService;

@FacesConverter(value = "UpdateAuditTrailConvertor")
public class UpdateAuditTrailConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a UpdateAuditTrail
 	 * @author TechFinium 
 	 * @see    UpdateAuditTrail
 	 * @return UpdateAuditTrail
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new UpdateAuditTrailService()
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
	 * Convert UpdateAuditTrail key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((UpdateAuditTrail)arg2).getId();
	}

/*
       <p:selectOneMenu id="UpdateAuditTrailId" value="#{xxxUI.UpdateAuditTrail.xxxType}" converter="UpdateAuditTrailConvertor" style="width:95%">
         <f:selectItems value="#{UpdateAuditTrailUI.UpdateAuditTrailList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="UpdateAuditTrail" for="UpdateAuditTrailID"/>
        <p:autoComplete id="UpdateAuditTrailID" value="#{UpdateAuditTrailUI.UpdateAuditTrail.municipality}" completeMethod="#{UpdateAuditTrailUI.completeUpdateAuditTrail}"
                            var="rv" itemLabel="#{rv.UpdateAuditTrailDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="UpdateAuditTrailConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="UpdateAuditTrail" style="white-space: nowrap">#{rv.UpdateAuditTrailDescription}</p:column>
       </p:autoComplete>         
       
*/

}
