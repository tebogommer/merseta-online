package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.TrainingImpactRating;
import haj.com.service.lookup.TrainingImpactRatingService;

// TODO: Auto-generated Javadoc
/**
 * The Class TrainingImpactRatingConvertor.
 */
@FacesConverter(value = "TrainingImpactRatingConvertor")
public class TrainingImpactRatingConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a TrainingImpactRating.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return TrainingImpactRating
	 * @see    TrainingImpactRating
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new TrainingImpactRatingService()
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
	 * Convert TrainingImpactRating key to String object.
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
		return ""+((TrainingImpactRating)arg2).getId();
	}

/*
       <p:selectOneMenu id="TrainingImpactRatingId" value="#{xxxUI.TrainingImpactRating.xxxType}" converter="TrainingImpactRatingConvertor" style="width:95%">
         <f:selectItems value="#{TrainingImpactRatingUI.TrainingImpactRatingList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="TrainingImpactRating" for="TrainingImpactRatingID"/>
        <p:autoComplete id="TrainingImpactRatingID" value="#{TrainingImpactRatingUI.TrainingImpactRating.municipality}" completeMethod="#{TrainingImpactRatingUI.completeTrainingImpactRating}"
                            var="rv" itemLabel="#{rv.TrainingImpactRatingDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="TrainingImpactRatingConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="TrainingImpactRating" style="white-space: nowrap">#{rv.TrainingImpactRatingDescription}</p:column>
       </p:autoComplete>         
       
*/

}
