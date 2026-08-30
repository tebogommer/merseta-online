package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyDTTCQualification;
import haj.com.service.lookup.LegacyDTTCQualificationService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyDTTCQualificationConvertor")
public class LegacyDTTCQualificationConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyDTTCQualification
 	 * @author TechFinium 
 	 * @see    LegacyDTTCQualification
 	 * @return LegacyDTTCQualification
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyDTTCQualificationService()
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
	 * Convert LegacyDTTCQualification key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyDTTCQualification)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyDTTCQualificationId" value="#{xxxUI.LegacyDTTCQualification.xxxType}" converter="LegacyDTTCQualificationConvertor" style="width:95%">
         <f:selectItems value="#{LegacyDTTCQualificationUI.LegacyDTTCQualificationList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyDTTCQualification" for="LegacyDTTCQualificationID"/>
        <p:autoComplete id="LegacyDTTCQualificationID" value="#{LegacyDTTCQualificationUI.LegacyDTTCQualification.municipality}" completeMethod="#{LegacyDTTCQualificationUI.completeLegacyDTTCQualification}"
                            var="rv" itemLabel="#{rv.LegacyDTTCQualificationDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyDTTCQualificationConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyDTTCQualification" style="white-space: nowrap">#{rv.LegacyDTTCQualificationDescription}</p:column>
       </p:autoComplete>         
       
*/

}
