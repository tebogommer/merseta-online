package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.WalkingRating;
import haj.com.service.lookup.WalkingRatingService;

// TODO: Auto-generated Javadoc
/**
 * The Class WalkingRatingConvertor.
 */
@FacesConverter(value = "WalkingRatingConvertor")
public class WalkingRatingConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a WalkingRating.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return WalkingRating
	 * @see    WalkingRating
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new WalkingRatingService()
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
	 * Convert WalkingRating key to String object.
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
		return ""+((WalkingRating)arg2).getId();
	}

/*
       <p:selectOneMenu id="WalkingRatingId" value="#{xxxUI.WalkingRating.xxxType}" converter="WalkingRatingConvertor" style="width:95%">
         <f:selectItems value="#{WalkingRatingUI.WalkingRatingList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="WalkingRating" for="WalkingRatingID"/>
        <p:autoComplete id="WalkingRatingID" value="#{WalkingRatingUI.WalkingRating.municipality}" completeMethod="#{WalkingRatingUI.completeWalkingRating}"
                            var="rv" itemLabel="#{rv.WalkingRatingDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="WalkingRatingConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="WalkingRating" style="white-space: nowrap">#{rv.WalkingRatingDescription}</p:column>
       </p:autoComplete>         
       
*/

}
