package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.CommunicatingRating;
import haj.com.service.lookup.CommunicatingRatingService;

// TODO: Auto-generated Javadoc
/**
 * The Class CommunicatingRatingConvertor.
 */
@FacesConverter(value = "CommunicatingRatingConvertor")
public class CommunicatingRatingConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a CommunicatingRating.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return CommunicatingRating
	 * @see    CommunicatingRating
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new CommunicatingRatingService()
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
	 * Convert CommunicatingRating key to String object.
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
		return ""+((CommunicatingRating)arg2).getId();
	}

/*
       <p:selectOneMenu id="CommunicatingRatingId" value="#{xxxUI.CommunicatingRating.xxxType}" converter="CommunicatingRatingConvertor" style="width:95%">
         <f:selectItems value="#{CommunicatingRatingUI.CommunicatingRatingList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="CommunicatingRating" for="CommunicatingRatingID"/>
        <p:autoComplete id="CommunicatingRatingID" value="#{CommunicatingRatingUI.CommunicatingRating.municipality}" completeMethod="#{CommunicatingRatingUI.completeCommunicatingRating}"
                            var="rv" itemLabel="#{rv.CommunicatingRatingDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="CommunicatingRatingConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="CommunicatingRating" style="white-space: nowrap">#{rv.CommunicatingRatingDescription}</p:column>
       </p:autoComplete>         
       
*/

}
