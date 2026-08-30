package haj.com.convertors;

import haj.com.entity.DetailsOfTrainingArpl;
import haj.com.service.DetailsOfTrainingArplService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "DetailsOfTrainingArplConvertor")
public class DetailsOfTrainingArplConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a DetailsOfTrainingArpl
 	 * @author TechFinium 
 	 * @see    DetailsOfTrainingArpl
 	 * @return DetailsOfTrainingArpl
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new DetailsOfTrainingArplService()
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
	 * Convert DetailsOfTrainingArpl key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((DetailsOfTrainingArpl)arg2).getId();
	}

/*
       <p:selectOneMenu id="DetailsOfTrainingArplId" value="#{xxxUI.DetailsOfTrainingArpl.xxxType}" converter="DetailsOfTrainingArplConvertor" style="width:95%">
         <f:selectItems value="#{DetailsOfTrainingArplUI.DetailsOfTrainingArplList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="DetailsOfTrainingArpl" for="DetailsOfTrainingArplID"/>
        <p:autoComplete id="DetailsOfTrainingArplID" value="#{DetailsOfTrainingArplUI.DetailsOfTrainingArpl.municipality}" completeMethod="#{DetailsOfTrainingArplUI.completeDetailsOfTrainingArpl}"
                            var="rv" itemLabel="#{rv.DetailsOfTrainingArplDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="DetailsOfTrainingArplConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="DetailsOfTrainingArpl" style="white-space: nowrap">#{rv.DetailsOfTrainingArplDescription}</p:column>
       </p:autoComplete>         
       
*/

}
