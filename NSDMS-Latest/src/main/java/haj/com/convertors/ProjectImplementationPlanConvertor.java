package haj.com.convertors;

import haj.com.entity.ProjectImplementationPlan;
import haj.com.service.ProjectImplementationPlanService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "ProjectImplementationPlanConvertor")
public class ProjectImplementationPlanConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a ProjectImplementationPlan
 	 * @author TechFinium 
 	 * @see    ProjectImplementationPlan
 	 * @return ProjectImplementationPlan
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new ProjectImplementationPlanService()
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
	 * Convert ProjectImplementationPlan key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((ProjectImplementationPlan)arg2).getId();
	}

/*
       <p:selectOneMenu id="ProjectImplementationPlanId" value="#{xxxUI.ProjectImplementationPlan.xxxType}" converter="ProjectImplementationPlanConvertor" style="width:95%">
         <f:selectItems value="#{ProjectImplementationPlanUI.ProjectImplementationPlanList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="ProjectImplementationPlan" for="ProjectImplementationPlanID"/>
        <p:autoComplete id="ProjectImplementationPlanID" value="#{ProjectImplementationPlanUI.ProjectImplementationPlan.municipality}" completeMethod="#{ProjectImplementationPlanUI.completeProjectImplementationPlan}"
                            var="rv" itemLabel="#{rv.ProjectImplementationPlanDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="ProjectImplementationPlanConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="ProjectImplementationPlan" style="white-space: nowrap">#{rv.ProjectImplementationPlanDescription}</p:column>
       </p:autoComplete>         
       
*/

}
