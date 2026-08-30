package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacySkillsProgramme;
import haj.com.service.lookup.LegacySkillsProgrammeService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacySkillsProgrammeConvertor")
public class LegacySkillsProgrammeConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacySkillsProgramme
 	 * @author TechFinium 
 	 * @see    LegacySkillsProgramme
 	 * @return LegacySkillsProgramme
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacySkillsProgrammeService()
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
	 * Convert LegacySkillsProgramme key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacySkillsProgramme)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacySkillsProgrammeId" value="#{xxxUI.LegacySkillsProgramme.xxxType}" converter="LegacySkillsProgrammeConvertor" style="width:95%">
         <f:selectItems value="#{LegacySkillsProgrammeUI.LegacySkillsProgrammeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacySkillsProgramme" for="LegacySkillsProgrammeID"/>
        <p:autoComplete id="LegacySkillsProgrammeID" value="#{LegacySkillsProgrammeUI.LegacySkillsProgramme.municipality}" completeMethod="#{LegacySkillsProgrammeUI.completeLegacySkillsProgramme}"
                            var="rv" itemLabel="#{rv.LegacySkillsProgrammeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacySkillsProgrammeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacySkillsProgramme" style="white-space: nowrap">#{rv.LegacySkillsProgrammeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
