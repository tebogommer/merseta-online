package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.SupportEmails;
import haj.com.service.SupportEmailsService;

@FacesConverter(value = "SupportEmailsConvertor")
public class SupportEmailsConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SupportEmails
 	 * @author TechFinium 
 	 * @see    SupportEmails
 	 * @return SupportEmails
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SupportEmailsService()
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
	 * Convert SupportEmails key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((SupportEmails)arg2).getId();
	}

/*
       <p:selectOneMenu id="SupportEmailsId" value="#{xxxUI.SupportEmails.xxxType}" converter="SupportEmailsConvertor" style="width:95%">
         <f:selectItems value="#{SupportEmailsUI.SupportEmailsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SupportEmails" for="SupportEmailsID"/>
        <p:autoComplete id="SupportEmailsID" value="#{SupportEmailsUI.SupportEmails.municipality}" completeMethod="#{SupportEmailsUI.completeSupportEmails}"
                            var="rv" itemLabel="#{rv.SupportEmailsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SupportEmailsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SupportEmails" style="white-space: nowrap">#{rv.SupportEmailsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
