package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.Signoff;
import haj.com.service.SignoffService;

@FacesConverter(value = "SignoffConvertor")
public class SignoffConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a Signoff
 	 * @author TechFinium 
 	 * @see    Signoff
 	 * @return Signoff
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SignoffService()
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
	 * Convert Signoff key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((Signoff)arg2).getId();
	}

/*
       <p:selectOneMenu id="SignoffId" value="#{xxxUI.Signoff.xxxType}" converter="SignoffConvertor" style="width:95%">
         <f:selectItems value="#{SignoffUI.SignoffList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="Signoff" for="SignoffID"/>
        <p:autoComplete id="SignoffID" value="#{SignoffUI.Signoff.municipality}" completeMethod="#{SignoffUI.completeSignoff}"
                            var="rv" itemLabel="#{rv.SignoffDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SignoffConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="Signoff" style="white-space: nowrap">#{rv.SignoffDescription}</p:column>
       </p:autoComplete>         
       
*/

}
