package haj.com.convertors;

import haj.com.entity.CompanyLearnersAchievementStatusEISA;
import haj.com.service.CompanyLearnersAchievementStatusEISAService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "CompanyLearnersAchievementStatusEISAConvertor")
public class CompanyLearnersAchievementStatusEISAConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a CompanyLearnersAchievementStatusEISA
 	 * @author TechFinium 
 	 * @see    CompanyLearnersAchievementStatusEISA
 	 * @return CompanyLearnersAchievementStatusEISA
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new CompanyLearnersAchievementStatusEISAService()
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
	 * Convert CompanyLearnersAchievementStatusEISA key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((CompanyLearnersAchievementStatusEISA)arg2).getId();
	}

/*
       <p:selectOneMenu id="CompanyLearnersAchievementStatusEISAId" value="#{xxxUI.CompanyLearnersAchievementStatusEISA.xxxType}" converter="CompanyLearnersAchievementStatusEISAConvertor" style="width:95%">
         <f:selectItems value="#{CompanyLearnersAchievementStatusEISAUI.CompanyLearnersAchievementStatusEISAList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="CompanyLearnersAchievementStatusEISA" for="CompanyLearnersAchievementStatusEISAID"/>
        <p:autoComplete id="CompanyLearnersAchievementStatusEISAID" value="#{CompanyLearnersAchievementStatusEISAUI.CompanyLearnersAchievementStatusEISA.municipality}" completeMethod="#{CompanyLearnersAchievementStatusEISAUI.completeCompanyLearnersAchievementStatusEISA}"
                            var="rv" itemLabel="#{rv.CompanyLearnersAchievementStatusEISADescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="CompanyLearnersAchievementStatusEISAConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="CompanyLearnersAchievementStatusEISA" style="white-space: nowrap">#{rv.CompanyLearnersAchievementStatusEISADescription}</p:column>
       </p:autoComplete>         
       
*/

}
