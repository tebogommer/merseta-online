package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.SelfcaringRating;
import haj.com.service.lookup.SelfcaringRatingService;

// TODO: Auto-generated Javadoc
/**
 * The Class SelfcaringRatingConvertor.
 */
@FacesConverter(value = "SelfcaringRatingConvertor")
public class SelfcaringRatingConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SelfcaringRating.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return SelfcaringRating
	 * @see    SelfcaringRating
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SelfcaringRatingService()
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
	 * Convert SelfcaringRating key to String object.
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
		return ""+((SelfcaringRating)arg2).getId();
	}

/*
       <p:selectOneMenu id="SelfcaringRatingId" value="#{xxxUI.SelfcaringRating.xxxType}" converter="SelfcaringRatingConvertor" style="width:95%">
         <f:selectItems value="#{SelfcaringRatingUI.SelfcaringRatingList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SelfcaringRating" for="SelfcaringRatingID"/>
        <p:autoComplete id="SelfcaringRatingID" value="#{SelfcaringRatingUI.SelfcaringRating.municipality}" completeMethod="#{SelfcaringRatingUI.completeSelfcaringRating}"
                            var="rv" itemLabel="#{rv.SelfcaringRatingDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SelfcaringRatingConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SelfcaringRating" style="white-space: nowrap">#{rv.SelfcaringRatingDescription}</p:column>
       </p:autoComplete>         
       
*/

}
