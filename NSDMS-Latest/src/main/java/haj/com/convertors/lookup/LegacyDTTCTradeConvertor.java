package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyDTTCTrade;
import haj.com.service.lookup.LegacyDTTCTradeService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyDTTCTradeConvertor")
public class LegacyDTTCTradeConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyDTTCTrade
 	 * @author TechFinium 
 	 * @see    LegacyDTTCTrade
 	 * @return LegacyDTTCTrade
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyDTTCTradeService()
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
	 * Convert LegacyDTTCTrade key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyDTTCTrade)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyDTTCTradeId" value="#{xxxUI.LegacyDTTCTrade.xxxType}" converter="LegacyDTTCTradeConvertor" style="width:95%">
         <f:selectItems value="#{LegacyDTTCTradeUI.LegacyDTTCTradeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyDTTCTrade" for="LegacyDTTCTradeID"/>
        <p:autoComplete id="LegacyDTTCTradeID" value="#{LegacyDTTCTradeUI.LegacyDTTCTrade.municipality}" completeMethod="#{LegacyDTTCTradeUI.completeLegacyDTTCTrade}"
                            var="rv" itemLabel="#{rv.LegacyDTTCTradeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyDTTCTradeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyDTTCTrade" style="white-space: nowrap">#{rv.LegacyDTTCTradeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
