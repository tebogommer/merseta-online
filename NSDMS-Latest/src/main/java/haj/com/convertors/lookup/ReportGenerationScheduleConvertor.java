package haj.com.convertors.lookup;

import haj.com.entity.lookup.ReportGenerationSchedule;
import haj.com.service.lookup.ReportGenerationScheduleService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "ReportGenerationScheduleConvertor")
public class ReportGenerationScheduleConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a ReportGenerationSchedule
 	 * @author TechFinium 
 	 * @see    ReportGenerationSchedule
 	 * @return ReportGenerationSchedule
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new ReportGenerationScheduleService()
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
	 * Convert ReportGenerationSchedule key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((ReportGenerationSchedule)arg2).getId();
	}

/*
       <p:selectOneMenu id="ReportGenerationScheduleId" value="#{xxxUI.ReportGenerationSchedule.xxxType}" converter="ReportGenerationScheduleConvertor" style="width:95%">
         <f:selectItems value="#{ReportGenerationScheduleUI.ReportGenerationScheduleList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="ReportGenerationSchedule" for="ReportGenerationScheduleID"/>
        <p:autoComplete id="ReportGenerationScheduleID" value="#{ReportGenerationScheduleUI.ReportGenerationSchedule.municipality}" completeMethod="#{ReportGenerationScheduleUI.completeReportGenerationSchedule}"
                            var="rv" itemLabel="#{rv.ReportGenerationScheduleDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="ReportGenerationScheduleConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="ReportGenerationSchedule" style="white-space: nowrap">#{rv.ReportGenerationScheduleDescription}</p:column>
       </p:autoComplete>         
       
*/

}
