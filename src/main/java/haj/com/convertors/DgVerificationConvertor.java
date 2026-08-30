package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.DgVerification;
import haj.com.service.DgVerificationService;

@FacesConverter(value = "DgVerificationConvertor")
public class DgVerificationConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a DgVerification
 	 * @author TechFinium 
 	 * @see    DgVerification
 	 * @return DgVerification
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new DgVerificationService()
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
	 * Convert DgVerification key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((DgVerification)arg2).getId();
	}

/*
       <p:selectOneMenu id="DgVerificationId" value="#{xxxUI.DgVerification.xxxType}" converter="DgVerificationConvertor" style="width:95%">
         <f:selectItems value="#{DgVerificationUI.DgVerificationList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="DgVerification" for="DgVerificationID"/>
        <p:autoComplete id="DgVerificationID" value="#{DgVerificationUI.DgVerification.municipality}" completeMethod="#{DgVerificationUI.completeDgVerification}"
                            var="rv" itemLabel="#{rv.DgVerificationDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="DgVerificationConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="DgVerification" style="white-space: nowrap">#{rv.DgVerificationDescription}</p:column>
       </p:autoComplete>         
       
*/

}
