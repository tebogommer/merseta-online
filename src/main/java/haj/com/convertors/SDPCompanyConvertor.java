package haj.com.convertors;

import haj.com.entity.SDPCompany;
import haj.com.service.SDPCompanyService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "SDPCompanyConvertor")
public class SDPCompanyConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SDPCompany
 	 * @author TechFinium 
 	 * @see    SDPCompany
 	 * @return SDPCompany
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SDPCompanyService()
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
	 * Convert SDPCompany key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((SDPCompany)arg2).getId();
	}

/*
       <p:selectOneMenu id="SDPCompanyId" value="#{xxxUI.SDPCompany.xxxType}" converter="SDPCompanyConvertor" style="width:95%">
         <f:selectItems value="#{SDPCompanyUI.SDPCompanyList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SDPCompany" for="SDPCompanyID"/>
        <p:autoComplete id="SDPCompanyID" value="#{SDPCompanyUI.SDPCompany.municipality}" completeMethod="#{SDPCompanyUI.completeSDPCompany}"
                            var="rv" itemLabel="#{rv.SDPCompanyDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SDPCompanyConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SDPCompany" style="white-space: nowrap">#{rv.SDPCompanyDescription}</p:column>
       </p:autoComplete>         
       
*/

}
