package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyEmployerWA2Qualification;
import haj.com.service.lookup.LegacyEmployerWA2QualificationService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyEmployerWA2QualificationConvertor")
public class LegacyEmployerWA2QualificationConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyEmployerWA2Qualification
 	 * @author TechFinium 
 	 * @see    LegacyEmployerWA2Qualification
 	 * @return LegacyEmployerWA2Qualification
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyEmployerWA2QualificationService()
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
	 * Convert LegacyEmployerWA2Qualification key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyEmployerWA2Qualification)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyEmployerWA2QualificationId" value="#{xxxUI.LegacyEmployerWA2Qualification.xxxType}" converter="LegacyEmployerWA2QualificationConvertor" style="width:95%">
         <f:selectItems value="#{LegacyEmployerWA2QualificationUI.LegacyEmployerWA2QualificationList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyEmployerWA2Qualification" for="LegacyEmployerWA2QualificationID"/>
        <p:autoComplete id="LegacyEmployerWA2QualificationID" value="#{LegacyEmployerWA2QualificationUI.LegacyEmployerWA2Qualification.municipality}" completeMethod="#{LegacyEmployerWA2QualificationUI.completeLegacyEmployerWA2Qualification}"
                            var="rv" itemLabel="#{rv.LegacyEmployerWA2QualificationDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyEmployerWA2QualificationConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyEmployerWA2Qualification" style="white-space: nowrap">#{rv.LegacyEmployerWA2QualificationDescription}</p:column>
       </p:autoComplete>         
       
*/

}
