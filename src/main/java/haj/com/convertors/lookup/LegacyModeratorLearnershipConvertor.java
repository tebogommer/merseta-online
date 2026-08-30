package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyModeratorLearnership;
import haj.com.service.lookup.LegacyModeratorLearnershipService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyModeratorLearnershipConvertor")
public class LegacyModeratorLearnershipConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyModeratorLearnership
 	 * @author TechFinium 
 	 * @see    LegacyModeratorLearnership
 	 * @return LegacyModeratorLearnership
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyModeratorLearnershipService()
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
	 * Convert LegacyModeratorLearnership key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyModeratorLearnership)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyModeratorLearnershipId" value="#{xxxUI.LegacyModeratorLearnership.xxxType}" converter="LegacyModeratorLearnershipConvertor" style="width:95%">
         <f:selectItems value="#{LegacyModeratorLearnershipUI.LegacyModeratorLearnershipList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyModeratorLearnership" for="LegacyModeratorLearnershipID"/>
        <p:autoComplete id="LegacyModeratorLearnershipID" value="#{LegacyModeratorLearnershipUI.LegacyModeratorLearnership.municipality}" completeMethod="#{LegacyModeratorLearnershipUI.completeLegacyModeratorLearnership}"
                            var="rv" itemLabel="#{rv.LegacyModeratorLearnershipDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyModeratorLearnershipConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyModeratorLearnership" style="white-space: nowrap">#{rv.LegacyModeratorLearnershipDescription}</p:column>
       </p:autoComplete>         
       
*/

}
