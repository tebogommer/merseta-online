package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.CompanyUserPosition;
import haj.com.service.lookup.CompanyUserPositionService;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyUserPositionConvertor.
 */
@FacesConverter(value = "CompanyUserPositionConvertor")
public class CompanyUserPositionConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a CompanyUserPosition.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return CompanyUserPosition
	 * @see    CompanyUserPosition
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new CompanyUserPositionService()
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
	 * Convert CompanyUserPosition key to String object.
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
		if (arg2 == null) {
			return "";
		}
		return ""+((CompanyUserPosition)arg2).getId();
	}

/*
       <p:selectOneMenu id="CompanyUserPositionId" value="#{xxxUI.CompanyUserPosition.xxxType}" converter="CompanyUserPositionConvertor" style="width:95%">
         <f:selectItems value="#{CompanyUserPositionUI.CompanyUserPositionList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="CompanyUserPosition" for="CompanyUserPositionID"/>
        <p:autoComplete id="CompanyUserPositionID" value="#{CompanyUserPositionUI.CompanyUserPosition.municipality}" completeMethod="#{CompanyUserPositionUI.completeCompanyUserPosition}"
                            var="rv" itemLabel="#{rv.CompanyUserPositionDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="CompanyUserPositionConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="CompanyUserPosition" style="white-space: nowrap">#{rv.CompanyUserPositionDescription}</p:column>
       </p:autoComplete>         
       
*/

}
