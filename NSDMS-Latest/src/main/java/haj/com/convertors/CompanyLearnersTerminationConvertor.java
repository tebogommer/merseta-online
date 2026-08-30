package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.CompanyLearnersTermination;
import haj.com.service.CompanyLearnersTerminationService;

@FacesConverter(value = "CompanyLearnersTerminationConvertor")
public class CompanyLearnersTerminationConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a CompanyLearnersTermination
 	 * @author TechFinium 
 	 * @see    CompanyLearnersTermination
 	 * @return CompanyLearnersTermination
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new CompanyLearnersTerminationService()
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
	 * Convert CompanyLearnersTermination key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((CompanyLearnersTermination)arg2).getId();
	}

/*
       <p:selectOneMenu id="CompanyLearnersTerminationId" value="#{xxxUI.CompanyLearnersTermination.xxxType}" converter="CompanyLearnersTerminationConvertor" style="width:95%">
         <f:selectItems value="#{CompanyLearnersTerminationUI.CompanyLearnersTerminationList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="CompanyLearnersTermination" for="CompanyLearnersTerminationID"/>
        <p:autoComplete id="CompanyLearnersTerminationID" value="#{CompanyLearnersTerminationUI.CompanyLearnersTermination.municipality}" completeMethod="#{CompanyLearnersTerminationUI.completeCompanyLearnersTermination}"
                            var="rv" itemLabel="#{rv.CompanyLearnersTerminationDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="CompanyLearnersTerminationConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="CompanyLearnersTermination" style="white-space: nowrap">#{rv.CompanyLearnersTerminationDescription}</p:column>
       </p:autoComplete>         
       
*/

}
