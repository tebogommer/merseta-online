package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyLearnership;
import haj.com.service.lookup.LegacyLearnershipService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyLearnershipConvertor")
public class LegacyLearnershipConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyLearnership
 	 * @author TechFinium 
 	 * @see    LegacyLearnership
 	 * @return LegacyLearnership
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyLearnershipService()
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
	 * Convert LegacyLearnership key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyLearnership)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyLearnershipId" value="#{xxxUI.LegacyLearnership.xxxType}" converter="LegacyLearnershipConvertor" style="width:95%">
         <f:selectItems value="#{LegacyLearnershipUI.LegacyLearnershipList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyLearnership" for="LegacyLearnershipID"/>
        <p:autoComplete id="LegacyLearnershipID" value="#{LegacyLearnershipUI.LegacyLearnership.municipality}" completeMethod="#{LegacyLearnershipUI.completeLegacyLearnership}"
                            var="rv" itemLabel="#{rv.LegacyLearnershipDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyLearnershipConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyLearnership" style="white-space: nowrap">#{rv.LegacyLearnershipDescription}</p:column>
       </p:autoComplete>         
       
*/

}
