package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.SummativeAssessmentReportUnitStandards;
import haj.com.service.SummativeAssessmentReportUnitStandardsService;

@FacesConverter(value = "SummativeAssessmentReportUnitStandardsConvertor")
public class SummativeAssessmentReportUnitStandardsConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SummativeAssessmentReportUnitStandards
 	 * @author TechFinium 
 	 * @see    SummativeAssessmentReportUnitStandards
 	 * @return SummativeAssessmentReportUnitStandards
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SummativeAssessmentReportUnitStandardsService()
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
	 * Convert SummativeAssessmentReportUnitStandards key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((SummativeAssessmentReportUnitStandards)arg2).getId();
	}

/*
       <p:selectOneMenu id="SummativeAssessmentReportUnitStandardsId" value="#{xxxUI.SummativeAssessmentReportUnitStandards.xxxType}" converter="SummativeAssessmentReportUnitStandardsConvertor" style="width:95%">
         <f:selectItems value="#{SummativeAssessmentReportUnitStandardsUI.SummativeAssessmentReportUnitStandardsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SummativeAssessmentReportUnitStandards" for="SummativeAssessmentReportUnitStandardsID"/>
        <p:autoComplete id="SummativeAssessmentReportUnitStandardsID" value="#{SummativeAssessmentReportUnitStandardsUI.SummativeAssessmentReportUnitStandards.municipality}" completeMethod="#{SummativeAssessmentReportUnitStandardsUI.completeSummativeAssessmentReportUnitStandards}"
                            var="rv" itemLabel="#{rv.SummativeAssessmentReportUnitStandardsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SummativeAssessmentReportUnitStandardsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SummativeAssessmentReportUnitStandards" style="white-space: nowrap">#{rv.SummativeAssessmentReportUnitStandardsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
