package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.SizeOfCompany;
import haj.com.service.lookup.SizeOfCompanyService;

@FacesConverter(value = "SizeOfCompanyConvertor")
public class SizeOfCompanyConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SizeOfCompany
 	 * @author TechFinium 
 	 * @see    SizeOfCompany
 	 * @return SizeOfCompany
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SizeOfCompanyService()
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
	 * Convert SizeOfCompany key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((SizeOfCompany)arg2).getId();
	}

/*
       <p:selectOneMenu id="SizeOfCompanyId" value="#{xxxUI.SizeOfCompany.xxxType}" converter="SizeOfCompanyConvertor" style="width:95%">
         <f:selectItems value="#{SizeOfCompanyUI.SizeOfCompanyList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SizeOfCompany" for="SizeOfCompanyID"/>
        <p:autoComplete id="SizeOfCompanyID" value="#{SizeOfCompanyUI.SizeOfCompany.municipality}" completeMethod="#{SizeOfCompanyUI.completeSizeOfCompany}"
                            var="rv" itemLabel="#{rv.SizeOfCompanyDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SizeOfCompanyConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SizeOfCompany" style="white-space: nowrap">#{rv.SizeOfCompanyDescription}</p:column>
       </p:autoComplete>         
       
*/

}
