package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.ModulesCategory;
import haj.com.service.lookup.ModulesCategoryService;

@FacesConverter(value = "ModulesCategoryConvertor")
public class ModulesCategoryConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a ModulesCategory
 	 * @author TechFinium 
 	 * @see    ModulesCategory
 	 * @return ModulesCategory
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new ModulesCategoryService()
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
	 * Convert ModulesCategory key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((ModulesCategory)arg2).getId();
	}

/*
       <p:selectOneMenu id="ModulesCategoryId" value="#{xxxUI.ModulesCategory.xxxType}" converter="ModulesCategoryConvertor" style="width:95%">
         <f:selectItems value="#{ModulesCategoryUI.ModulesCategoryList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="ModulesCategory" for="ModulesCategoryID"/>
        <p:autoComplete id="ModulesCategoryID" value="#{ModulesCategoryUI.ModulesCategory.municipality}" completeMethod="#{ModulesCategoryUI.completeModulesCategory}"
                            var="rv" itemLabel="#{rv.ModulesCategoryDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="ModulesCategoryConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="ModulesCategory" style="white-space: nowrap">#{rv.ModulesCategoryDescription}</p:column>
       </p:autoComplete>         
       
*/

}
