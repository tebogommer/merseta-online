package haj.com.convertors;

import haj.com.entity.GpBankAccountAlterAudit;
import haj.com.service.GpBankAccountAlterAuditService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "GpBankAccountAlterAuditConvertor")
public class GpBankAccountAlterAuditConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a GpBankAccountAlterAudit
 	 * @author TechFinium 
 	 * @see    GpBankAccountAlterAudit
 	 * @return GpBankAccountAlterAudit
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new GpBankAccountAlterAuditService()
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
	 * Convert GpBankAccountAlterAudit key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((GpBankAccountAlterAudit)arg2).getId();
	}

/*
       <p:selectOneMenu id="GpBankAccountAlterAuditId" value="#{xxxUI.GpBankAccountAlterAudit.xxxType}" converter="GpBankAccountAlterAuditConvertor" style="width:95%">
         <f:selectItems value="#{GpBankAccountAlterAuditUI.GpBankAccountAlterAuditList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="GpBankAccountAlterAudit" for="GpBankAccountAlterAuditID"/>
        <p:autoComplete id="GpBankAccountAlterAuditID" value="#{GpBankAccountAlterAuditUI.GpBankAccountAlterAudit.municipality}" completeMethod="#{GpBankAccountAlterAuditUI.completeGpBankAccountAlterAudit}"
                            var="rv" itemLabel="#{rv.GpBankAccountAlterAuditDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="GpBankAccountAlterAuditConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="GpBankAccountAlterAudit" style="white-space: nowrap">#{rv.GpBankAccountAlterAuditDescription}</p:column>
       </p:autoComplete>         
       
*/

}
