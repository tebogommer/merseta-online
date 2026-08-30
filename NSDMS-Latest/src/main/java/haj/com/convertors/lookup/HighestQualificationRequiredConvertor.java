package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.HighestQualificationRequired;
import haj.com.service.lookup.HighestQualificationRequiredService;

@FacesConverter(value = "HighestQualificationRequiredConvertor")
public class HighestQualificationRequiredConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a HighestQualificationRequired
 	 * @author TechFinium 
 	 * @see    HighestQualificationRequired
 	 * @return HighestQualificationRequired
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new HighestQualificationRequiredService()
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
	 * Convert HighestQualificationRequired key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((HighestQualificationRequired)arg2).getId();
	}

/*
       <p:selectOneMenu id="HighestQualificationRequiredId" value="#{xxxUI.HighestQualificationRequired.xxxType}" converter="HighestQualificationRequiredConvertor" style="width:95%">
         <f:selectItems value="#{HighestQualificationRequiredUI.HighestQualificationRequiredList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="HighestQualificationRequired" for="HighestQualificationRequiredID"/>
        <p:autoComplete id="HighestQualificationRequiredID" value="#{HighestQualificationRequiredUI.HighestQualificationRequired.municipality}" completeMethod="#{HighestQualificationRequiredUI.completeHighestQualificationRequired}"
                            var="rv" itemLabel="#{rv.HighestQualificationRequiredDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="HighestQualificationRequiredConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="HighestQualificationRequired" style="white-space: nowrap">#{rv.HighestQualificationRequiredDescription}</p:column>
       </p:autoComplete>         
       
*/

}
