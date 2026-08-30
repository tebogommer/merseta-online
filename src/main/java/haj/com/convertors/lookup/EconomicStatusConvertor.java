package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.EconomicStatus;
import haj.com.service.lookup.EconomicStatusService;

// TODO: Auto-generated Javadoc
/**
 * The Class EconomicStatusConvertor.
 */
@FacesConverter(value = "EconomicStatusConvertor")
public class EconomicStatusConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a EconomicStatus.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return EconomicStatus
	 * @see    EconomicStatus
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new EconomicStatusService()
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
	 * Convert EconomicStatus key to String object.
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
		return ""+((EconomicStatus)arg2).getId();
	}

/*
       <p:selectOneMenu id="EconomicStatusId" value="#{xxxUI.EconomicStatus.xxxType}" converter="EconomicStatusConvertor" style="width:95%">
         <f:selectItems value="#{EconomicStatusUI.EconomicStatusList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="EconomicStatus" for="EconomicStatusID"/>
        <p:autoComplete id="EconomicStatusID" value="#{EconomicStatusUI.EconomicStatus.municipality}" completeMethod="#{EconomicStatusUI.completeEconomicStatus}"
                            var="rv" itemLabel="#{rv.EconomicStatusDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="EconomicStatusConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="EconomicStatus" style="white-space: nowrap">#{rv.EconomicStatusDescription}</p:column>
       </p:autoComplete>         
       
*/

}
