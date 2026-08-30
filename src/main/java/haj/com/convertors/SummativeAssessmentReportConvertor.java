package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.SummativeAssessmentReport;
import haj.com.service.SummativeAssessmentReportService;

@FacesConverter(value = "SummativeAssessmentReportConvertor")
public class SummativeAssessmentReportConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SummativeAssessmentReport
 	 * @author TechFinium 
 	 * @see    SummativeAssessmentReport
 	 * @return SummativeAssessmentReport
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SummativeAssessmentReportService()
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
	 * Convert SummativeAssessmentReport key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((SummativeAssessmentReport)arg2).getId();
	}

/*
       <p:selectOneMenu id="SummativeAssessmentReportId" value="#{xxxUI.SummativeAssessmentReport.xxxType}" converter="SummativeAssessmentReportConvertor" style="width:95%">
         <f:selectItems value="#{SummativeAssessmentReportUI.SummativeAssessmentReportList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SummativeAssessmentReport" for="SummativeAssessmentReportID"/>
        <p:autoComplete id="SummativeAssessmentReportID" value="#{SummativeAssessmentReportUI.SummativeAssessmentReport.municipality}" completeMethod="#{SummativeAssessmentReportUI.completeSummativeAssessmentReport}"
                            var="rv" itemLabel="#{rv.SummativeAssessmentReportDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SummativeAssessmentReportConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SummativeAssessmentReport" style="white-space: nowrap">#{rv.SummativeAssessmentReportDescription}</p:column>
       </p:autoComplete>         
       
*/

}
