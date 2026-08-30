package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.Statuses;
import haj.com.service.lookup.StatusesService;

@FacesConverter(value = "StatusesConvertor")
public class StatusesConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a Statuses
 	 * @author TechFinium 
 	 * @see    Statuses
 	 * @return Statuses
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new StatusesService()
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
	 * Convert Statuses key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((Statuses)arg2).getId();
	}

/*
       <p:selectOneMenu id="StatusesId" value="#{xxxUI.Statuses.xxxType}" converter="StatusesConvertor" style="width:95%">
         <f:selectItems value="#{StatusesUI.StatusesList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="Statuses" for="StatusesID"/>
        <p:autoComplete id="StatusesID" value="#{StatusesUI.Statuses.municipality}" completeMethod="#{StatusesUI.completeStatuses}"
                            var="rv" itemLabel="#{rv.StatusesDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="StatusesConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="Statuses" style="white-space: nowrap">#{rv.StatusesDescription}</p:column>
       </p:autoComplete>         
       
*/

}
