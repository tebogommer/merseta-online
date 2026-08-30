package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacySkillsProgrammeAssessments;
import haj.com.service.lookup.LegacySkillsProgrammeAssessmentsService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacySkillsProgrammeAssessmentsConvertor")
public class LegacySkillsProgrammeAssessmentsConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacySkillsProgrammeAssessments
 	 * @author TechFinium 
 	 * @see    LegacySkillsProgrammeAssessments
 	 * @return LegacySkillsProgrammeAssessments
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacySkillsProgrammeAssessmentsService()
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
	 * Convert LegacySkillsProgrammeAssessments key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacySkillsProgrammeAssessments)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacySkillsProgrammeAssessmentsId" value="#{xxxUI.LegacySkillsProgrammeAssessments.xxxType}" converter="LegacySkillsProgrammeAssessmentsConvertor" style="width:95%">
         <f:selectItems value="#{LegacySkillsProgrammeAssessmentsUI.LegacySkillsProgrammeAssessmentsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacySkillsProgrammeAssessments" for="LegacySkillsProgrammeAssessmentsID"/>
        <p:autoComplete id="LegacySkillsProgrammeAssessmentsID" value="#{LegacySkillsProgrammeAssessmentsUI.LegacySkillsProgrammeAssessments.municipality}" completeMethod="#{LegacySkillsProgrammeAssessmentsUI.completeLegacySkillsProgrammeAssessments}"
                            var="rv" itemLabel="#{rv.LegacySkillsProgrammeAssessmentsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacySkillsProgrammeAssessmentsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacySkillsProgrammeAssessments" style="white-space: nowrap">#{rv.LegacySkillsProgrammeAssessmentsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
