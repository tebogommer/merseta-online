package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyDTTCSkillsProgramme;
import haj.com.service.lookup.LegacyDTTCSkillsProgrammeService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyDTTCSkillsProgrammeConvertor")
public class LegacyDTTCSkillsProgrammeConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyDTTCSkillsProgramme
 	 * @author TechFinium 
 	 * @see    LegacyDTTCSkillsProgramme
 	 * @return LegacyDTTCSkillsProgramme
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyDTTCSkillsProgrammeService()
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
	 * Convert LegacyDTTCSkillsProgramme key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyDTTCSkillsProgramme)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyDTTCSkillsProgrammeId" value="#{xxxUI.LegacyDTTCSkillsProgramme.xxxType}" converter="LegacyDTTCSkillsProgrammeConvertor" style="width:95%">
         <f:selectItems value="#{LegacyDTTCSkillsProgrammeUI.LegacyDTTCSkillsProgrammeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyDTTCSkillsProgramme" for="LegacyDTTCSkillsProgrammeID"/>
        <p:autoComplete id="LegacyDTTCSkillsProgrammeID" value="#{LegacyDTTCSkillsProgrammeUI.LegacyDTTCSkillsProgramme.municipality}" completeMethod="#{LegacyDTTCSkillsProgrammeUI.completeLegacyDTTCSkillsProgramme}"
                            var="rv" itemLabel="#{rv.LegacyDTTCSkillsProgrammeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyDTTCSkillsProgrammeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyDTTCSkillsProgramme" style="white-space: nowrap">#{rv.LegacyDTTCSkillsProgrammeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
