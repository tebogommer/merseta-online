package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.SkillsIdentification;
import haj.com.service.lookup.SkillsIdentificationService;

@FacesConverter(value = "SkillsIdentificationConvertor")
public class SkillsIdentificationConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SkillsIdentification
 	 * @author TechFinium 
 	 * @see    SkillsIdentification
 	 * @return SkillsIdentification
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SkillsIdentificationService()
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
	 * Convert SkillsIdentification key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((SkillsIdentification)arg2).getId();
	}

/*
       <p:selectOneMenu id="SkillsIdentificationId" value="#{xxxUI.SkillsIdentification.xxxType}" converter="SkillsIdentificationConvertor" style="width:95%">
         <f:selectItems value="#{SkillsIdentificationUI.SkillsIdentificationList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SkillsIdentification" for="SkillsIdentificationID"/>
        <p:autoComplete id="SkillsIdentificationID" value="#{SkillsIdentificationUI.SkillsIdentification.municipality}" completeMethod="#{SkillsIdentificationUI.completeSkillsIdentification}"
                            var="rv" itemLabel="#{rv.SkillsIdentificationDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SkillsIdentificationConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SkillsIdentification" style="white-space: nowrap">#{rv.SkillsIdentificationDescription}</p:column>
       </p:autoComplete>         
       
*/

}
