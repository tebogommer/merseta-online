package haj.com.convertors;

import haj.com.entity.CompletionLetter;
import haj.com.service.CompletionLetterService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "CompletionLetterConvertor")
public class CompletionLetterConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a CompletionLetter
 	 * @author TechFinium 
 	 * @see    CompletionLetter
 	 * @return CompletionLetter
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new CompletionLetterService()
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
	 * Convert CompletionLetter key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((CompletionLetter)arg2).getId();
	}

/*
       <p:selectOneMenu id="CompletionLetterId" value="#{xxxUI.CompletionLetter.xxxType}" converter="CompletionLetterConvertor" style="width:95%">
         <f:selectItems value="#{CompletionLetterUI.CompletionLetterList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="CompletionLetter" for="CompletionLetterID"/>
        <p:autoComplete id="CompletionLetterID" value="#{CompletionLetterUI.CompletionLetter.municipality}" completeMethod="#{CompletionLetterUI.completeCompletionLetter}"
                            var="rv" itemLabel="#{rv.CompletionLetterDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="CompletionLetterConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="CompletionLetter" style="white-space: nowrap">#{rv.CompletionLetterDescription}</p:column>
       </p:autoComplete>         
       
*/

}
