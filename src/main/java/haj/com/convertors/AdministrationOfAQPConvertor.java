package haj.com.convertors;

import haj.com.entity.AdministrationOfAQP;
import haj.com.service.AdministrationOfAQPService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "AdministrationOfAQPConvertor")
public class AdministrationOfAQPConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a AdministrationOfAQP
 	 * @author TechFinium 
 	 * @see    AdministrationOfAQP
 	 * @return AdministrationOfAQP
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new AdministrationOfAQPService()
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
	 * Convert AdministrationOfAQP key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((AdministrationOfAQP)arg2).getId();
	}

/*
       <p:selectOneMenu id="AdministrationOfAQPId" value="#{xxxUI.AdministrationOfAQP.xxxType}" converter="AdministrationOfAQPConvertor" style="width:95%">
         <f:selectItems value="#{AdministrationOfAQPUI.AdministrationOfAQPList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="AdministrationOfAQP" for="AdministrationOfAQPID"/>
        <p:autoComplete id="AdministrationOfAQPID" value="#{AdministrationOfAQPUI.AdministrationOfAQP.municipality}" completeMethod="#{AdministrationOfAQPUI.completeAdministrationOfAQP}"
                            var="rv" itemLabel="#{rv.AdministrationOfAQPDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="AdministrationOfAQPConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="AdministrationOfAQP" style="white-space: nowrap">#{rv.AdministrationOfAQPDescription}</p:column>
       </p:autoComplete>         
       
*/

}
