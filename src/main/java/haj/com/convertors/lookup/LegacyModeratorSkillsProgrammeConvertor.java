package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyModeratorSkillsProgramme;
import haj.com.service.lookup.LegacyModeratorSkillsProgrammeService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyModeratorSkillsProgrammeConvertor")
public class LegacyModeratorSkillsProgrammeConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyModeratorSkillsProgramme
 	 * @author TechFinium 
 	 * @see    LegacyModeratorSkillsProgramme
 	 * @return LegacyModeratorSkillsProgramme
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyModeratorSkillsProgrammeService()
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
	 * Convert LegacyModeratorSkillsProgramme key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyModeratorSkillsProgramme)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyModeratorSkillsProgrammeId" value="#{xxxUI.LegacyModeratorSkillsProgramme.xxxType}" converter="LegacyModeratorSkillsProgrammeConvertor" style="width:95%">
         <f:selectItems value="#{LegacyModeratorSkillsProgrammeUI.LegacyModeratorSkillsProgrammeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyModeratorSkillsProgramme" for="LegacyModeratorSkillsProgrammeID"/>
        <p:autoComplete id="LegacyModeratorSkillsProgrammeID" value="#{LegacyModeratorSkillsProgrammeUI.LegacyModeratorSkillsProgramme.municipality}" completeMethod="#{LegacyModeratorSkillsProgrammeUI.completeLegacyModeratorSkillsProgramme}"
                            var="rv" itemLabel="#{rv.LegacyModeratorSkillsProgrammeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyModeratorSkillsProgrammeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyModeratorSkillsProgramme" style="white-space: nowrap">#{rv.LegacyModeratorSkillsProgrammeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
