package haj.com.convertors;

import haj.com.entity.SetmisFile506Extracted;
import haj.com.service.SetmisFile506ExtractedService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "SetmisFile506ExtractedConvertor")
public class SetmisFile506ExtractedConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SetmisFile506Extracted
 	 * @author TechFinium 
 	 * @see    SetmisFile506Extracted
 	 * @return SetmisFile506Extracted
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SetmisFile506ExtractedService()
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
	 * Convert SetmisFile506Extracted key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((SetmisFile506Extracted)arg2).getId();
	}

/*
       <p:selectOneMenu id="SetmisFile506ExtractedId" value="#{xxxUI.SetmisFile506Extracted.xxxType}" converter="SetmisFile506ExtractedConvertor" style="width:95%">
         <f:selectItems value="#{SetmisFile506ExtractedUI.SetmisFile506ExtractedList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SetmisFile506Extracted" for="SetmisFile506ExtractedID"/>
        <p:autoComplete id="SetmisFile506ExtractedID" value="#{SetmisFile506ExtractedUI.SetmisFile506Extracted.municipality}" completeMethod="#{SetmisFile506ExtractedUI.completeSetmisFile506Extracted}"
                            var="rv" itemLabel="#{rv.SetmisFile506ExtractedDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SetmisFile506ExtractedConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SetmisFile506Extracted" style="white-space: nowrap">#{rv.SetmisFile506ExtractedDescription}</p:column>
       </p:autoComplete>         
       
*/

}
