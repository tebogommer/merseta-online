package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.SeeingRating;
import haj.com.service.lookup.SeeingRatingService;

// TODO: Auto-generated Javadoc
/**
 * The Class SeeingRatingConvertor.
 */
@FacesConverter(value = "SeeingRatingConvertor")
public class SeeingRatingConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SeeingRating.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return SeeingRating
	 * @see    SeeingRating
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SeeingRatingService()
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
	 * Convert SeeingRating key to String object.
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
		return ""+((SeeingRating)arg2).getId();
	}

/*
       <p:selectOneMenu id="SeeingRatingId" value="#{xxxUI.SeeingRating.xxxType}" converter="SeeingRatingConvertor" style="width:95%">
         <f:selectItems value="#{SeeingRatingUI.SeeingRatingList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SeeingRating" for="SeeingRatingID"/>
        <p:autoComplete id="SeeingRatingID" value="#{SeeingRatingUI.SeeingRating.municipality}" completeMethod="#{SeeingRatingUI.completeSeeingRating}"
                            var="rv" itemLabel="#{rv.SeeingRatingDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SeeingRatingConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SeeingRating" style="white-space: nowrap">#{rv.SeeingRatingDescription}</p:column>
       </p:autoComplete>         
       
*/

}
