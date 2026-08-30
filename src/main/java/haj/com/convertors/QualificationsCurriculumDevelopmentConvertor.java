package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.QualificationsCurriculumDevelopment;
import haj.com.service.QualificationsCurriculumDevelopmentService;

@FacesConverter(value = "QualificationsCurriculumDevelopmentConvertor")
public class QualificationsCurriculumDevelopmentConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a QualificationsCurriculumDevelopment
 	 * @author TechFinium 
 	 * @see    QualificationsCurriculumDevelopment
 	 * @return QualificationsCurriculumDevelopment
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new QualificationsCurriculumDevelopmentService()
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
	 * Convert QualificationsCurriculumDevelopment key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((QualificationsCurriculumDevelopment)arg2).getId();
	}

/*
       <p:selectOneMenu id="QualificationsCurriculumDevelopmentId" value="#{xxxUI.QualificationsCurriculumDevelopment.xxxType}" converter="QualificationsCurriculumDevelopmentConvertor" style="width:95%">
         <f:selectItems value="#{QualificationsCurriculumDevelopmentUI.QualificationsCurriculumDevelopmentList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="QualificationsCurriculumDevelopment" for="QualificationsCurriculumDevelopmentID"/>
        <p:autoComplete id="QualificationsCurriculumDevelopmentID" value="#{QualificationsCurriculumDevelopmentUI.QualificationsCurriculumDevelopment.municipality}" completeMethod="#{QualificationsCurriculumDevelopmentUI.completeQualificationsCurriculumDevelopment}"
                            var="rv" itemLabel="#{rv.QualificationsCurriculumDevelopmentDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="QualificationsCurriculumDevelopmentConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="QualificationsCurriculumDevelopment" style="white-space: nowrap">#{rv.QualificationsCurriculumDevelopmentDescription}</p:column>
       </p:autoComplete>         
       
*/

}
