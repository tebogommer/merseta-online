package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.ToolList;
import haj.com.service.lookup.ToolListService;

@FacesConverter(value = "ToolListConvertor")
public class ToolListConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a ToolList
 	 * @author TechFinium 
 	 * @see    ToolList
 	 * @return ToolList
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new ToolListService()
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
	 * Convert ToolList key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((ToolList)arg2).getId();
	}

/*
       <p:selectOneMenu id="ToolListId" value="#{xxxUI.ToolList.xxxType}" converter="ToolListConvertor" style="width:95%">
         <f:selectItems value="#{ToolListUI.ToolListList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="ToolList" for="ToolListID"/>
        <p:autoComplete id="ToolListID" value="#{ToolListUI.ToolList.municipality}" completeMethod="#{ToolListUI.completeToolList}"
                            var="rv" itemLabel="#{rv.ToolListDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="ToolListConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="ToolList" style="white-space: nowrap">#{rv.ToolListDescription}</p:column>
       </p:autoComplete>         
       
*/

}
