package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.CitizenResidentStatus;
import haj.com.service.lookup.CitizenResidentStatusService;

// TODO: Auto-generated Javadoc
/**
 * The Class CitizenResidentStatusConvertor.
 */
@FacesConverter(value = "CitizenResidentStatusConvertor")
public class CitizenResidentStatusConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a CitizenResidentStatus.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return CitizenResidentStatus
	 * @see    CitizenResidentStatus
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new CitizenResidentStatusService()
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
	 * Convert CitizenResidentStatus key to String object.
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
		return ""+((CitizenResidentStatus)arg2).getId();
	}

/*
       <p:selectOneMenu id="CitizenResidentStatusId" value="#{xxxUI.CitizenResidentStatus.xxxType}" converter="CitizenResidentStatusConvertor" style="width:95%">
         <f:selectItems value="#{CitizenResidentStatusUI.CitizenResidentStatusList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="CitizenResidentStatus" for="CitizenResidentStatusID"/>
        <p:autoComplete id="CitizenResidentStatusID" value="#{CitizenResidentStatusUI.CitizenResidentStatus.municipality}" completeMethod="#{CitizenResidentStatusUI.completeCitizenResidentStatus}"
                            var="rv" itemLabel="#{rv.CitizenResidentStatusDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="CitizenResidentStatusConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="CitizenResidentStatus" style="white-space: nowrap">#{rv.CitizenResidentStatusDescription}</p:column>
       </p:autoComplete>         
       
*/

}
