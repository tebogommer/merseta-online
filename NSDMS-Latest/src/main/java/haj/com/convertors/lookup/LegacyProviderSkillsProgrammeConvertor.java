package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyProviderSkillsProgramme;
import haj.com.service.lookup.LegacyProviderSkillsProgrammeService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyProviderSkillsProgrammeConvertor")
public class LegacyProviderSkillsProgrammeConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyProviderSkillsProgramme
 	 * @author TechFinium 
 	 * @see    LegacyProviderSkillsProgramme
 	 * @return LegacyProviderSkillsProgramme
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyProviderSkillsProgrammeService()
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
	 * Convert LegacyProviderSkillsProgramme key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyProviderSkillsProgramme)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyProviderSkillsProgrammeId" value="#{xxxUI.LegacyProviderSkillsProgramme.xxxType}" converter="LegacyProviderSkillsProgrammeConvertor" style="width:95%">
         <f:selectItems value="#{LegacyProviderSkillsProgrammeUI.LegacyProviderSkillsProgrammeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyProviderSkillsProgramme" for="LegacyProviderSkillsProgrammeID"/>
        <p:autoComplete id="LegacyProviderSkillsProgrammeID" value="#{LegacyProviderSkillsProgrammeUI.LegacyProviderSkillsProgramme.municipality}" completeMethod="#{LegacyProviderSkillsProgrammeUI.completeLegacyProviderSkillsProgramme}"
                            var="rv" itemLabel="#{rv.LegacyProviderSkillsProgrammeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyProviderSkillsProgrammeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyProviderSkillsProgramme" style="white-space: nowrap">#{rv.LegacyProviderSkillsProgrammeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
