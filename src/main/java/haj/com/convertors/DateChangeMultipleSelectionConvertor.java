package haj.com.convertors;

import haj.com.entity.DateChangeMultipleSelection;
import haj.com.service.DateChangeMultipleSelectionService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "DateChangeMultipleSelectionConvertor")
public class DateChangeMultipleSelectionConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a DateChangeMultipleSelection
 	 * @author TechFinium 
 	 * @see    DateChangeMultipleSelection
 	 * @return DateChangeMultipleSelection
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new DateChangeMultipleSelectionService()
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
	 * Convert DateChangeMultipleSelection key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((DateChangeMultipleSelection)arg2).getId();
	}

/*
       <p:selectOneMenu id="DateChangeMultipleSelectionId" value="#{xxxUI.DateChangeMultipleSelection.xxxType}" converter="DateChangeMultipleSelectionConvertor" style="width:95%">
         <f:selectItems value="#{DateChangeMultipleSelectionUI.DateChangeMultipleSelectionList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="DateChangeMultipleSelection" for="DateChangeMultipleSelectionID"/>
        <p:autoComplete id="DateChangeMultipleSelectionID" value="#{DateChangeMultipleSelectionUI.DateChangeMultipleSelection.municipality}" completeMethod="#{DateChangeMultipleSelectionUI.completeDateChangeMultipleSelection}"
                            var="rv" itemLabel="#{rv.DateChangeMultipleSelectionDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="DateChangeMultipleSelectionConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="DateChangeMultipleSelection" style="white-space: nowrap">#{rv.DateChangeMultipleSelectionDescription}</p:column>
       </p:autoComplete>         
       
*/

}
