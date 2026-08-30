package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.MailTemplates;
import haj.com.service.lookup.MailTemplatesService;

@FacesConverter(value = "MailTemplatesConvertor")
public class MailTemplatesConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a MailTemplates
 	 * @author TechFinium 
 	 * @see    MailTemplates
 	 * @return MailTemplates
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new MailTemplatesService()
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
	 * Convert MailTemplates key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return ""+((MailTemplates)arg2).getId();
	}

/*
       <p:selectOneMenu id="MailTemplatesId" value="#{xxxUI.MailTemplates.xxxType}" converter="MailTemplatesConvertor" style="width:95%">
         <f:selectItems value="#{MailTemplatesUI.MailTemplatesList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="MailTemplates" for="MailTemplatesID"/>
        <p:autoComplete id="MailTemplatesID" value="#{MailTemplatesUI.MailTemplates.municipality}" completeMethod="#{MailTemplatesUI.completeMailTemplates}"
                            var="rv" itemLabel="#{rv.MailTemplatesDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="MailTemplatesConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="MailTemplates" style="white-space: nowrap">#{rv.MailTemplatesDescription}</p:column>
       </p:autoComplete>         
       
*/

}
