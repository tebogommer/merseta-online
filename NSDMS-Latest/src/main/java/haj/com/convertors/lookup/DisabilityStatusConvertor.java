package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.DisabilityStatus;
import haj.com.service.lookup.DisabilityStatusService;

// TODO: Auto-generated Javadoc
/**
 * The Class DisabilityStatusConvertor.
 */
@FacesConverter(value = "DisabilityStatusConvertor")
public class DisabilityStatusConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a DisabilityStatus.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return DisabilityStatus
	 * @see    DisabilityStatus
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new DisabilityStatusService()
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
	 * Convert DisabilityStatus key to String object.
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
		return ""+((DisabilityStatus)arg2).getId();
	}

/*
       <p:selectOneMenu id="DisabilityStatusId" value="#{xxxUI.DisabilityStatus.xxxType}" converter="DisabilityStatusConvertor" style="width:95%">
         <f:selectItems value="#{DisabilityStatusUI.DisabilityStatusList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="DisabilityStatus" for="DisabilityStatusID"/>
        <p:autoComplete id="DisabilityStatusID" value="#{DisabilityStatusUI.DisabilityStatus.municipality}" completeMethod="#{DisabilityStatusUI.completeDisabilityStatus}"
                            var="rv" itemLabel="#{rv.DisabilityStatusDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="DisabilityStatusConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="DisabilityStatus" style="white-space: nowrap">#{rv.DisabilityStatusDescription}</p:column>
       </p:autoComplete>         
       
*/

}
