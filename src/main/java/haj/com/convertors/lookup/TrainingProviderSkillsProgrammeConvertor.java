package haj.com.convertors.lookup;

import haj.com.entity.TrainingProviderSkillsProgramme;
import haj.com.service.TrainingProviderSkillsProgrammeService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "TrainingProviderSkillsProgrammeConvertor")
public class TrainingProviderSkillsProgrammeConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a TrainingProviderSkillsProgramme
 	 * @author TechFinium 
 	 * @see    TrainingProviderSkillsProgramme
 	 * @return TrainingProviderSkillsProgramme
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new TrainingProviderSkillsProgrammeService()
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
	 * Convert TrainingProviderSkillsProgramme key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((TrainingProviderSkillsProgramme)arg2).getId();
	}

/*
       <p:selectOneMenu id="TrainingProviderSkillsProgrammeId" value="#{xxxUI.TrainingProviderSkillsProgramme.xxxType}" converter="TrainingProviderSkillsProgrammeConvertor" style="width:95%">
         <f:selectItems value="#{TrainingProviderSkillsProgrammeUI.TrainingProviderSkillsProgrammeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="TrainingProviderSkillsProgramme" for="TrainingProviderSkillsProgrammeID"/>
        <p:autoComplete id="TrainingProviderSkillsProgrammeID" value="#{TrainingProviderSkillsProgrammeUI.TrainingProviderSkillsProgramme.municipality}" completeMethod="#{TrainingProviderSkillsProgrammeUI.completeTrainingProviderSkillsProgramme}"
                            var="rv" itemLabel="#{rv.TrainingProviderSkillsProgrammeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="TrainingProviderSkillsProgrammeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="TrainingProviderSkillsProgramme" style="white-space: nowrap">#{rv.TrainingProviderSkillsProgrammeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
