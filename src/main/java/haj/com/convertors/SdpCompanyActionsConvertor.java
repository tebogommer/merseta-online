package haj.com.convertors;

import haj.com.entity.SdpCompanyActions;
import haj.com.service.SdpCompanyActionsService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "SdpCompanyActionsConvertor")
public class SdpCompanyActionsConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SdpCompanyActions
 	 * @author TechFinium 
 	 * @see    SdpCompanyActions
 	 * @return SdpCompanyActions
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SdpCompanyActionsService()
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
	 * Convert SdpCompanyActions key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((SdpCompanyActions)arg2).getId();
	}

/*
       <p:selectOneMenu id="SdpCompanyActionsId" value="#{xxxUI.SdpCompanyActions.xxxType}" converter="SdpCompanyActionsConvertor" style="width:95%">
         <f:selectItems value="#{SdpCompanyActionsUI.SdpCompanyActionsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SdpCompanyActions" for="SdpCompanyActionsID"/>
        <p:autoComplete id="SdpCompanyActionsID" value="#{SdpCompanyActionsUI.SdpCompanyActions.municipality}" completeMethod="#{SdpCompanyActionsUI.completeSdpCompanyActions}"
                            var="rv" itemLabel="#{rv.SdpCompanyActionsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SdpCompanyActionsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SdpCompanyActions" style="white-space: nowrap">#{rv.SdpCompanyActionsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
