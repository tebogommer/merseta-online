package haj.com.convertors;

import haj.com.entity.SmeQualifications;
import haj.com.service.SmeQualificationsService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "SmeQualificationsConvertor")
public class SmeQualificationsConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SmeQualifications
 	 * @author TechFinium 
 	 * @see    SmeQualifications
 	 * @return SmeQualifications
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SmeQualificationsService()
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
	 * Convert SmeQualifications key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((SmeQualifications)arg2).getId();
	}

/*
       <p:selectOneMenu id="SmeQualificationsId" value="#{xxxUI.SmeQualifications.xxxType}" converter="SmeQualificationsConvertor" style="width:95%">
         <f:selectItems value="#{SmeQualificationsUI.SmeQualificationsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SmeQualifications" for="SmeQualificationsID"/>
        <p:autoComplete id="SmeQualificationsID" value="#{SmeQualificationsUI.SmeQualifications.municipality}" completeMethod="#{SmeQualificationsUI.completeSmeQualifications}"
                            var="rv" itemLabel="#{rv.SmeQualificationsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SmeQualificationsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SmeQualifications" style="white-space: nowrap">#{rv.SmeQualificationsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
