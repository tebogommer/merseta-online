package haj.com.convertors;

import haj.com.entity.ExceptionsTable;
import haj.com.service.ExceptionsTableService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "ExceptionsTableConvertor")
public class ExceptionsTableConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a ExceptionsTable
 	 * @author TechFinium 
 	 * @see    ExceptionsTable
 	 * @return ExceptionsTable
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new ExceptionsTableService()
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
	 * Convert ExceptionsTable key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((ExceptionsTable)arg2).getId();
	}

/*
       <p:selectOneMenu id="ExceptionsTableId" value="#{xxxUI.ExceptionsTable.xxxType}" converter="ExceptionsTableConvertor" style="width:95%">
         <f:selectItems value="#{ExceptionsTableUI.ExceptionsTableList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="ExceptionsTable" for="ExceptionsTableID"/>
        <p:autoComplete id="ExceptionsTableID" value="#{ExceptionsTableUI.ExceptionsTable.municipality}" completeMethod="#{ExceptionsTableUI.completeExceptionsTable}"
                            var="rv" itemLabel="#{rv.ExceptionsTableDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="ExceptionsTableConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="ExceptionsTable" style="white-space: nowrap">#{rv.ExceptionsTableDescription}</p:column>
       </p:autoComplete>         
       
*/

}
