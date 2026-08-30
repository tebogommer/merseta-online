package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.Subdomain;
import haj.com.service.lookup.SubdomainService;

// TODO: Auto-generated Javadoc
/**
 * The Class SubdomainConvertor.
 */
@FacesConverter(value = "SubdomainConvertor")
public class SubdomainConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a Subdomain.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return Subdomain
	 * @see    Subdomain
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SubdomainService()
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
	 * Convert Subdomain key to String object.
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
		return ""+((Subdomain)arg2).getId();
	}

/*
       <p:selectOneMenu id="SubdomainId" value="#{xxxUI.Subdomain.xxxType}" converter="SubdomainConvertor" style="width:95%">
         <f:selectItems value="#{SubdomainUI.SubdomainList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="Subdomain" for="SubdomainID"/>
        <p:autoComplete id="SubdomainID" value="#{SubdomainUI.Subdomain.municipality}" completeMethod="#{SubdomainUI.completeSubdomain}"
                            var="rv" itemLabel="#{rv.SubdomainDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SubdomainConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="Subdomain" style="white-space: nowrap">#{rv.SubdomainDescription}</p:column>
       </p:autoComplete>         
       
*/

}
