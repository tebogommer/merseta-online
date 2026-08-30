package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyModeratorAccreditation;
import haj.com.service.lookup.LegacyModeratorAccreditationService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyModeratorAccreditationConvertor")
public class LegacyModeratorAccreditationConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyModeratorAccreditation
 	 * @author TechFinium 
 	 * @see    LegacyModeratorAccreditation
 	 * @return LegacyModeratorAccreditation
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyModeratorAccreditationService()
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
	 * Convert LegacyModeratorAccreditation key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyModeratorAccreditation)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyModeratorAccreditationId" value="#{xxxUI.LegacyModeratorAccreditation.xxxType}" converter="LegacyModeratorAccreditationConvertor" style="width:95%">
         <f:selectItems value="#{LegacyModeratorAccreditationUI.LegacyModeratorAccreditationList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyModeratorAccreditation" for="LegacyModeratorAccreditationID"/>
        <p:autoComplete id="LegacyModeratorAccreditationID" value="#{LegacyModeratorAccreditationUI.LegacyModeratorAccreditation.municipality}" completeMethod="#{LegacyModeratorAccreditationUI.completeLegacyModeratorAccreditation}"
                            var="rv" itemLabel="#{rv.LegacyModeratorAccreditationDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyModeratorAccreditationConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyModeratorAccreditation" style="white-space: nowrap">#{rv.LegacyModeratorAccreditationDescription}</p:column>
       </p:autoComplete>         
       
*/

}
