package haj.com.convertors.lookup;

import haj.com.entity.lookup.OfoQualificationLink;
import haj.com.service.lookup.OfoQualificationLinkService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "OfoQualificationLinkConvertor")
public class OfoQualificationLinkConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a OfoQualificationLink
 	 * @author TechFinium 
 	 * @see    OfoQualificationLink
 	 * @return OfoQualificationLink
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new OfoQualificationLinkService()
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
	 * Convert OfoQualificationLink key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((OfoQualificationLink)arg2).getId();
	}

/*
       <p:selectOneMenu id="OfoQualificationLinkId" value="#{xxxUI.OfoQualificationLink.xxxType}" converter="OfoQualificationLinkConvertor" style="width:95%">
         <f:selectItems value="#{OfoQualificationLinkUI.OfoQualificationLinkList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="OfoQualificationLink" for="OfoQualificationLinkID"/>
        <p:autoComplete id="OfoQualificationLinkID" value="#{OfoQualificationLinkUI.OfoQualificationLink.municipality}" completeMethod="#{OfoQualificationLinkUI.completeOfoQualificationLink}"
                            var="rv" itemLabel="#{rv.OfoQualificationLinkDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="OfoQualificationLinkConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="OfoQualificationLink" style="white-space: nowrap">#{rv.OfoQualificationLinkDescription}</p:column>
       </p:autoComplete>         
       
*/

}
