package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.SDFCompany;
import haj.com.service.SDFCompanyService;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanySDFConvertor.
 */
@FacesConverter(value = "CompanySDFConvertor")
public class CompanySDFConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a CompanySDF.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return CompanySDF
	 * @see    SDFCompany
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SDFCompanyService()
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
	 * Convert CompanySDF key to String object.
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
		return ""+((SDFCompany)arg2).getId();
	}

/*
       <p:selectOneMenu id="CompanySDFId" value="#{xxxUI.CompanySDF.xxxType}" converter="CompanySDFConvertor" style="width:95%">
         <f:selectItems value="#{CompanySDFUI.CompanySDFList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="CompanySDF" for="CompanySDFID"/>
        <p:autoComplete id="CompanySDFID" value="#{CompanySDFUI.CompanySDF.municipality}" completeMethod="#{CompanySDFUI.completeCompanySDF}"
                            var="rv" itemLabel="#{rv.CompanySDFDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="CompanySDFConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="CompanySDF" style="white-space: nowrap">#{rv.CompanySDFDescription}</p:column>
       </p:autoComplete>         
       
*/

}
