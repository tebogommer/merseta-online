package haj.com.convertors.lookup;

import haj.com.entity.lookup.Validity;
import haj.com.service.lookup.ValidityService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "ValidityConvertor")
public class ValidityConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a Validity
 	 * @author TechFinium 
 	 * @see    Validity
 	 * @return Validity
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new ValidityService()
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
	 * Convert Validity key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((Validity)arg2).getId();
	}

/*
       <p:selectOneMenu id="ValidityId" value="#{xxxUI.Validity.xxxType}" converter="ValidityConvertor" style="width:95%">
         <f:selectItems value="#{ValidityUI.ValidityList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="Validity" for="ValidityID"/>
        <p:autoComplete id="ValidityID" value="#{ValidityUI.Validity.municipality}" completeMethod="#{ValidityUI.completeValidity}"
                            var="rv" itemLabel="#{rv.ValidityDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="ValidityConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="Validity" style="white-space: nowrap">#{rv.ValidityDescription}</p:column>
       </p:autoComplete>         
       
*/

}
