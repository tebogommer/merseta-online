package haj.com.convertors;

import haj.com.entity.SetmisFile500Extracted;
import haj.com.service.SetmisFile500ExtractedService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "SetmisFile500ExtractedConvertor")
public class SetmisFile500ExtractedConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SetmisFile500Extracted
 	 * @author TechFinium 
 	 * @see    SetmisFile500Extracted
 	 * @return SetmisFile500Extracted
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SetmisFile500ExtractedService()
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
	 * Convert SetmisFile500Extracted key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((SetmisFile500Extracted)arg2).getId();
	}

/*
       <p:selectOneMenu id="SetmisFile500ExtractedId" value="#{xxxUI.SetmisFile500Extracted.xxxType}" converter="SetmisFile500ExtractedConvertor" style="width:95%">
         <f:selectItems value="#{SetmisFile500ExtractedUI.SetmisFile500ExtractedList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SetmisFile500Extracted" for="SetmisFile500ExtractedID"/>
        <p:autoComplete id="SetmisFile500ExtractedID" value="#{SetmisFile500ExtractedUI.SetmisFile500Extracted.municipality}" completeMethod="#{SetmisFile500ExtractedUI.completeSetmisFile500Extracted}"
                            var="rv" itemLabel="#{rv.SetmisFile500ExtractedDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SetmisFile500ExtractedConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SetmisFile500Extracted" style="white-space: nowrap">#{rv.SetmisFile500ExtractedDescription}</p:column>
       </p:autoComplete>         
       
*/

}
