package haj.com.convertors;

import haj.com.entity.SetmisFile502Extracted;
import haj.com.service.SetmisFile502ExtractedService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "SetmisFile502ExtractedConvertor")
public class SetmisFile502ExtractedConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SetmisFile502Extracted
 	 * @author TechFinium 
 	 * @see    SetmisFile502Extracted
 	 * @return SetmisFile502Extracted
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SetmisFile502ExtractedService()
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
	 * Convert SetmisFile502Extracted key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((SetmisFile502Extracted)arg2).getId();
	}

/*
       <p:selectOneMenu id="SetmisFile502ExtractedId" value="#{xxxUI.SetmisFile502Extracted.xxxType}" converter="SetmisFile502ExtractedConvertor" style="width:95%">
         <f:selectItems value="#{SetmisFile502ExtractedUI.SetmisFile502ExtractedList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SetmisFile502Extracted" for="SetmisFile502ExtractedID"/>
        <p:autoComplete id="SetmisFile502ExtractedID" value="#{SetmisFile502ExtractedUI.SetmisFile502Extracted.municipality}" completeMethod="#{SetmisFile502ExtractedUI.completeSetmisFile502Extracted}"
                            var="rv" itemLabel="#{rv.SetmisFile502ExtractedDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SetmisFile502ExtractedConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SetmisFile502Extracted" style="white-space: nowrap">#{rv.SetmisFile502ExtractedDescription}</p:column>
       </p:autoComplete>         
       
*/

}
