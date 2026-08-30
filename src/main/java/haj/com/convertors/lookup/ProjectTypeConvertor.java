package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.ProjectType;
import haj.com.entity.lookup.JobTitle;
import haj.com.service.lookup.ProjectTypeService;

// TODO: Auto-generated Javadoc
/**
 * The Class ProjectTagsConvertor.
 */
@FacesConverter(value = "ProjectTypeConvertor")
public class ProjectTypeConvertor implements Converter {
	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a ProjectTags.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return ProjectTags
	 * @see    ProjectType
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new ProjectTypeService()
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
	 * Convert ProjectTags key to String object.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param arg2 the arg 2
	 * @return String
	 * @see    String
	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {		
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((ProjectType)arg2).getId();
	}

/*
       <p:selectOneMenu id="ProjectTagsId" value="#{xxxUI.ProjectTags.xxxType}" converter="ProjectTagsConvertor" style="width:95%">
         <f:selectItems value="#{ProjectTagsUI.ProjectTagsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="ProjectTags" for="ProjectTagsID"/>
        <p:autoComplete id="ProjectTagsID" value="#{ProjectTagsUI.ProjectTags.municipality}" completeMethod="#{ProjectTagsUI.completeProjectTags}"
                            var="rv" itemLabel="#{rv.ProjectTagsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="ProjectTagsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="ProjectTags" style="white-space: nowrap">#{rv.ProjectTagsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
