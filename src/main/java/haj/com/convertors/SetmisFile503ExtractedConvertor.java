package haj.com.convertors;

import haj.com.entity.SetmisFile503Extracted;
import haj.com.service.SetmisFile503ExtractedService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "SetmisFile503ExtractedConvertor")
public class SetmisFile503ExtractedConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SetmisFile503Extracted
 	 * @author TechFinium 
 	 * @see    SetmisFile503Extracted
 	 * @return SetmisFile503Extracted
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SetmisFile503ExtractedService()
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
	 * Convert SetmisFile503Extracted key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((SetmisFile503Extracted)arg2).getId();
	}

/*
       <p:selectOneMenu id="SetmisFile503ExtractedId" value="#{xxxUI.SetmisFile503Extracted.xxxType}" converter="SetmisFile503ExtractedConvertor" style="width:95%">
         <f:selectItems value="#{SetmisFile503ExtractedUI.SetmisFile503ExtractedList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SetmisFile503Extracted" for="SetmisFile503ExtractedID"/>
        <p:autoComplete id="SetmisFile503ExtractedID" value="#{SetmisFile503ExtractedUI.SetmisFile503Extracted.municipality}" completeMethod="#{SetmisFile503ExtractedUI.completeSetmisFile503Extracted}"
                            var="rv" itemLabel="#{rv.SetmisFile503ExtractedDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SetmisFile503ExtractedConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SetmisFile503Extracted" style="white-space: nowrap">#{rv.SetmisFile503ExtractedDescription}</p:column>
       </p:autoComplete>         
       
*/

}
