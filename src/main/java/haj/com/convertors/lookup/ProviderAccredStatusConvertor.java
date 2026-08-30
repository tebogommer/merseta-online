package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.ProviderAccredStatus;
import haj.com.service.lookup.ProviderAccredStatusService;

// TODO: Auto-generated Javadoc
/**
 * The Class ProviderAccredStatusConvertor.
 */
@FacesConverter(value = "ProviderAccredStatusConvertor")
public class ProviderAccredStatusConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a ProviderAccredStatus.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return ProviderAccredStatus
	 * @see    ProviderAccredStatus
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new ProviderAccredStatusService()
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
	 * Convert ProviderAccredStatus key to String object.
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
		return ""+((ProviderAccredStatus)arg2).getId();
	}

/*
       <p:selectOneMenu id="ProviderAccredStatusId" value="#{xxxUI.ProviderAccredStatus.xxxType}" converter="ProviderAccredStatusConvertor" style="width:95%">
         <f:selectItems value="#{ProviderAccredStatusUI.ProviderAccredStatusList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="ProviderAccredStatus" for="ProviderAccredStatusID"/>
        <p:autoComplete id="ProviderAccredStatusID" value="#{ProviderAccredStatusUI.ProviderAccredStatus.municipality}" completeMethod="#{ProviderAccredStatusUI.completeProviderAccredStatus}"
                            var="rv" itemLabel="#{rv.ProviderAccredStatusDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="ProviderAccredStatusConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="ProviderAccredStatus" style="white-space: nowrap">#{rv.ProviderAccredStatusDescription}</p:column>
       </p:autoComplete>         
       
*/

}
