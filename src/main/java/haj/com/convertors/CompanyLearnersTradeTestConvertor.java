package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.CompanyLearnersTradeTest;
import haj.com.service.CompanyLearnersTradeTestService;

@FacesConverter(value = "CompanyLearnersTradeTestConvertor")
public class CompanyLearnersTradeTestConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a CompanyLearnersTradeTest
 	 * @author TechFinium 
 	 * @see    CompanyLearnersTradeTest
 	 * @return CompanyLearnersTradeTest
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new CompanyLearnersTradeTestService()
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
	 * Convert CompanyLearnersTradeTest key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((CompanyLearnersTradeTest)arg2).getId();
	}

/*
       <p:selectOneMenu id="CompanyLearnersTradeTestId" value="#{xxxUI.CompanyLearnersTradeTest.xxxType}" converter="CompanyLearnersTradeTestConvertor" style="width:95%">
         <f:selectItems value="#{CompanyLearnersTradeTestUI.CompanyLearnersTradeTestList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="CompanyLearnersTradeTest" for="CompanyLearnersTradeTestID"/>
        <p:autoComplete id="CompanyLearnersTradeTestID" value="#{CompanyLearnersTradeTestUI.CompanyLearnersTradeTest.municipality}" completeMethod="#{CompanyLearnersTradeTestUI.completeCompanyLearnersTradeTest}"
                            var="rv" itemLabel="#{rv.CompanyLearnersTradeTestDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="CompanyLearnersTradeTestConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="CompanyLearnersTradeTest" style="white-space: nowrap">#{rv.CompanyLearnersTradeTestDescription}</p:column>
       </p:autoComplete>         
       
*/

}
