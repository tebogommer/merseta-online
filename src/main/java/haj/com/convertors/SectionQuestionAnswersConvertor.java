package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.formconfig.FormTypeAnswers;
import haj.com.service.FormTypeAnswersService;

@FacesConverter(value = "SectionQuestionAnswersConvertor")
public class SectionQuestionAnswersConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SectionQuestionAnswers
 	 * @author TechFinium 
 	 * @see    FormTypeAnswers
 	 * @return SectionQuestionAnswers
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new FormTypeAnswersService()
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
	 * Convert SectionQuestionAnswers key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((FormTypeAnswers)arg2).getId();
	}

/*
       <p:selectOneMenu id="SectionQuestionAnswersId" value="#{xxxUI.SectionQuestionAnswers.xxxType}" converter="SectionQuestionAnswersConvertor" style="width:95%">
         <f:selectItems value="#{SectionQuestionAnswersUI.SectionQuestionAnswersList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SectionQuestionAnswers" for="SectionQuestionAnswersID"/>
        <p:autoComplete id="SectionQuestionAnswersID" value="#{SectionQuestionAnswersUI.SectionQuestionAnswers.municipality}" completeMethod="#{SectionQuestionAnswersUI.completeSectionQuestionAnswers}"
                            var="rv" itemLabel="#{rv.SectionQuestionAnswersDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SectionQuestionAnswersConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SectionQuestionAnswers" style="white-space: nowrap">#{rv.SectionQuestionAnswersDescription}</p:column>
       </p:autoComplete>         
       
*/

}
