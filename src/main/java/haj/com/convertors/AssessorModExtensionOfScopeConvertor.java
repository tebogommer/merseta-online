package haj.com.convertors;

import haj.com.entity.AssessorModExtensionOfScope;
import haj.com.service.AssessorModExtensionOfScopeService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "AssessorModExtensionOfScopeConvertor")
public class AssessorModExtensionOfScopeConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a AssessorModExtensionOfScope
 	 * @author TechFinium 
 	 * @see    AssessorModExtensionOfScope
 	 * @return AssessorModExtensionOfScope
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new AssessorModExtensionOfScopeService()
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
	 * Convert AssessorModExtensionOfScope key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((AssessorModExtensionOfScope)arg2).getId();
	}

/*
       <p:selectOneMenu id="AssessorModExtensionOfScopeId" value="#{xxxUI.AssessorModExtensionOfScope.xxxType}" converter="AssessorModExtensionOfScopeConvertor" style="width:95%">
         <f:selectItems value="#{AssessorModExtensionOfScopeUI.AssessorModExtensionOfScopeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="AssessorModExtensionOfScope" for="AssessorModExtensionOfScopeID"/>
        <p:autoComplete id="AssessorModExtensionOfScopeID" value="#{AssessorModExtensionOfScopeUI.AssessorModExtensionOfScope.municipality}" completeMethod="#{AssessorModExtensionOfScopeUI.completeAssessorModExtensionOfScope}"
                            var="rv" itemLabel="#{rv.AssessorModExtensionOfScopeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="AssessorModExtensionOfScopeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="AssessorModExtensionOfScope" style="white-space: nowrap">#{rv.AssessorModExtensionOfScopeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
