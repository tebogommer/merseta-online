package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.MandatoryGrantRecommendation;
import haj.com.service.MandatoryGrantRecommendationService;

@FacesConverter(value = "MandatoryGrantRecommendationConvertor")
public class MandatoryGrantRecommendationConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a MandatoryGrantRecommendation
 	 * @author TechFinium 
 	 * @see    MandatoryGrantRecommendation
 	 * @return MandatoryGrantRecommendation
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new MandatoryGrantRecommendationService()
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
	 * Convert MandatoryGrantRecommendation key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((MandatoryGrantRecommendation)arg2).getId();
	}

/*
       <p:selectOneMenu id="MandatoryGrantRecommendationId" value="#{xxxUI.MandatoryGrantRecommendation.xxxType}" converter="MandatoryGrantRecommendationConvertor" style="width:95%">
         <f:selectItems value="#{MandatoryGrantRecommendationUI.MandatoryGrantRecommendationList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="MandatoryGrantRecommendation" for="MandatoryGrantRecommendationID"/>
        <p:autoComplete id="MandatoryGrantRecommendationID" value="#{MandatoryGrantRecommendationUI.MandatoryGrantRecommendation.municipality}" completeMethod="#{MandatoryGrantRecommendationUI.completeMandatoryGrantRecommendation}"
                            var="rv" itemLabel="#{rv.MandatoryGrantRecommendationDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="MandatoryGrantRecommendationConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="MandatoryGrantRecommendation" style="white-space: nowrap">#{rv.MandatoryGrantRecommendationDescription}</p:column>
       </p:autoComplete>         
       
*/

}
