package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.formconfig.FormSectionQuestions;
import haj.com.service.FormSectionQuestionsService;

@FacesConverter(value = "FormSectionQuestionsConvertor")
public class FormSectionQuestionsConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a FormSectionQuestions
 	 * @author TechFinium 
 	 * @see    FormSectionQuestions
 	 * @return FormSectionQuestions
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new FormSectionQuestionsService()
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
	 * Convert FormSectionQuestions key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((FormSectionQuestions)arg2).getId();
	}

/*
       <p:selectOneMenu id="FormSectionQuestionsId" value="#{xxxUI.FormSectionQuestions.xxxType}" converter="FormSectionQuestionsConvertor" style="width:95%">
         <f:selectItems value="#{FormSectionQuestionsUI.FormSectionQuestionsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="FormSectionQuestions" for="FormSectionQuestionsID"/>
        <p:autoComplete id="FormSectionQuestionsID" value="#{FormSectionQuestionsUI.FormSectionQuestions.municipality}" completeMethod="#{FormSectionQuestionsUI.completeFormSectionQuestions}"
                            var="rv" itemLabel="#{rv.FormSectionQuestionsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="FormSectionQuestionsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="FormSectionQuestions" style="white-space: nowrap">#{rv.FormSectionQuestionsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
