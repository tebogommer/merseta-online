package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.HearingRating;
import haj.com.service.lookup.HearingRatingService;

// TODO: Auto-generated Javadoc
/**
 * The Class HearingRatingConvertor.
 */
@FacesConverter(value = "HearingRatingConvertor")
public class HearingRatingConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a HearingRating.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return HearingRating
	 * @see    HearingRating
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new HearingRatingService()
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
	 * Convert HearingRating key to String object.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param arg2 the arg 2
	 * @return String
	 * @see    String
	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return ""+((HearingRating)arg2).getId();
	}

/*
       <p:selectOneMenu id="HearingRatingId" value="#{xxxUI.HearingRating.xxxType}" converter="HearingRatingConvertor" style="width:95%">
         <f:selectItems value="#{HearingRatingUI.HearingRatingList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="HearingRating" for="HearingRatingID"/>
        <p:autoComplete id="HearingRatingID" value="#{HearingRatingUI.HearingRating.municipality}" completeMethod="#{HearingRatingUI.completeHearingRating}"
                            var="rv" itemLabel="#{rv.HearingRatingDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="HearingRatingConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="HearingRating" style="white-space: nowrap">#{rv.HearingRatingDescription}</p:column>
       </p:autoComplete>         
       
*/

}
