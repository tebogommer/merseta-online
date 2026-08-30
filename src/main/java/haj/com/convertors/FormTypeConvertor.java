package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.formconfig.FormType;
import haj.com.service.FormTypeService;

@FacesConverter(value = "FormTypeConvertor")
public class FormTypeConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a FormType
 	 * @author TechFinium 
 	 * @see    FormType
 	 * @return FormType
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new FormTypeService()
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
	 * Convert FormType key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((FormType)arg2).getId();
	}

/*
       <p:selectOneMenu id="FormTypeId" value="#{xxxUI.FormType.xxxType}" converter="FormTypeConvertor" style="width:95%">
         <f:selectItems value="#{FormTypeUI.FormTypeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="FormType" for="FormTypeID"/>
        <p:autoComplete id="FormTypeID" value="#{FormTypeUI.FormType.municipality}" completeMethod="#{FormTypeUI.completeFormType}"
                            var="rv" itemLabel="#{rv.FormTypeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="FormTypeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="FormType" style="white-space: nowrap">#{rv.FormTypeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
