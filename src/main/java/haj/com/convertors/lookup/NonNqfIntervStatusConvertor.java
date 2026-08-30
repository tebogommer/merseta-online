package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.NonNqfIntervStatus;
import haj.com.service.lookup.NonNqfIntervStatusService;

// TODO: Auto-generated Javadoc
/**
 * The Class NonNqfIntervStatusConvertor.
 */
@FacesConverter(value = "NonNqfIntervStatusConvertor")
public class NonNqfIntervStatusConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a NonNqfIntervStatus.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return NonNqfIntervStatus
	 * @see    NonNqfIntervStatus
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new NonNqfIntervStatusService()
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
	 * Convert NonNqfIntervStatus key to String object.
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
		return ""+((NonNqfIntervStatus)arg2).getId();
	}

/*
       <p:selectOneMenu id="NonNqfIntervStatusId" value="#{xxxUI.NonNqfIntervStatus.xxxType}" converter="NonNqfIntervStatusConvertor" style="width:95%">
         <f:selectItems value="#{NonNqfIntervStatusUI.NonNqfIntervStatusList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="NonNqfIntervStatus" for="NonNqfIntervStatusID"/>
        <p:autoComplete id="NonNqfIntervStatusID" value="#{NonNqfIntervStatusUI.NonNqfIntervStatus.municipality}" completeMethod="#{NonNqfIntervStatusUI.completeNonNqfIntervStatus}"
                            var="rv" itemLabel="#{rv.NonNqfIntervStatusDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="NonNqfIntervStatusConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="NonNqfIntervStatus" style="white-space: nowrap">#{rv.NonNqfIntervStatusDescription}</p:column>
       </p:autoComplete>         
       
*/

}
