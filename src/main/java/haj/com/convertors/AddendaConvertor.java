package haj.com.convertors;

import haj.com.entity.Addenda;
import haj.com.service.AddendaService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "AddendaConvertor")
public class AddendaConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a Addenda
 	 * @author TechFinium 
 	 * @see    Addenda
 	 * @return Addenda
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new AddendaService()
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
	 * Convert Addenda key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((Addenda)arg2).getId();
	}

/*
       <p:selectOneMenu id="AddendaId" value="#{xxxUI.Addenda.xxxType}" converter="AddendaConvertor" style="width:95%">
         <f:selectItems value="#{AddendaUI.AddendaList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="Addenda" for="AddendaID"/>
        <p:autoComplete id="AddendaID" value="#{AddendaUI.Addenda.municipality}" completeMethod="#{AddendaUI.completeAddenda}"
                            var="rv" itemLabel="#{rv.AddendaDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="AddendaConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="Addenda" style="white-space: nowrap">#{rv.AddendaDescription}</p:column>
       </p:autoComplete>         
       
*/

}
