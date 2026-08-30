package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.SkillsRegistrationUnitStandards;
import haj.com.service.SkillsRegistrationUnitStandardsService;

@FacesConverter(value = "SkillsRegistrationUnitStandardsConvertor")
public class SkillsRegistrationUnitStandardsConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SkillsRegistrationUnitStandards
 	 * @author TechFinium 
 	 * @see    SkillsRegistrationUnitStandards
 	 * @return SkillsRegistrationUnitStandards
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SkillsRegistrationUnitStandardsService()
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
	 * Convert SkillsRegistrationUnitStandards key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((SkillsRegistrationUnitStandards)arg2).getId();
	}

/*
       <p:selectOneMenu id="SkillsRegistrationUnitStandardsId" value="#{xxxUI.SkillsRegistrationUnitStandards.xxxType}" converter="SkillsRegistrationUnitStandardsConvertor" style="width:95%">
         <f:selectItems value="#{SkillsRegistrationUnitStandardsUI.SkillsRegistrationUnitStandardsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SkillsRegistrationUnitStandards" for="SkillsRegistrationUnitStandardsID"/>
        <p:autoComplete id="SkillsRegistrationUnitStandardsID" value="#{SkillsRegistrationUnitStandardsUI.SkillsRegistrationUnitStandards.municipality}" completeMethod="#{SkillsRegistrationUnitStandardsUI.completeSkillsRegistrationUnitStandards}"
                            var="rv" itemLabel="#{rv.SkillsRegistrationUnitStandardsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SkillsRegistrationUnitStandardsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SkillsRegistrationUnitStandards" style="white-space: nowrap">#{rv.SkillsRegistrationUnitStandardsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
