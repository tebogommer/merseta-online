package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.MgVerification;
import haj.com.service.MgVerificationService;

@FacesConverter(value = "MgVerificationConvertor")
public class MgVerificationConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a MgVerification
 	 * @author TechFinium 
 	 * @see    MgVerification
 	 * @return MgVerification
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new MgVerificationService()
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
	 * Convert MgVerification key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((MgVerification)arg2).getId();
	}

/*
       <p:selectOneMenu id="MgVerificationId" value="#{xxxUI.MgVerification.xxxType}" converter="MgVerificationConvertor" style="width:95%">
         <f:selectItems value="#{MgVerificationUI.MgVerificationList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="MgVerification" for="MgVerificationID"/>
        <p:autoComplete id="MgVerificationID" value="#{MgVerificationUI.MgVerification.municipality}" completeMethod="#{MgVerificationUI.completeMgVerification}"
                            var="rv" itemLabel="#{rv.MgVerificationDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="MgVerificationConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="MgVerification" style="white-space: nowrap">#{rv.MgVerificationDescription}</p:column>
       </p:autoComplete>         
       
*/

}
