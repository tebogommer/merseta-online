package haj.com.convertors;

import haj.com.entity.ScheduleChangesLog;
import haj.com.service.ScheduleChangesLogService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "ScheduleChangesLogConvertor")
public class ScheduleChangesLogConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a ScheduleChangesLog
 	 * @author TechFinium 
 	 * @see    ScheduleChangesLog
 	 * @return ScheduleChangesLog
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new ScheduleChangesLogService()
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
	 * Convert ScheduleChangesLog key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((ScheduleChangesLog)arg2).getId();
	}

/*
       <p:selectOneMenu id="ScheduleChangesLogId" value="#{xxxUI.ScheduleChangesLog.xxxType}" converter="ScheduleChangesLogConvertor" style="width:95%">
         <f:selectItems value="#{ScheduleChangesLogUI.ScheduleChangesLogList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="ScheduleChangesLog" for="ScheduleChangesLogID"/>
        <p:autoComplete id="ScheduleChangesLogID" value="#{ScheduleChangesLogUI.ScheduleChangesLog.municipality}" completeMethod="#{ScheduleChangesLogUI.completeScheduleChangesLog}"
                            var="rv" itemLabel="#{rv.ScheduleChangesLogDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="ScheduleChangesLogConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="ScheduleChangesLog" style="white-space: nowrap">#{rv.ScheduleChangesLogDescription}</p:column>
       </p:autoComplete>         
       
*/

}
