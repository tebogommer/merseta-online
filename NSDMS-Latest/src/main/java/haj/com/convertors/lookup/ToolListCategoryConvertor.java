package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.ToolListCategory;
import haj.com.service.lookup.ToolListCategoryService;

@FacesConverter(value = "ToolListCategoryConvertor")
public class ToolListCategoryConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a ToolListCategory
 	 * @author TechFinium 
 	 * @see    ToolListCategory
 	 * @return ToolListCategory
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new ToolListCategoryService()
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
	 * Convert ToolListCategory key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((ToolListCategory)arg2).getId();
	}

/*
       <p:selectOneMenu id="ToolListCategoryId" value="#{xxxUI.ToolListCategory.xxxType}" converter="ToolListCategoryConvertor" style="width:95%">
         <f:selectItems value="#{ToolListCategoryUI.ToolListCategoryList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="ToolListCategory" for="ToolListCategoryID"/>
        <p:autoComplete id="ToolListCategoryID" value="#{ToolListCategoryUI.ToolListCategory.municipality}" completeMethod="#{ToolListCategoryUI.completeToolListCategory}"
                            var="rv" itemLabel="#{rv.ToolListCategoryDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="ToolListCategoryConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="ToolListCategory" style="white-space: nowrap">#{rv.ToolListCategoryDescription}</p:column>
       </p:autoComplete>         
       
*/

}
