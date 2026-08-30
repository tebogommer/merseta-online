package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.ProviderStatus;
import haj.com.entity.lookup.Qualification;
import haj.com.service.lookup.ProviderStatusService;

// TODO: Auto-generated Javadoc
/**
 * The Class ProviderStatusConvertor.
 */
@FacesConverter(value = "ProviderStatusConvertor")
public class ProviderStatusConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a ProviderStatus.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return ProviderStatus
	 * @see    ProviderStatus
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new ProviderStatusService()
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
	 * Convert ProviderStatus key to String object.
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
		if(arg2!=null && ((ProviderStatus)arg2).getId() !=null){
			return ""+((ProviderStatus)arg2).getId();
		}else {
			return "";
		}	
	}

/*
       <p:selectOneMenu id="ProviderStatusId" value="#{xxxUI.ProviderStatus.xxxType}" converter="ProviderStatusConvertor" style="width:95%">
         <f:selectItems value="#{ProviderStatusUI.ProviderStatusList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="ProviderStatus" for="ProviderStatusID"/>
        <p:autoComplete id="ProviderStatusID" value="#{ProviderStatusUI.ProviderStatus.municipality}" completeMethod="#{ProviderStatusUI.completeProviderStatus}"
                            var="rv" itemLabel="#{rv.ProviderStatusDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="ProviderStatusConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="ProviderStatus" style="white-space: nowrap">#{rv.ProviderStatusDescription}</p:column>
       </p:autoComplete>         
       
*/

}
