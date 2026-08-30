package haj.com.convertors;

import haj.com.entity.ProviderApplicationTradeTestAssessorsLink;
import haj.com.service.ProviderApplicationTradeTestAssessorsLinkService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "ProviderApplicationTradeTestAssessorsLinkConvertor")
public class ProviderApplicationTradeTestAssessorsLinkConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a ProviderApplicationTradeTestAssessorsLink
 	 * @author TechFinium 
 	 * @see    ProviderApplicationTradeTestAssessorsLink
 	 * @return ProviderApplicationTradeTestAssessorsLink
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new ProviderApplicationTradeTestAssessorsLinkService()
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
	 * Convert ProviderApplicationTradeTestAssessorsLink key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((ProviderApplicationTradeTestAssessorsLink)arg2).getId();
	}

/*
       <p:selectOneMenu id="ProviderApplicationTradeTestAssessorsLinkId" value="#{xxxUI.ProviderApplicationTradeTestAssessorsLink.xxxType}" converter="ProviderApplicationTradeTestAssessorsLinkConvertor" style="width:95%">
         <f:selectItems value="#{ProviderApplicationTradeTestAssessorsLinkUI.ProviderApplicationTradeTestAssessorsLinkList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="ProviderApplicationTradeTestAssessorsLink" for="ProviderApplicationTradeTestAssessorsLinkID"/>
        <p:autoComplete id="ProviderApplicationTradeTestAssessorsLinkID" value="#{ProviderApplicationTradeTestAssessorsLinkUI.ProviderApplicationTradeTestAssessorsLink.municipality}" completeMethod="#{ProviderApplicationTradeTestAssessorsLinkUI.completeProviderApplicationTradeTestAssessorsLink}"
                            var="rv" itemLabel="#{rv.ProviderApplicationTradeTestAssessorsLinkDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="ProviderApplicationTradeTestAssessorsLinkConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="ProviderApplicationTradeTestAssessorsLink" style="white-space: nowrap">#{rv.ProviderApplicationTradeTestAssessorsLinkDescription}</p:column>
       </p:autoComplete>         
       
*/

}
