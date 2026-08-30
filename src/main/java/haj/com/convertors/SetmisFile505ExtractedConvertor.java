package haj.com.convertors;

import haj.com.entity.SetmisFile505Extracted;
import haj.com.service.SetmisFile505ExtractedService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "SetmisFile505ExtractedConvertor")
public class SetmisFile505ExtractedConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SetmisFile505Extracted
 	 * @author TechFinium 
 	 * @see    SetmisFile505Extracted
 	 * @return SetmisFile505Extracted
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SetmisFile505ExtractedService()
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
	 * Convert SetmisFile505Extracted key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((SetmisFile505Extracted)arg2).getId();
	}

/*
       <p:selectOneMenu id="SetmisFile505ExtractedId" value="#{xxxUI.SetmisFile505Extracted.xxxType}" converter="SetmisFile505ExtractedConvertor" style="width:95%">
         <f:selectItems value="#{SetmisFile505ExtractedUI.SetmisFile505ExtractedList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SetmisFile505Extracted" for="SetmisFile505ExtractedID"/>
        <p:autoComplete id="SetmisFile505ExtractedID" value="#{SetmisFile505ExtractedUI.SetmisFile505Extracted.municipality}" completeMethod="#{SetmisFile505ExtractedUI.completeSetmisFile505Extracted}"
                            var="rv" itemLabel="#{rv.SetmisFile505ExtractedDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SetmisFile505ExtractedConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SetmisFile505Extracted" style="white-space: nowrap">#{rv.SetmisFile505ExtractedDescription}</p:column>
       </p:autoComplete>         
       
*/

}
