package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyEmployerWA2Workplace;
import haj.com.service.lookup.LegacyEmployerWA2WorkplaceService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyEmployerWA2WorkplaceConvertor")
public class LegacyEmployerWA2WorkplaceConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyEmployerWA2Workplace
 	 * @author TechFinium 
 	 * @see    LegacyEmployerWA2Workplace
 	 * @return LegacyEmployerWA2Workplace
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyEmployerWA2WorkplaceService()
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
	 * Convert LegacyEmployerWA2Workplace key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyEmployerWA2Workplace)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyEmployerWA2WorkplaceId" value="#{xxxUI.LegacyEmployerWA2Workplace.xxxType}" converter="LegacyEmployerWA2WorkplaceConvertor" style="width:95%">
         <f:selectItems value="#{LegacyEmployerWA2WorkplaceUI.LegacyEmployerWA2WorkplaceList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyEmployerWA2Workplace" for="LegacyEmployerWA2WorkplaceID"/>
        <p:autoComplete id="LegacyEmployerWA2WorkplaceID" value="#{LegacyEmployerWA2WorkplaceUI.LegacyEmployerWA2Workplace.municipality}" completeMethod="#{LegacyEmployerWA2WorkplaceUI.completeLegacyEmployerWA2Workplace}"
                            var="rv" itemLabel="#{rv.LegacyEmployerWA2WorkplaceDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyEmployerWA2WorkplaceConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyEmployerWA2Workplace" style="white-space: nowrap">#{rv.LegacyEmployerWA2WorkplaceDescription}</p:column>
       </p:autoComplete>         
       
*/

}
