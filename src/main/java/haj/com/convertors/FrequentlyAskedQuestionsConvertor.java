package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.FrequentlyAskedQuestions;
import haj.com.service.FrequentlyAskedQuestionsService;

@FacesConverter(value = "FrequentlyAskedQuestionsConvertor")
public class FrequentlyAskedQuestionsConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a FrequentlyAskedQuestions
 	 * @author TechFinium 
 	 * @see    FrequentlyAskedQuestions
 	 * @return FrequentlyAskedQuestions
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new FrequentlyAskedQuestionsService()
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
	 * Convert FrequentlyAskedQuestions key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((FrequentlyAskedQuestions)arg2).getId();
	}

/*
       <p:selectOneMenu id="FrequentlyAskedQuestionsId" value="#{xxxUI.FrequentlyAskedQuestions.xxxType}" converter="FrequentlyAskedQuestionsConvertor" style="width:95%">
         <f:selectItems value="#{FrequentlyAskedQuestionsUI.FrequentlyAskedQuestionsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="FrequentlyAskedQuestions" for="FrequentlyAskedQuestionsID"/>
        <p:autoComplete id="FrequentlyAskedQuestionsID" value="#{FrequentlyAskedQuestionsUI.FrequentlyAskedQuestions.municipality}" completeMethod="#{FrequentlyAskedQuestionsUI.completeFrequentlyAskedQuestions}"
                            var="rv" itemLabel="#{rv.FrequentlyAskedQuestionsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="FrequentlyAskedQuestionsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="FrequentlyAskedQuestions" style="white-space: nowrap">#{rv.FrequentlyAskedQuestionsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
