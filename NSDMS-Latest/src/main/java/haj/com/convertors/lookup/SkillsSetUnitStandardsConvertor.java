package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.SkillsSetUnitStandards;
import haj.com.service.lookup.SkillsSetUnitStandardsService;

@FacesConverter(value = "SkillsSetUnitStandardsConvertor")
public class SkillsSetUnitStandardsConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SkillsSetUnitStandards
 	 * @author TechFinium 
 	 * @see    SkillsSetUnitStandards
 	 * @return SkillsSetUnitStandards
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SkillsSetUnitStandardsService()
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
	 * Convert SkillsSetUnitStandards key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((SkillsSetUnitStandards)arg2).getId();
	}

/*
       <p:selectOneMenu id="SkillsSetUnitStandardsId" value="#{xxxUI.SkillsSetUnitStandards.xxxType}" converter="SkillsSetUnitStandardsConvertor" style="width:95%">
         <f:selectItems value="#{SkillsSetUnitStandardsUI.SkillsSetUnitStandardsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SkillsSetUnitStandards" for="SkillsSetUnitStandardsID"/>
        <p:autoComplete id="SkillsSetUnitStandardsID" value="#{SkillsSetUnitStandardsUI.SkillsSetUnitStandards.municipality}" completeMethod="#{SkillsSetUnitStandardsUI.completeSkillsSetUnitStandards}"
                            var="rv" itemLabel="#{rv.SkillsSetUnitStandardsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SkillsSetUnitStandardsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SkillsSetUnitStandards" style="white-space: nowrap">#{rv.SkillsSetUnitStandardsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
