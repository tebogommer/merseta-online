package haj.com.convertors;

import haj.com.entity.SetmisFile100Extracted;
import haj.com.service.SetmisFile100ExtractedService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "SetmisFile100ExtractedConvertor")
public class SetmisFile100ExtractedConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SetmisFile100Extracted
 	 * @author TechFinium 
 	 * @see    SetmisFile100Extracted
 	 * @return SetmisFile100Extracted
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SetmisFile100ExtractedService()
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
	 * Convert SetmisFile100Extracted key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((SetmisFile100Extracted)arg2).getId();
	}

/*
       <p:selectOneMenu id="SetmisFile100ExtractedId" value="#{xxxUI.SetmisFile100Extracted.xxxType}" converter="SetmisFile100ExtractedConvertor" style="width:95%">
         <f:selectItems value="#{SetmisFile100ExtractedUI.SetmisFile100ExtractedList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SetmisFile100Extracted" for="SetmisFile100ExtractedID"/>
        <p:autoComplete id="SetmisFile100ExtractedID" value="#{SetmisFile100ExtractedUI.SetmisFile100Extracted.municipality}" completeMethod="#{SetmisFile100ExtractedUI.completeSetmisFile100Extracted}"
                            var="rv" itemLabel="#{rv.SetmisFile100ExtractedDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SetmisFile100ExtractedConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SetmisFile100Extracted" style="white-space: nowrap">#{rv.SetmisFile100ExtractedDescription}</p:column>
       </p:autoComplete>         
       
*/

}
