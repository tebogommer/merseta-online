package haj.com.convertors;

import haj.com.entity.CompanyLearnersDetailsChange;
import haj.com.service.CompanyLearnersDetailsChangeService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "CompanyLearnersDetailsChangeConvertor")
public class CompanyLearnersDetailsChangeConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a CompanyLearnersDetailsChange
 	 * @author TechFinium 
 	 * @see    CompanyLearnersDetailsChange
 	 * @return CompanyLearnersDetailsChange
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new CompanyLearnersDetailsChangeService()
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
	 * Convert CompanyLearnersDetailsChange key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((CompanyLearnersDetailsChange)arg2).getId();
	}

/*
       <p:selectOneMenu id="CompanyLearnersDetailsChangeId" value="#{xxxUI.CompanyLearnersDetailsChange.xxxType}" converter="CompanyLearnersDetailsChangeConvertor" style="width:95%">
         <f:selectItems value="#{CompanyLearnersDetailsChangeUI.CompanyLearnersDetailsChangeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="CompanyLearnersDetailsChange" for="CompanyLearnersDetailsChangeID"/>
        <p:autoComplete id="CompanyLearnersDetailsChangeID" value="#{CompanyLearnersDetailsChangeUI.CompanyLearnersDetailsChange.municipality}" completeMethod="#{CompanyLearnersDetailsChangeUI.completeCompanyLearnersDetailsChange}"
                            var="rv" itemLabel="#{rv.CompanyLearnersDetailsChangeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="CompanyLearnersDetailsChangeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="CompanyLearnersDetailsChange" style="white-space: nowrap">#{rv.CompanyLearnersDetailsChangeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
