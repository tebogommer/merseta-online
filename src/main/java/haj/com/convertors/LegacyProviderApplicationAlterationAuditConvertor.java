package haj.com.convertors;

import haj.com.entity.LegacyProviderApplicationAlterationAudit;
import haj.com.service.LegacyProviderApplicationAlterationAuditService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyProviderApplicationAlterationAuditConvertor")
public class LegacyProviderApplicationAlterationAuditConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyProviderApplicationAlterationAudit
 	 * @author TechFinium 
 	 * @see    LegacyProviderApplicationAlterationAudit
 	 * @return LegacyProviderApplicationAlterationAudit
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyProviderApplicationAlterationAuditService()
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
	 * Convert LegacyProviderApplicationAlterationAudit key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyProviderApplicationAlterationAudit)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyProviderApplicationAlterationAuditId" value="#{xxxUI.LegacyProviderApplicationAlterationAudit.xxxType}" converter="LegacyProviderApplicationAlterationAuditConvertor" style="width:95%">
         <f:selectItems value="#{LegacyProviderApplicationAlterationAuditUI.LegacyProviderApplicationAlterationAuditList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyProviderApplicationAlterationAudit" for="LegacyProviderApplicationAlterationAuditID"/>
        <p:autoComplete id="LegacyProviderApplicationAlterationAuditID" value="#{LegacyProviderApplicationAlterationAuditUI.LegacyProviderApplicationAlterationAudit.municipality}" completeMethod="#{LegacyProviderApplicationAlterationAuditUI.completeLegacyProviderApplicationAlterationAudit}"
                            var="rv" itemLabel="#{rv.LegacyProviderApplicationAlterationAuditDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyProviderApplicationAlterationAuditConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyProviderApplicationAlterationAudit" style="white-space: nowrap">#{rv.LegacyProviderApplicationAlterationAuditDescription}</p:column>
       </p:autoComplete>         
       
*/

}
