package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyBursary;
import haj.com.service.lookup.LegacyBursaryService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyBursaryConvertor")
public class LegacyBursaryConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyBursary
 	 * @author TechFinium 
 	 * @see    LegacyBursary
 	 * @return LegacyBursary
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyBursaryService()
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
	 * Convert LegacyBursary key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyBursary)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyBursaryId" value="#{xxxUI.LegacyBursary.xxxType}" converter="LegacyBursaryConvertor" style="width:95%">
         <f:selectItems value="#{LegacyBursaryUI.LegacyBursaryList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyBursary" for="LegacyBursaryID"/>
        <p:autoComplete id="LegacyBursaryID" value="#{LegacyBursaryUI.LegacyBursary.municipality}" completeMethod="#{LegacyBursaryUI.completeLegacyBursary}"
                            var="rv" itemLabel="#{rv.LegacyBursaryDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyBursaryConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyBursary" style="white-space: nowrap">#{rv.LegacyBursaryDescription}</p:column>
       </p:autoComplete>         
       
*/

}
