package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.formconfig.FormTypeSections;
import haj.com.service.FormTypeSectionsService;

@FacesConverter(value = "FormTypeSectionsConvertor")
public class FormTypeSectionsConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a FormTypeSections
 	 * @author TechFinium 
 	 * @see    FormTypeSections
 	 * @return FormTypeSections
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new FormTypeSectionsService()
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
	 * Convert FormTypeSections key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((FormTypeSections)arg2).getId();
	}

/*
       <p:selectOneMenu id="FormTypeSectionsId" value="#{xxxUI.FormTypeSections.xxxType}" converter="FormTypeSectionsConvertor" style="width:95%">
         <f:selectItems value="#{FormTypeSectionsUI.FormTypeSectionsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="FormTypeSections" for="FormTypeSectionsID"/>
        <p:autoComplete id="FormTypeSectionsID" value="#{FormTypeSectionsUI.FormTypeSections.municipality}" completeMethod="#{FormTypeSectionsUI.completeFormTypeSections}"
                            var="rv" itemLabel="#{rv.FormTypeSectionsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="FormTypeSectionsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="FormTypeSections" style="white-space: nowrap">#{rv.FormTypeSectionsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
