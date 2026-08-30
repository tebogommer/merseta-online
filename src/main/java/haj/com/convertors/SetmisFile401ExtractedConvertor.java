package haj.com.convertors;

import haj.com.entity.SetmisFile401Extracted;
import haj.com.service.SetmisFile401ExtractedService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "SetmisFile401ExtractedConvertor")
public class SetmisFile401ExtractedConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SetmisFile401Extracted
 	 * @author TechFinium 
 	 * @see    SetmisFile401Extracted
 	 * @return SetmisFile401Extracted
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SetmisFile401ExtractedService()
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
	 * Convert SetmisFile401Extracted key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((SetmisFile401Extracted)arg2).getId();
	}

/*
       <p:selectOneMenu id="SetmisFile401ExtractedId" value="#{xxxUI.SetmisFile401Extracted.xxxType}" converter="SetmisFile401ExtractedConvertor" style="width:95%">
         <f:selectItems value="#{SetmisFile401ExtractedUI.SetmisFile401ExtractedList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SetmisFile401Extracted" for="SetmisFile401ExtractedID"/>
        <p:autoComplete id="SetmisFile401ExtractedID" value="#{SetmisFile401ExtractedUI.SetmisFile401Extracted.municipality}" completeMethod="#{SetmisFile401ExtractedUI.completeSetmisFile401Extracted}"
                            var="rv" itemLabel="#{rv.SetmisFile401ExtractedDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SetmisFile401ExtractedConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SetmisFile401Extracted" style="white-space: nowrap">#{rv.SetmisFile401ExtractedDescription}</p:column>
       </p:autoComplete>         
       
*/

}
