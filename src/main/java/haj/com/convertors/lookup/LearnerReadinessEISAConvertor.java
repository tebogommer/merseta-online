package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.LearnerReadinessEISA;
import haj.com.service.lookup.LearnerReadinessEISAService;

// TODO: Auto-generated Javadoc
/**
 * The Class LearnerReadinessEISAConvertor.
 */
@FacesConverter(value = "LearnerReadinessEISAConvertor")
public class LearnerReadinessEISAConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LearnerReadinessEISA.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return LearnerReadinessEISA
	 * @see    LearnerReadinessEISA
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LearnerReadinessEISAService()
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
	 * Convert LearnerReadinessEISA key to String object.
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
		return ""+((LearnerReadinessEISA)arg2).getId();
	}

/*
       <p:selectOneMenu id="LearnerReadinessEISAId" value="#{xxxUI.LearnerReadinessEISA.xxxType}" converter="LearnerReadinessEISAConvertor" style="width:95%">
         <f:selectItems value="#{LearnerReadinessEISAUI.LearnerReadinessEISAList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LearnerReadinessEISA" for="LearnerReadinessEISAID"/>
        <p:autoComplete id="LearnerReadinessEISAID" value="#{LearnerReadinessEISAUI.LearnerReadinessEISA.municipality}" completeMethod="#{LearnerReadinessEISAUI.completeLearnerReadinessEISA}"
                            var="rv" itemLabel="#{rv.LearnerReadinessEISADescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LearnerReadinessEISAConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LearnerReadinessEISA" style="white-space: nowrap">#{rv.LearnerReadinessEISADescription}</p:column>
       </p:autoComplete>         
       
*/

}
