package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.ScarcityReason;
import haj.com.service.lookup.ScarcityReasonService;

// TODO: Auto-generated Javadoc
/**
 * The Class ScarcityReasonConvertor.
 */
@FacesConverter(value = "ScarcityReasonConvertor")
public class ScarcityReasonConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a ScarcityReason.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return ScarcityReason
	 * @see    ScarcityReason
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new ScarcityReasonService()
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
	 * Convert ScarcityReason key to String object.
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
		return ""+((ScarcityReason)arg2).getId();
	}

/*
       <p:selectOneMenu id="ScarcityReasonId" value="#{xxxUI.ScarcityReason.xxxType}" converter="ScarcityReasonConvertor" style="width:95%">
         <f:selectItems value="#{ScarcityReasonUI.ScarcityReasonList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="ScarcityReason" for="ScarcityReasonID"/>
        <p:autoComplete id="ScarcityReasonID" value="#{ScarcityReasonUI.ScarcityReason.municipality}" completeMethod="#{ScarcityReasonUI.completeScarcityReason}"
                            var="rv" itemLabel="#{rv.ScarcityReasonDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="ScarcityReasonConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="ScarcityReason" style="white-space: nowrap">#{rv.ScarcityReasonDescription}</p:column>
       </p:autoComplete>         
       
*/

}
