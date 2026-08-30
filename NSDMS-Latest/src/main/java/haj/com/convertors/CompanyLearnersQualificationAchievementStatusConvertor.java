package haj.com.convertors;

import haj.com.entity.CompanyLearnersQualificationAchievementStatus;
import haj.com.service.CompanyLearnersQualificationAchievementStatusService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "CompanyLearnersQualificationAchievementStatusConvertor")
public class CompanyLearnersQualificationAchievementStatusConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a CompanyLearnersQualificationAchievementStatus
 	 * @author TechFinium 
 	 * @see    CompanyLearnersQualificationAchievementStatus
 	 * @return CompanyLearnersQualificationAchievementStatus
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new CompanyLearnersQualificationAchievementStatusService()
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
	 * Convert CompanyLearnersQualificationAchievementStatus key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((CompanyLearnersQualificationAchievementStatus)arg2).getId();
	}

/*
       <p:selectOneMenu id="CompanyLearnersQualificationAchievementStatusId" value="#{xxxUI.CompanyLearnersQualificationAchievementStatus.xxxType}" converter="CompanyLearnersQualificationAchievementStatusConvertor" style="width:95%">
         <f:selectItems value="#{CompanyLearnersQualificationAchievementStatusUI.CompanyLearnersQualificationAchievementStatusList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="CompanyLearnersQualificationAchievementStatus" for="CompanyLearnersQualificationAchievementStatusID"/>
        <p:autoComplete id="CompanyLearnersQualificationAchievementStatusID" value="#{CompanyLearnersQualificationAchievementStatusUI.CompanyLearnersQualificationAchievementStatus.municipality}" completeMethod="#{CompanyLearnersQualificationAchievementStatusUI.completeCompanyLearnersQualificationAchievementStatus}"
                            var="rv" itemLabel="#{rv.CompanyLearnersQualificationAchievementStatusDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="CompanyLearnersQualificationAchievementStatusConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="CompanyLearnersQualificationAchievementStatus" style="white-space: nowrap">#{rv.CompanyLearnersQualificationAchievementStatusDescription}</p:column>
       </p:autoComplete>         
       
*/

}
