package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.CompanyUnitStandard;
import haj.com.service.CompanyUnitStandardService;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyUnitStandardConvertor.
 */
@FacesConverter(value = "CompanyUnitStandardConvertor")
public class CompanyUnitStandardConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a CompanyUnitStandard.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return CompanyUnitStandard
	 * @see    CompanyUnitStandard
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new CompanyUnitStandardService()
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
	 * Convert CompanyUnitStandard key to String object.
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
		return ""+((CompanyUnitStandard)arg2).getId();
	}

/*
       <p:selectOneMenu id="CompanyUnitStandardId" value="#{xxxUI.CompanyUnitStandard.xxxType}" converter="CompanyUnitStandardConvertor" style="width:95%">
         <f:selectItems value="#{CompanyUnitStandardUI.CompanyUnitStandardList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="CompanyUnitStandard" for="CompanyUnitStandardID"/>
        <p:autoComplete id="CompanyUnitStandardID" value="#{CompanyUnitStandardUI.CompanyUnitStandard.municipality}" completeMethod="#{CompanyUnitStandardUI.completeCompanyUnitStandard}"
                            var="rv" itemLabel="#{rv.CompanyUnitStandardDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="CompanyUnitStandardConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="CompanyUnitStandard" style="white-space: nowrap">#{rv.CompanyUnitStandardDescription}</p:column>
       </p:autoComplete>         
       
*/

}
