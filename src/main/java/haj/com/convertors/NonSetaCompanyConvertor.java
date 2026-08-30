package haj.com.convertors;

import haj.com.entity.NonSetaCompany;
import haj.com.service.NonSetaCompanyService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "NonSetaCompanyConvertor")
public class NonSetaCompanyConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a NonSetaCompany
 	 * @author TechFinium 
 	 * @see    NonSetaCompany
 	 * @return NonSetaCompany
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new NonSetaCompanyService()
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
	 * Convert NonSetaCompany key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((NonSetaCompany)arg2).getId();
	}

/*
       <p:selectOneMenu id="NonSetaCompanyId" value="#{xxxUI.NonSetaCompany.xxxType}" converter="NonSetaCompanyConvertor" style="width:95%">
         <f:selectItems value="#{NonSetaCompanyUI.NonSetaCompanyList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="NonSetaCompany" for="NonSetaCompanyID"/>
        <p:autoComplete id="NonSetaCompanyID" value="#{NonSetaCompanyUI.NonSetaCompany.municipality}" completeMethod="#{NonSetaCompanyUI.completeNonSetaCompany}"
                            var="rv" itemLabel="#{rv.NonSetaCompanyDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="NonSetaCompanyConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="NonSetaCompany" style="white-space: nowrap">#{rv.NonSetaCompanyDescription}</p:column>
       </p:autoComplete>         
       
*/

}
