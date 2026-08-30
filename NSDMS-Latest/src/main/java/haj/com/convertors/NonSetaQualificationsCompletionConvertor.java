package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.NonSetaQualificationsCompletion;
import haj.com.service.NonSetaQualificationsCompletionService;

@FacesConverter(value = "NonSetaQualificationsCompletionConvertor")
public class NonSetaQualificationsCompletionConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a NonSetaQualificationsCompletion
 	 * @author TechFinium 
 	 * @see    NonSetaQualificationsCompletion
 	 * @return NonSetaQualificationsCompletion
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new NonSetaQualificationsCompletionService()
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
	 * Convert NonSetaQualificationsCompletion key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((NonSetaQualificationsCompletion)arg2).getId();
	}

/*
       <p:selectOneMenu id="NonSetaQualificationsCompletionId" value="#{xxxUI.NonSetaQualificationsCompletion.xxxType}" converter="NonSetaQualificationsCompletionConvertor" style="width:95%">
         <f:selectItems value="#{NonSetaQualificationsCompletionUI.NonSetaQualificationsCompletionList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="NonSetaQualificationsCompletion" for="NonSetaQualificationsCompletionID"/>
        <p:autoComplete id="NonSetaQualificationsCompletionID" value="#{NonSetaQualificationsCompletionUI.NonSetaQualificationsCompletion.municipality}" completeMethod="#{NonSetaQualificationsCompletionUI.completeNonSetaQualificationsCompletion}"
                            var="rv" itemLabel="#{rv.NonSetaQualificationsCompletionDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="NonSetaQualificationsCompletionConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="NonSetaQualificationsCompletion" style="white-space: nowrap">#{rv.NonSetaQualificationsCompletionDescription}</p:column>
       </p:autoComplete>         
       
*/

}
