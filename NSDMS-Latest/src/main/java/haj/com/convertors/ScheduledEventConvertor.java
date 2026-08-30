package haj.com.convertors;

import haj.com.entity.ScheduledEvent;
import haj.com.service.ScheduledEventService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "ScheduledEventConvertor")
public class ScheduledEventConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a ScheduledEvent
 	 * @author TechFinium 
 	 * @see    ScheduledEvent
 	 * @return ScheduledEvent
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new ScheduledEventService()
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
	 * Convert ScheduledEvent key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((ScheduledEvent)arg2).getId();
	}

/*
       <p:selectOneMenu id="ScheduledEventId" value="#{xxxUI.ScheduledEvent.xxxType}" converter="ScheduledEventConvertor" style="width:95%">
         <f:selectItems value="#{ScheduledEventUI.ScheduledEventList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="ScheduledEvent" for="ScheduledEventID"/>
        <p:autoComplete id="ScheduledEventID" value="#{ScheduledEventUI.ScheduledEvent.municipality}" completeMethod="#{ScheduledEventUI.completeScheduledEvent}"
                            var="rv" itemLabel="#{rv.ScheduledEventDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="ScheduledEventConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="ScheduledEvent" style="white-space: nowrap">#{rv.ScheduledEventDescription}</p:column>
       </p:autoComplete>         
       
*/

}
