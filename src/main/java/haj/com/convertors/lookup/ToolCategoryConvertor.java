package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.ToolCategory;
import haj.com.service.lookup.ToolCategoryService;

@FacesConverter(value = "ToolCategoryConvertor")
public class ToolCategoryConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a ToolCategory
 	 * @author TechFinium 
 	 * @see    ToolCategory
 	 * @return ToolCategory
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new ToolCategoryService()
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
	 * Convert ToolCategory key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((ToolCategory)arg2).getId();
	}

/*
       <p:selectOneMenu id="ToolCategoryId" value="#{xxxUI.ToolCategory.xxxType}" converter="ToolCategoryConvertor" style="width:95%">
         <f:selectItems value="#{ToolCategoryUI.ToolCategoryList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="ToolCategory" for="ToolCategoryID"/>
        <p:autoComplete id="ToolCategoryID" value="#{ToolCategoryUI.ToolCategory.municipality}" completeMethod="#{ToolCategoryUI.completeToolCategory}"
                            var="rv" itemLabel="#{rv.ToolCategoryDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="ToolCategoryConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="ToolCategory" style="white-space: nowrap">#{rv.ToolCategoryDescription}</p:column>
       </p:autoComplete>         
       
*/

}
