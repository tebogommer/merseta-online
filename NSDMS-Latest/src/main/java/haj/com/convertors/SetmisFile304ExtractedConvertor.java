package haj.com.convertors;

import haj.com.entity.SetmisFile304Extracted;
import haj.com.service.SetmisFile304ExtractedService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "SetmisFile304ExtractedConvertor")
public class SetmisFile304ExtractedConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SetmisFile304Extracted
 	 * @author TechFinium 
 	 * @see    SetmisFile304Extracted
 	 * @return SetmisFile304Extracted
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SetmisFile304ExtractedService()
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
	 * Convert SetmisFile304Extracted key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((SetmisFile304Extracted)arg2).getId();
	}

/*
       <p:selectOneMenu id="SetmisFile304ExtractedId" value="#{xxxUI.SetmisFile304Extracted.xxxType}" converter="SetmisFile304ExtractedConvertor" style="width:95%">
         <f:selectItems value="#{SetmisFile304ExtractedUI.SetmisFile304ExtractedList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SetmisFile304Extracted" for="SetmisFile304ExtractedID"/>
        <p:autoComplete id="SetmisFile304ExtractedID" value="#{SetmisFile304ExtractedUI.SetmisFile304Extracted.municipality}" completeMethod="#{SetmisFile304ExtractedUI.completeSetmisFile304Extracted}"
                            var="rv" itemLabel="#{rv.SetmisFile304ExtractedDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SetmisFile304ExtractedConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SetmisFile304Extracted" style="white-space: nowrap">#{rv.SetmisFile304ExtractedDescription}</p:column>
       </p:autoComplete>         
       
*/

}
