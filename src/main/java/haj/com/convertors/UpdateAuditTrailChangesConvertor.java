package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.UpdateAuditTrailChanges;
import haj.com.service.UpdateAuditTrailChangesService;

@FacesConverter(value = "UpdateAuditTrailChangesConvertor")
public class UpdateAuditTrailChangesConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a UpdateAuditTrailChanges
 	 * @author TechFinium 
 	 * @see    UpdateAuditTrailChanges
 	 * @return UpdateAuditTrailChanges
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new UpdateAuditTrailChangesService()
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
	 * Convert UpdateAuditTrailChanges key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((UpdateAuditTrailChanges)arg2).getId();
	}

/*
       <p:selectOneMenu id="UpdateAuditTrailChangesId" value="#{xxxUI.UpdateAuditTrailChanges.xxxType}" converter="UpdateAuditTrailChangesConvertor" style="width:95%">
         <f:selectItems value="#{UpdateAuditTrailChangesUI.UpdateAuditTrailChangesList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="UpdateAuditTrailChanges" for="UpdateAuditTrailChangesID"/>
        <p:autoComplete id="UpdateAuditTrailChangesID" value="#{UpdateAuditTrailChangesUI.UpdateAuditTrailChanges.municipality}" completeMethod="#{UpdateAuditTrailChangesUI.completeUpdateAuditTrailChanges}"
                            var="rv" itemLabel="#{rv.UpdateAuditTrailChangesDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="UpdateAuditTrailChangesConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="UpdateAuditTrailChanges" style="white-space: nowrap">#{rv.UpdateAuditTrailChangesDescription}</p:column>
       </p:autoComplete>         
       
*/

}
