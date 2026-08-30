package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyProviderTrade;
import haj.com.service.lookup.LegacyProviderTradeService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyProviderTradeConvertor")
public class LegacyProviderTradeConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyProviderTrade
 	 * @author TechFinium 
 	 * @see    LegacyProviderTrade
 	 * @return LegacyProviderTrade
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyProviderTradeService()
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
	 * Convert LegacyProviderTrade key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyProviderTrade)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyProviderTradeId" value="#{xxxUI.LegacyProviderTrade.xxxType}" converter="LegacyProviderTradeConvertor" style="width:95%">
         <f:selectItems value="#{LegacyProviderTradeUI.LegacyProviderTradeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyProviderTrade" for="LegacyProviderTradeID"/>
        <p:autoComplete id="LegacyProviderTradeID" value="#{LegacyProviderTradeUI.LegacyProviderTrade.municipality}" completeMethod="#{LegacyProviderTradeUI.completeLegacyProviderTrade}"
                            var="rv" itemLabel="#{rv.LegacyProviderTradeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyProviderTradeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyProviderTrade" style="white-space: nowrap">#{rv.LegacyProviderTradeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
