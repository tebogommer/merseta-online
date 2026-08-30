package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyAssessorSkillsProgramme;
import haj.com.service.lookup.LegacyAssessorSkillsProgrammeService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyAssessorSkillsProgrammeConvertor")
public class LegacyAssessorSkillsProgrammeConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyAssessorSkillsProgramme
 	 * @author TechFinium 
 	 * @see    LegacyAssessorSkillsProgramme
 	 * @return LegacyAssessorSkillsProgramme
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyAssessorSkillsProgrammeService()
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
	 * Convert LegacyAssessorSkillsProgramme key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyAssessorSkillsProgramme)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyAssessorSkillsProgrammeId" value="#{xxxUI.LegacyAssessorSkillsProgramme.xxxType}" converter="LegacyAssessorSkillsProgrammeConvertor" style="width:95%">
         <f:selectItems value="#{LegacyAssessorSkillsProgrammeUI.LegacyAssessorSkillsProgrammeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyAssessorSkillsProgramme" for="LegacyAssessorSkillsProgrammeID"/>
        <p:autoComplete id="LegacyAssessorSkillsProgrammeID" value="#{LegacyAssessorSkillsProgrammeUI.LegacyAssessorSkillsProgramme.municipality}" completeMethod="#{LegacyAssessorSkillsProgrammeUI.completeLegacyAssessorSkillsProgramme}"
                            var="rv" itemLabel="#{rv.LegacyAssessorSkillsProgrammeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyAssessorSkillsProgrammeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyAssessorSkillsProgramme" style="white-space: nowrap">#{rv.LegacyAssessorSkillsProgrammeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
