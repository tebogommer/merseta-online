package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.CompanyQualifications;
import haj.com.service.CompanyQualificationsService;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyQualificationsConvertor.
 */
@FacesConverter(value = "CompanyQualificationsConvertor")
public class CompanyQualificationsConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a CompanyQualifications.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return CompanyQualifications
	 * @see    CompanyQualifications
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new CompanyQualificationsService()
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
	 * Convert CompanyQualifications key to String object.
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
		return ""+((CompanyQualifications)arg2).getId();
	}

/*
       <p:selectOneMenu id="CompanyQualificationsId" value="#{xxxUI.CompanyQualifications.xxxType}" converter="CompanyQualificationsConvertor" style="width:95%">
         <f:selectItems value="#{CompanyQualificationsUI.CompanyQualificationsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="CompanyQualifications" for="CompanyQualificationsID"/>
        <p:autoComplete id="CompanyQualificationsID" value="#{CompanyQualificationsUI.CompanyQualifications.municipality}" completeMethod="#{CompanyQualificationsUI.completeCompanyQualifications}"
                            var="rv" itemLabel="#{rv.CompanyQualificationsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="CompanyQualificationsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="CompanyQualifications" style="white-space: nowrap">#{rv.CompanyQualificationsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
