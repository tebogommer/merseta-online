package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.CoursewareDistibution;
import haj.com.service.CoursewareDistibutionService;

@FacesConverter(value = "CoursewareDistibutionConvertor")
public class CoursewareDistibutionConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a CoursewareDistibution
 	 * @author TechFinium 
 	 * @see    CoursewareDistibution
 	 * @return CoursewareDistibution
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new CoursewareDistibutionService()
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
	 * Convert CoursewareDistibution key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((CoursewareDistibution)arg2).getId();
	}

/*
       <p:selectOneMenu id="CoursewareDistibutionId" value="#{xxxUI.CoursewareDistibution.xxxType}" converter="CoursewareDistibutionConvertor" style="width:95%">
         <f:selectItems value="#{CoursewareDistibutionUI.CoursewareDistibutionList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="CoursewareDistibution" for="CoursewareDistibutionID"/>
        <p:autoComplete id="CoursewareDistibutionID" value="#{CoursewareDistibutionUI.CoursewareDistibution.municipality}" completeMethod="#{CoursewareDistibutionUI.completeCoursewareDistibution}"
                            var="rv" itemLabel="#{rv.CoursewareDistibutionDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="CoursewareDistibutionConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="CoursewareDistibution" style="white-space: nowrap">#{rv.CoursewareDistibutionDescription}</p:column>
       </p:autoComplete>         
       
*/

}
