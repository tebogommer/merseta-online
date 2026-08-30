package haj.com.convertors;

import haj.com.entity.SetmisFile400Extracted;
import haj.com.service.SetmisFile400ExtractedService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "SetmisFile400ExtractedConvertor")
public class SetmisFile400ExtractedConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SetmisFile400Extracted
 	 * @author TechFinium 
 	 * @see    SetmisFile400Extracted
 	 * @return SetmisFile400Extracted
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SetmisFile400ExtractedService()
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
	 * Convert SetmisFile400Extracted key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((SetmisFile400Extracted)arg2).getId();
	}

/*
       <p:selectOneMenu id="SetmisFile400ExtractedId" value="#{xxxUI.SetmisFile400Extracted.xxxType}" converter="SetmisFile400ExtractedConvertor" style="width:95%">
         <f:selectItems value="#{SetmisFile400ExtractedUI.SetmisFile400ExtractedList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SetmisFile400Extracted" for="SetmisFile400ExtractedID"/>
        <p:autoComplete id="SetmisFile400ExtractedID" value="#{SetmisFile400ExtractedUI.SetmisFile400Extracted.municipality}" completeMethod="#{SetmisFile400ExtractedUI.completeSetmisFile400Extracted}"
                            var="rv" itemLabel="#{rv.SetmisFile400ExtractedDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SetmisFile400ExtractedConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SetmisFile400Extracted" style="white-space: nowrap">#{rv.SetmisFile400ExtractedDescription}</p:column>
       </p:autoComplete>         
       
*/

}
