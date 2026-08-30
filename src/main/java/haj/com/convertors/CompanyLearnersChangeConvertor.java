package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.CompanyLearnersChange;
import haj.com.service.CompanyLearnersChangeService;

@FacesConverter(value = "CompanyLearnersChangeConvertor")
public class CompanyLearnersChangeConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a CompanyLearnersChange
 	 * @author TechFinium 
 	 * @see    CompanyLearnersChange
 	 * @return CompanyLearnersChange
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new CompanyLearnersChangeService()
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
	 * Convert CompanyLearnersChange key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((CompanyLearnersChange)arg2).getId();
	}

/*
       <p:selectOneMenu id="CompanyLearnersChangeId" value="#{xxxUI.CompanyLearnersChange.xxxType}" converter="CompanyLearnersChangeConvertor" style="width:95%">
         <f:selectItems value="#{CompanyLearnersChangeUI.CompanyLearnersChangeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="CompanyLearnersChange" for="CompanyLearnersChangeID"/>
        <p:autoComplete id="CompanyLearnersChangeID" value="#{CompanyLearnersChangeUI.CompanyLearnersChange.municipality}" completeMethod="#{CompanyLearnersChangeUI.completeCompanyLearnersChange}"
                            var="rv" itemLabel="#{rv.CompanyLearnersChangeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="CompanyLearnersChangeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="CompanyLearnersChange" style="white-space: nowrap">#{rv.CompanyLearnersChangeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
