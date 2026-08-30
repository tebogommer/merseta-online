package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.RememberingRating;
import haj.com.service.lookup.RememberingRatingService;

// TODO: Auto-generated Javadoc
/**
 * The Class RememberingRatingConvertor.
 */
@FacesConverter(value = "RememberingRatingConvertor")
public class RememberingRatingConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a RememberingRating.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return RememberingRating
	 * @see    RememberingRating
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new RememberingRatingService()
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
	 * Convert RememberingRating key to String object.
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
		return ""+((RememberingRating)arg2).getId();
	}

/*
       <p:selectOneMenu id="RememberingRatingId" value="#{xxxUI.RememberingRating.xxxType}" converter="RememberingRatingConvertor" style="width:95%">
         <f:selectItems value="#{RememberingRatingUI.RememberingRatingList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="RememberingRating" for="RememberingRatingID"/>
        <p:autoComplete id="RememberingRatingID" value="#{RememberingRatingUI.RememberingRating.municipality}" completeMethod="#{RememberingRatingUI.completeRememberingRating}"
                            var="rv" itemLabel="#{rv.RememberingRatingDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="RememberingRatingConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="RememberingRating" style="white-space: nowrap">#{rv.RememberingRatingDescription}</p:column>
       </p:autoComplete>         
       
*/

}
