package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyInternship;
import haj.com.service.lookup.LegacyInternshipService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyInternshipConvertor")
public class LegacyInternshipConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyInternship
 	 * @author TechFinium 
 	 * @see    LegacyInternship
 	 * @return LegacyInternship
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyInternshipService()
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
	 * Convert LegacyInternship key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyInternship)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyInternshipId" value="#{xxxUI.LegacyInternship.xxxType}" converter="LegacyInternshipConvertor" style="width:95%">
         <f:selectItems value="#{LegacyInternshipUI.LegacyInternshipList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyInternship" for="LegacyInternshipID"/>
        <p:autoComplete id="LegacyInternshipID" value="#{LegacyInternshipUI.LegacyInternship.municipality}" completeMethod="#{LegacyInternshipUI.completeLegacyInternship}"
                            var="rv" itemLabel="#{rv.LegacyInternshipDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyInternshipConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyInternship" style="white-space: nowrap">#{rv.LegacyInternshipDescription}</p:column>
       </p:autoComplete>         
       
*/

}
