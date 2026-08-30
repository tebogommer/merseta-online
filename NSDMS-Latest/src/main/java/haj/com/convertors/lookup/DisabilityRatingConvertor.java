package haj.com.convertors.lookup;

import haj.com.entity.lookup.DisabilityRating;
import haj.com.service.lookup.DisabilityRatingService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "DisabilityRatingConvertor")
public class DisabilityRatingConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a DisabilityRating
 	 * @author TechFinium 
 	 * @see    DisabilityRating
 	 * @return DisabilityRating
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new DisabilityRatingService()
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
	 * Convert DisabilityRating key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((DisabilityRating)arg2).getId();
	}

/*
       <p:selectOneMenu id="DisabilityRatingId" value="#{xxxUI.DisabilityRating.xxxType}" converter="DisabilityRatingConvertor" style="width:95%">
         <f:selectItems value="#{DisabilityRatingUI.DisabilityRatingList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="DisabilityRating" for="DisabilityRatingID"/>
        <p:autoComplete id="DisabilityRatingID" value="#{DisabilityRatingUI.DisabilityRating.municipality}" completeMethod="#{DisabilityRatingUI.completeDisabilityRating}"
                            var="rv" itemLabel="#{rv.DisabilityRatingDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="DisabilityRatingConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="DisabilityRating" style="white-space: nowrap">#{rv.DisabilityRatingDescription}</p:column>
       </p:autoComplete>         
       
*/

}
