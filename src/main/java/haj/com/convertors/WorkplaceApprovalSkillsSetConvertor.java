package haj.com.convertors;

import haj.com.entity.WorkplaceApprovalSkillsSet;
import haj.com.service.WorkplaceApprovalSkillsSetService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "WorkplaceApprovalSkillsSetConvertor")
public class WorkplaceApprovalSkillsSetConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a WorkplaceApprovalSkillsSet
 	 * @author TechFinium 
 	 * @see    WorkplaceApprovalSkillsSet
 	 * @return WorkplaceApprovalSkillsSet
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new WorkplaceApprovalSkillsSetService()
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
	 * Convert WorkplaceApprovalSkillsSet key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((WorkplaceApprovalSkillsSet)arg2).getId();
	}

/*
       <p:selectOneMenu id="WorkplaceApprovalSkillsSetId" value="#{xxxUI.WorkplaceApprovalSkillsSet.xxxType}" converter="WorkplaceApprovalSkillsSetConvertor" style="width:95%">
         <f:selectItems value="#{WorkplaceApprovalSkillsSetUI.WorkplaceApprovalSkillsSetList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="WorkplaceApprovalSkillsSet" for="WorkplaceApprovalSkillsSetID"/>
        <p:autoComplete id="WorkplaceApprovalSkillsSetID" value="#{WorkplaceApprovalSkillsSetUI.WorkplaceApprovalSkillsSet.municipality}" completeMethod="#{WorkplaceApprovalSkillsSetUI.completeWorkplaceApprovalSkillsSet}"
                            var="rv" itemLabel="#{rv.WorkplaceApprovalSkillsSetDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="WorkplaceApprovalSkillsSetConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="WorkplaceApprovalSkillsSet" style="white-space: nowrap">#{rv.WorkplaceApprovalSkillsSetDescription}</p:column>
       </p:autoComplete>         
       
*/

}
