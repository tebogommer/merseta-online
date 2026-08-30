package haj.com.convertors;

import haj.com.entity.DetailsOfExperienceArpl;
import haj.com.service.DetailsOfExperienceArplService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "DetailsOfExperienceArplConvertor")
public class DetailsOfExperienceArplConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a DetailsOfExperienceArpl
 	 * @author TechFinium 
 	 * @see    DetailsOfExperienceArpl
 	 * @return DetailsOfExperienceArpl
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new DetailsOfExperienceArplService()
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
	 * Convert DetailsOfExperienceArpl key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((DetailsOfExperienceArpl)arg2).getId();
	}

/*
       <p:selectOneMenu id="DetailsOfExperienceArplId" value="#{xxxUI.DetailsOfExperienceArpl.xxxType}" converter="DetailsOfExperienceArplConvertor" style="width:95%">
         <f:selectItems value="#{DetailsOfExperienceArplUI.DetailsOfExperienceArplList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="DetailsOfExperienceArpl" for="DetailsOfExperienceArplID"/>
        <p:autoComplete id="DetailsOfExperienceArplID" value="#{DetailsOfExperienceArplUI.DetailsOfExperienceArpl.municipality}" completeMethod="#{DetailsOfExperienceArplUI.completeDetailsOfExperienceArpl}"
                            var="rv" itemLabel="#{rv.DetailsOfExperienceArplDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="DetailsOfExperienceArplConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="DetailsOfExperienceArpl" style="white-space: nowrap">#{rv.DetailsOfExperienceArplDescription}</p:column>
       </p:autoComplete>         
       
*/

}
