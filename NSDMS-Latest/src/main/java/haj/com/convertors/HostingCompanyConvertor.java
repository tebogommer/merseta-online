package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.HostingCompany;
import haj.com.service.HostingCompanyService;

// TODO: Auto-generated Javadoc
/**
 * The Class HostingCompanyConvertor.
 */
@FacesConverter(value = "HostingCompanyConvertor")
public class HostingCompanyConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a HostingCompany.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return HostingCompany
	 * @see    HostingCompany
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new HostingCompanyService()
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
	 * Convert HostingCompany key to String object.
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
		return ""+((HostingCompany)arg2).getId();
	}

/*
       <p:selectOneMenu id="HostingCompanyId" value="#{xxxUI.HostingCompany.xxxType}" converter="HostingCompanyConvertor" style="width:95%">
         <f:selectItems value="#{HostingCompanyUI.HostingCompanyList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="HostingCompany" for="HostingCompanyID"/>
        <p:autoComplete id="HostingCompanyID" value="#{HostingCompanyUI.HostingCompany.municipality}" completeMethod="#{HostingCompanyUI.completeHostingCompany}"
                            var="rv" itemLabel="#{rv.HostingCompanyDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="HostingCompanyConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="HostingCompany" style="white-space: nowrap">#{rv.HostingCompanyDescription}</p:column>
       </p:autoComplete>         
       
*/

}
