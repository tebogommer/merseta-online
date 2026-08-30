package haj.com.convertors;

import haj.com.entity.LegacyProviderApplicationSiteAllocation;
import haj.com.service.LegacyProviderApplicationSiteAllocationService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyProviderApplicationSiteAllocationConvertor")
public class LegacyProviderApplicationSiteAllocationConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyProviderApplicationSiteAllocation
 	 * @author TechFinium 
 	 * @see    LegacyProviderApplicationSiteAllocation
 	 * @return LegacyProviderApplicationSiteAllocation
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyProviderApplicationSiteAllocationService()
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
	 * Convert LegacyProviderApplicationSiteAllocation key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyProviderApplicationSiteAllocation)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyProviderApplicationSiteAllocationId" value="#{xxxUI.LegacyProviderApplicationSiteAllocation.xxxType}" converter="LegacyProviderApplicationSiteAllocationConvertor" style="width:95%">
         <f:selectItems value="#{LegacyProviderApplicationSiteAllocationUI.LegacyProviderApplicationSiteAllocationList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyProviderApplicationSiteAllocation" for="LegacyProviderApplicationSiteAllocationID"/>
        <p:autoComplete id="LegacyProviderApplicationSiteAllocationID" value="#{LegacyProviderApplicationSiteAllocationUI.LegacyProviderApplicationSiteAllocation.municipality}" completeMethod="#{LegacyProviderApplicationSiteAllocationUI.completeLegacyProviderApplicationSiteAllocation}"
                            var="rv" itemLabel="#{rv.LegacyProviderApplicationSiteAllocationDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyProviderApplicationSiteAllocationConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyProviderApplicationSiteAllocation" style="white-space: nowrap">#{rv.LegacyProviderApplicationSiteAllocationDescription}</p:column>
       </p:autoComplete>         
       
*/

}
