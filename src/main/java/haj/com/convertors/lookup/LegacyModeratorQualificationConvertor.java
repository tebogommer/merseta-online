package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyModeratorQualification;
import haj.com.service.lookup.LegacyModeratorQualificationService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyModeratorQualificationConvertor")
public class LegacyModeratorQualificationConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyModeratorQualification
 	 * @author TechFinium 
 	 * @see    LegacyModeratorQualification
 	 * @return LegacyModeratorQualification
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyModeratorQualificationService()
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
	 * Convert LegacyModeratorQualification key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyModeratorQualification)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyModeratorQualificationId" value="#{xxxUI.LegacyModeratorQualification.xxxType}" converter="LegacyModeratorQualificationConvertor" style="width:95%">
         <f:selectItems value="#{LegacyModeratorQualificationUI.LegacyModeratorQualificationList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyModeratorQualification" for="LegacyModeratorQualificationID"/>
        <p:autoComplete id="LegacyModeratorQualificationID" value="#{LegacyModeratorQualificationUI.LegacyModeratorQualification.municipality}" completeMethod="#{LegacyModeratorQualificationUI.completeLegacyModeratorQualification}"
                            var="rv" itemLabel="#{rv.LegacyModeratorQualificationDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyModeratorQualificationConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyModeratorQualification" style="white-space: nowrap">#{rv.LegacyModeratorQualificationDescription}</p:column>
       </p:autoComplete>         
       
*/

}
