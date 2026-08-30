package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.CompanyHostingCompany;
import haj.com.service.CompanyHostingCompanyService;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyHostingCompanyConvertor.
 */
@FacesConverter(value = "CompanyHostingCompanyConvertor")
public class CompanyHostingCompanyConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a CompanyHostingCompany.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return CompanyHostingCompany
	 * @see    CompanyHostingCompany
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new CompanyHostingCompanyService()
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
	 * Convert CompanyHostingCompany key to String object.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param arg2 the arg 2
	 * @return String
	 * @see    String
	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return ""+((CompanyHostingCompany)arg2).getId();
	}

/*
       <p:selectOneMenu id="CompanyHostingCompanyId" value="#{xxxUI.CompanyHostingCompany.xxxType}" converter="CompanyHostingCompanyConvertor" style="width:95%">
         <f:selectItems value="#{CompanyHostingCompanyUI.CompanyHostingCompanyList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="CompanyHostingCompany" for="CompanyHostingCompanyID"/>
        <p:autoComplete id="CompanyHostingCompanyID" value="#{CompanyHostingCompanyUI.CompanyHostingCompany.municipality}" completeMethod="#{CompanyHostingCompanyUI.completeCompanyHostingCompany}"
                            var="rv" itemLabel="#{rv.CompanyHostingCompanyDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="CompanyHostingCompanyConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="CompanyHostingCompany" style="white-space: nowrap">#{rv.CompanyHostingCompanyDescription}</p:column>
       </p:autoComplete>         
       
*/

}
