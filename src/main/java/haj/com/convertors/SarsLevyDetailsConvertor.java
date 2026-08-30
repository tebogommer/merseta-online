package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.sars.SarsLevyDetails;
import haj.com.service.SarsLevyDetailsService;

// TODO: Auto-generated Javadoc
/**
 * The Class SarsLevyDetailsConvertor.
 */
@FacesConverter(value = "SarsLevyDetailsConvertor")
public class SarsLevyDetailsConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SarsLevyDetails.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return SarsLevyDetails
	 * @see    SarsLevyDetails
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SarsLevyDetailsService()
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
	 * Convert SarsLevyDetails key to String object.
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
		return ""+((SarsLevyDetails)arg2).getId();
	}

/*
       <p:selectOneMenu id="SarsLevyDetailsId" value="#{xxxUI.SarsLevyDetails.xxxType}" converter="SarsLevyDetailsConvertor" style="width:95%">
         <f:selectItems value="#{SarsLevyDetailsUI.SarsLevyDetailsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SarsLevyDetails" for="SarsLevyDetailsID"/>
        <p:autoComplete id="SarsLevyDetailsID" value="#{SarsLevyDetailsUI.SarsLevyDetails.municipality}" completeMethod="#{SarsLevyDetailsUI.completeSarsLevyDetails}"
                            var="rv" itemLabel="#{rv.SarsLevyDetailsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SarsLevyDetailsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SarsLevyDetails" style="white-space: nowrap">#{rv.SarsLevyDetailsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
