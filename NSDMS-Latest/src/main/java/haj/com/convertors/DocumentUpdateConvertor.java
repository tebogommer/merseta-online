package haj.com.convertors;

import haj.com.entity.DocumentUpdate;
import haj.com.service.DocumentUpdateService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "DocumentUpdateConvertor")
public class DocumentUpdateConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a DocumentUpdate
 	 * @author TechFinium 
 	 * @see    DocumentUpdate
 	 * @return DocumentUpdate
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new DocumentUpdateService()
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
	 * Convert DocumentUpdate key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((DocumentUpdate)arg2).getId();
	}

/*
       <p:selectOneMenu id="DocumentUpdateId" value="#{xxxUI.DocumentUpdate.xxxType}" converter="DocumentUpdateConvertor" style="width:95%">
         <f:selectItems value="#{DocumentUpdateUI.DocumentUpdateList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="DocumentUpdate" for="DocumentUpdateID"/>
        <p:autoComplete id="DocumentUpdateID" value="#{DocumentUpdateUI.DocumentUpdate.municipality}" completeMethod="#{DocumentUpdateUI.completeDocumentUpdate}"
                            var="rv" itemLabel="#{rv.DocumentUpdateDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="DocumentUpdateConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="DocumentUpdate" style="white-space: nowrap">#{rv.DocumentUpdateDescription}</p:column>
       </p:autoComplete>         
       
*/

}
