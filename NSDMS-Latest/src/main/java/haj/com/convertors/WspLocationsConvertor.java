package haj.com.convertors;

import haj.com.entity.WspLocations;
import haj.com.service.WspLocationsService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "WspLocationsConvertor")
public class WspLocationsConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a WspLocations
 	 * @author TechFinium 
 	 * @see    WspLocations
 	 * @return WspLocations
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new WspLocationsService()
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
	 * Convert WspLocations key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((WspLocations)arg2).getId();
	}

/*
       <p:selectOneMenu id="WspLocationsId" value="#{xxxUI.WspLocations.xxxType}" converter="WspLocationsConvertor" style="width:95%">
         <f:selectItems value="#{WspLocationsUI.WspLocationsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="WspLocations" for="WspLocationsID"/>
        <p:autoComplete id="WspLocationsID" value="#{WspLocationsUI.WspLocations.municipality}" completeMethod="#{WspLocationsUI.completeWspLocations}"
                            var="rv" itemLabel="#{rv.WspLocationsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="WspLocationsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="WspLocations" style="white-space: nowrap">#{rv.WspLocationsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
