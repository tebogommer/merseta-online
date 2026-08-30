package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyApprenticeship;
import haj.com.service.lookup.LegacyApprenticeshipService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyApprenticeshipConvertor")
public class LegacyApprenticeshipConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyApprenticeship
 	 * @author TechFinium 
 	 * @see    LegacyApprenticeship
 	 * @return LegacyApprenticeship
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyApprenticeshipService()
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
	 * Convert LegacyApprenticeship key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyApprenticeship)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyApprenticeshipId" value="#{xxxUI.LegacyApprenticeship.xxxType}" converter="LegacyApprenticeshipConvertor" style="width:95%">
         <f:selectItems value="#{LegacyApprenticeshipUI.LegacyApprenticeshipList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyApprenticeship" for="LegacyApprenticeshipID"/>
        <p:autoComplete id="LegacyApprenticeshipID" value="#{LegacyApprenticeshipUI.LegacyApprenticeship.municipality}" completeMethod="#{LegacyApprenticeshipUI.completeLegacyApprenticeship}"
                            var="rv" itemLabel="#{rv.LegacyApprenticeshipDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyApprenticeshipConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyApprenticeship" style="white-space: nowrap">#{rv.LegacyApprenticeshipDescription}</p:column>
       </p:autoComplete>         
       
*/

}
