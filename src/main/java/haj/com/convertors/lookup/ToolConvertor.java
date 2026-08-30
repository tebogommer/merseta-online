package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.Tool;
import haj.com.service.lookup.ToolService;

@FacesConverter(value = "ToolConvertor")
public class ToolConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a Tool
 	 * @author TechFinium 
 	 * @see    Tool
 	 * @return Tool
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new ToolService()
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
	 * Convert Tool key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((Tool)arg2).getId();
	}

/*
       <p:selectOneMenu id="ToolId" value="#{xxxUI.Tool.xxxType}" converter="ToolConvertor" style="width:95%">
         <f:selectItems value="#{ToolUI.ToolList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="Tool" for="ToolID"/>
        <p:autoComplete id="ToolID" value="#{ToolUI.Tool.municipality}" completeMethod="#{ToolUI.completeTool}"
                            var="rv" itemLabel="#{rv.ToolDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="ToolConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="Tool" style="white-space: nowrap">#{rv.ToolDescription}</p:column>
       </p:autoComplete>         
       
*/

}
