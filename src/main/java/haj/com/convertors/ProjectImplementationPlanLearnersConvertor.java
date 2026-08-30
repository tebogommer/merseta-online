package haj.com.convertors;

import haj.com.entity.ProjectImplementationPlanLearners;
import haj.com.service.ProjectImplementationPlanLearnersService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "ProjectImplementationPlanLearnersConvertor")
public class ProjectImplementationPlanLearnersConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a ProjectImplementationPlanLearners
 	 * @author TechFinium 
 	 * @see    ProjectImplementationPlanLearners
 	 * @return ProjectImplementationPlanLearners
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new ProjectImplementationPlanLearnersService()
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
	 * Convert ProjectImplementationPlanLearners key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((ProjectImplementationPlanLearners)arg2).getId();
	}

/*
       <p:selectOneMenu id="ProjectImplementationPlanLearnersId" value="#{xxxUI.ProjectImplementationPlanLearners.xxxType}" converter="ProjectImplementationPlanLearnersConvertor" style="width:95%">
         <f:selectItems value="#{ProjectImplementationPlanLearnersUI.ProjectImplementationPlanLearnersList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="ProjectImplementationPlanLearners" for="ProjectImplementationPlanLearnersID"/>
        <p:autoComplete id="ProjectImplementationPlanLearnersID" value="#{ProjectImplementationPlanLearnersUI.ProjectImplementationPlanLearners.municipality}" completeMethod="#{ProjectImplementationPlanLearnersUI.completeProjectImplementationPlanLearners}"
                            var="rv" itemLabel="#{rv.ProjectImplementationPlanLearnersDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="ProjectImplementationPlanLearnersConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="ProjectImplementationPlanLearners" style="white-space: nowrap">#{rv.ProjectImplementationPlanLearnersDescription}</p:column>
       </p:autoComplete>         
       
*/

}
