package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacySectionTwentyEightTradeTest;
import haj.com.service.lookup.LegacySectionTwentyEightTradeTestService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacySectionTwentyEightTradeTestConvertor")
public class LegacySectionTwentyEightTradeTestConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacySectionTwentyEightTradeTest
 	 * @author TechFinium 
 	 * @see    LegacySectionTwentyEightTradeTest
 	 * @return LegacySectionTwentyEightTradeTest
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacySectionTwentyEightTradeTestService()
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
	 * Convert LegacySectionTwentyEightTradeTest key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacySectionTwentyEightTradeTest)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacySectionTwentyEightTradeTestId" value="#{xxxUI.LegacySectionTwentyEightTradeTest.xxxType}" converter="LegacySectionTwentyEightTradeTestConvertor" style="width:95%">
         <f:selectItems value="#{LegacySectionTwentyEightTradeTestUI.LegacySectionTwentyEightTradeTestList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacySectionTwentyEightTradeTest" for="LegacySectionTwentyEightTradeTestID"/>
        <p:autoComplete id="LegacySectionTwentyEightTradeTestID" value="#{LegacySectionTwentyEightTradeTestUI.LegacySectionTwentyEightTradeTest.municipality}" completeMethod="#{LegacySectionTwentyEightTradeTestUI.completeLegacySectionTwentyEightTradeTest}"
                            var="rv" itemLabel="#{rv.LegacySectionTwentyEightTradeTestDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacySectionTwentyEightTradeTestConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacySectionTwentyEightTradeTest" style="white-space: nowrap">#{rv.LegacySectionTwentyEightTradeTestDescription}</p:column>
       </p:autoComplete>         
       
*/

}
