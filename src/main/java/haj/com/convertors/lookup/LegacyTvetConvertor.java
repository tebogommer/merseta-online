package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyTvet;
import haj.com.service.lookup.LegacyTvetService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyTvetConvertor")
public class LegacyTvetConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyTvet
 	 * @author TechFinium 
 	 * @see    LegacyTvet
 	 * @return LegacyTvet
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyTvetService()
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
	 * Convert LegacyTvet key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyTvet)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyTvetId" value="#{xxxUI.LegacyTvet.xxxType}" converter="LegacyTvetConvertor" style="width:95%">
         <f:selectItems value="#{LegacyTvetUI.LegacyTvetList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyTvet" for="LegacyTvetID"/>
        <p:autoComplete id="LegacyTvetID" value="#{LegacyTvetUI.LegacyTvet.municipality}" completeMethod="#{LegacyTvetUI.completeLegacyTvet}"
                            var="rv" itemLabel="#{rv.LegacyTvetDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyTvetConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyTvet" style="white-space: nowrap">#{rv.LegacyTvetDescription}</p:column>
       </p:autoComplete>         
       
*/

}
