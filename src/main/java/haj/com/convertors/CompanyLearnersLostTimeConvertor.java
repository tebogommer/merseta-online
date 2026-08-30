package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.CompanyLearnersLostTime;
import haj.com.service.CompanyLearnersLostTimeService;

@FacesConverter(value = "CompanyLearnersLostTimeConvertor")
public class CompanyLearnersLostTimeConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a CompanyLearnersLostTime
 	 * @author TechFinium 
 	 * @see    CompanyLearnersLostTime
 	 * @return CompanyLearnersLostTime
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new CompanyLearnersLostTimeService()
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
	 * Convert CompanyLearnersLostTime key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((CompanyLearnersLostTime)arg2).getId();
	}

/*
       <p:selectOneMenu id="CompanyLearnersLostTimeId" value="#{xxxUI.CompanyLearnersLostTime.xxxType}" converter="CompanyLearnersLostTimeConvertor" style="width:95%">
         <f:selectItems value="#{CompanyLearnersLostTimeUI.CompanyLearnersLostTimeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="CompanyLearnersLostTime" for="CompanyLearnersLostTimeID"/>
        <p:autoComplete id="CompanyLearnersLostTimeID" value="#{CompanyLearnersLostTimeUI.CompanyLearnersLostTime.municipality}" completeMethod="#{CompanyLearnersLostTimeUI.completeCompanyLearnersLostTime}"
                            var="rv" itemLabel="#{rv.CompanyLearnersLostTimeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="CompanyLearnersLostTimeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="CompanyLearnersLostTime" style="white-space: nowrap">#{rv.CompanyLearnersLostTimeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
