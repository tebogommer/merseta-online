package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyEmployerWA2SkillsProgramme;
import haj.com.service.lookup.LegacyEmployerWA2SkillsProgrammeService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyEmployerWA2SkillsProgrammeConvertor")
public class LegacyEmployerWA2SkillsProgrammeConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyEmployerWA2SkillsProgramme
 	 * @author TechFinium 
 	 * @see    LegacyEmployerWA2SkillsProgramme
 	 * @return LegacyEmployerWA2SkillsProgramme
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyEmployerWA2SkillsProgrammeService()
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
	 * Convert LegacyEmployerWA2SkillsProgramme key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyEmployerWA2SkillsProgramme)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyEmployerWA2SkillsProgrammeId" value="#{xxxUI.LegacyEmployerWA2SkillsProgramme.xxxType}" converter="LegacyEmployerWA2SkillsProgrammeConvertor" style="width:95%">
         <f:selectItems value="#{LegacyEmployerWA2SkillsProgrammeUI.LegacyEmployerWA2SkillsProgrammeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyEmployerWA2SkillsProgramme" for="LegacyEmployerWA2SkillsProgrammeID"/>
        <p:autoComplete id="LegacyEmployerWA2SkillsProgrammeID" value="#{LegacyEmployerWA2SkillsProgrammeUI.LegacyEmployerWA2SkillsProgramme.municipality}" completeMethod="#{LegacyEmployerWA2SkillsProgrammeUI.completeLegacyEmployerWA2SkillsProgramme}"
                            var="rv" itemLabel="#{rv.LegacyEmployerWA2SkillsProgrammeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyEmployerWA2SkillsProgrammeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyEmployerWA2SkillsProgramme" style="white-space: nowrap">#{rv.LegacyEmployerWA2SkillsProgrammeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
