package haj.com.convertors;

import haj.com.entity.CompanyLearnersProgress;
import haj.com.service.CompanyLearnersProgressService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "CompanyLearnersProgressConvertor")
public class CompanyLearnersProgressConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a CompanyLearnersProgress
 	 * @author TechFinium 
 	 * @see    CompanyLearnersProgress
 	 * @return CompanyLearnersProgress
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new CompanyLearnersProgressService()
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
	 * Convert CompanyLearnersProgress key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((CompanyLearnersProgress)arg2).getId();
	}

/*
       <p:selectOneMenu id="CompanyLearnersProgressId" value="#{xxxUI.CompanyLearnersProgress.xxxType}" converter="CompanyLearnersProgressConvertor" style="width:95%">
         <f:selectItems value="#{CompanyLearnersProgressUI.CompanyLearnersProgressList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="CompanyLearnersProgress" for="CompanyLearnersProgressID"/>
        <p:autoComplete id="CompanyLearnersProgressID" value="#{CompanyLearnersProgressUI.CompanyLearnersProgress.municipality}" completeMethod="#{CompanyLearnersProgressUI.completeCompanyLearnersProgress}"
                            var="rv" itemLabel="#{rv.CompanyLearnersProgressDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="CompanyLearnersProgressConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="CompanyLearnersProgress" style="white-space: nowrap">#{rv.CompanyLearnersProgressDescription}</p:column>
       </p:autoComplete>         
       
*/

}
