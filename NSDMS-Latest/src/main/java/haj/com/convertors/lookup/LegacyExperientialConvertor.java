package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyExperiential;
import haj.com.service.lookup.LegacyExperientialService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyExperientialConvertor")
public class LegacyExperientialConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyExperiential
 	 * @author TechFinium 
 	 * @see    LegacyExperiential
 	 * @return LegacyExperiential
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyExperientialService()
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
	 * Convert LegacyExperiential key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyExperiential)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyExperientialId" value="#{xxxUI.LegacyExperiential.xxxType}" converter="LegacyExperientialConvertor" style="width:95%">
         <f:selectItems value="#{LegacyExperientialUI.LegacyExperientialList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyExperiential" for="LegacyExperientialID"/>
        <p:autoComplete id="LegacyExperientialID" value="#{LegacyExperientialUI.LegacyExperiential.municipality}" completeMethod="#{LegacyExperientialUI.completeLegacyExperiential}"
                            var="rv" itemLabel="#{rv.LegacyExperientialDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyExperientialConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyExperiential" style="white-space: nowrap">#{rv.LegacyExperientialDescription}</p:column>
       </p:autoComplete>         
       
*/

}
