package haj.com.convertors;

import haj.com.entity.UserSkillsProgramme;
import haj.com.service.UserSkillsProgrammeService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "UserSkillsProgrammeConvertor")
public class UserSkillsProgrammeConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a UserSkillsProgramme
 	 * @author TechFinium 
 	 * @see    UserSkillsProgramme
 	 * @return UserSkillsProgramme
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new UserSkillsProgrammeService()
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
	 * Convert UserSkillsProgramme key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((UserSkillsProgramme)arg2).getId();
	}

/*
       <p:selectOneMenu id="UserSkillsProgrammeId" value="#{xxxUI.UserSkillsProgramme.xxxType}" converter="UserSkillsProgrammeConvertor" style="width:95%">
         <f:selectItems value="#{UserSkillsProgrammeUI.UserSkillsProgrammeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="UserSkillsProgramme" for="UserSkillsProgrammeID"/>
        <p:autoComplete id="UserSkillsProgrammeID" value="#{UserSkillsProgrammeUI.UserSkillsProgramme.municipality}" completeMethod="#{UserSkillsProgrammeUI.completeUserSkillsProgramme}"
                            var="rv" itemLabel="#{rv.UserSkillsProgrammeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="UserSkillsProgrammeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="UserSkillsProgramme" style="white-space: nowrap">#{rv.UserSkillsProgrammeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
