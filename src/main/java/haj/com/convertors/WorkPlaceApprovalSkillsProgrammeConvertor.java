package haj.com.convertors;

import haj.com.entity.WorkPlaceApprovalSkillsProgramme;
import haj.com.service.WorkPlaceApprovalSkillsProgrammeService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "WorkPlaceApprovalSkillsProgrammeConvertor")
public class WorkPlaceApprovalSkillsProgrammeConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a WorkPlaceApprovalSkillsProgramme
 	 * @author TechFinium 
 	 * @see    WorkPlaceApprovalSkillsProgramme
 	 * @return WorkPlaceApprovalSkillsProgramme
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new WorkPlaceApprovalSkillsProgrammeService()
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
	 * Convert WorkPlaceApprovalSkillsProgramme key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((WorkPlaceApprovalSkillsProgramme)arg2).getId();
	}

/*
       <p:selectOneMenu id="WorkPlaceApprovalSkillsProgrammeId" value="#{xxxUI.WorkPlaceApprovalSkillsProgramme.xxxType}" converter="WorkPlaceApprovalSkillsProgrammeConvertor" style="width:95%">
         <f:selectItems value="#{WorkPlaceApprovalSkillsProgrammeUI.WorkPlaceApprovalSkillsProgrammeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="WorkPlaceApprovalSkillsProgramme" for="WorkPlaceApprovalSkillsProgrammeID"/>
        <p:autoComplete id="WorkPlaceApprovalSkillsProgrammeID" value="#{WorkPlaceApprovalSkillsProgrammeUI.WorkPlaceApprovalSkillsProgramme.municipality}" completeMethod="#{WorkPlaceApprovalSkillsProgrammeUI.completeWorkPlaceApprovalSkillsProgramme}"
                            var="rv" itemLabel="#{rv.WorkPlaceApprovalSkillsProgrammeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="WorkPlaceApprovalSkillsProgrammeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="WorkPlaceApprovalSkillsProgramme" style="white-space: nowrap">#{rv.WorkPlaceApprovalSkillsProgrammeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
