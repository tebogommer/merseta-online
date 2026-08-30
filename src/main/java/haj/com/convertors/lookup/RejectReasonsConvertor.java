package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.RejectReasons;
import haj.com.service.lookup.RejectReasonsService;

// TODO: Auto-generated Javadoc
/**
 * The Class RejectReasonsConvertor.
 */
@FacesConverter(value = "RejectReasonsConvertor")
public class RejectReasonsConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a RejectReasons.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return RejectReasons
	 * @see    RejectReasons
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new RejectReasonsService()
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
	 * Convert RejectReasons key to String object.
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
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((RejectReasons)arg2).getId();
	}

/*
       <p:selectOneMenu id="RejectReasonsId" value="#{xxxUI.RejectReasons.xxxType}" converter="RejectReasonsConvertor" style="width:95%">
         <f:selectItems value="#{RejectReasonsUI.RejectReasonsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="RejectReasons" for="RejectReasonsID"/>
        <p:autoComplete id="RejectReasonsID" value="#{RejectReasonsUI.RejectReasons.municipality}" completeMethod="#{RejectReasonsUI.completeRejectReasons}"
                            var="rv" itemLabel="#{rv.RejectReasonsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="RejectReasonsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="RejectReasons" style="white-space: nowrap">#{rv.RejectReasonsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
