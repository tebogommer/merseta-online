package haj.com.convertors;

import haj.com.entity.SetmisFile501Extracted;
import haj.com.service.SetmisFile501ExtractedService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "SetmisFile501ExtractedConvertor")
public class SetmisFile501ExtractedConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SetmisFile501Extracted
 	 * @author TechFinium 
 	 * @see    SetmisFile501Extracted
 	 * @return SetmisFile501Extracted
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SetmisFile501ExtractedService()
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
	 * Convert SetmisFile501Extracted key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((SetmisFile501Extracted)arg2).getId();
	}

/*
       <p:selectOneMenu id="SetmisFile501ExtractedId" value="#{xxxUI.SetmisFile501Extracted.xxxType}" converter="SetmisFile501ExtractedConvertor" style="width:95%">
         <f:selectItems value="#{SetmisFile501ExtractedUI.SetmisFile501ExtractedList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SetmisFile501Extracted" for="SetmisFile501ExtractedID"/>
        <p:autoComplete id="SetmisFile501ExtractedID" value="#{SetmisFile501ExtractedUI.SetmisFile501Extracted.municipality}" completeMethod="#{SetmisFile501ExtractedUI.completeSetmisFile501Extracted}"
                            var="rv" itemLabel="#{rv.SetmisFile501ExtractedDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SetmisFile501ExtractedConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SetmisFile501Extracted" style="white-space: nowrap">#{rv.SetmisFile501ExtractedDescription}</p:column>
       </p:autoComplete>         
       
*/

}
