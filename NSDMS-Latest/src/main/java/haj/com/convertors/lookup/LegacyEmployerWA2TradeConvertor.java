package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyEmployerWA2Trade;
import haj.com.service.lookup.LegacyEmployerWA2TradeService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyEmployerWA2TradeConvertor")
public class LegacyEmployerWA2TradeConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyEmployerWA2Trade
 	 * @author TechFinium 
 	 * @see    LegacyEmployerWA2Trade
 	 * @return LegacyEmployerWA2Trade
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyEmployerWA2TradeService()
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
	 * Convert LegacyEmployerWA2Trade key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyEmployerWA2Trade)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyEmployerWA2TradeId" value="#{xxxUI.LegacyEmployerWA2Trade.xxxType}" converter="LegacyEmployerWA2TradeConvertor" style="width:95%">
         <f:selectItems value="#{LegacyEmployerWA2TradeUI.LegacyEmployerWA2TradeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyEmployerWA2Trade" for="LegacyEmployerWA2TradeID"/>
        <p:autoComplete id="LegacyEmployerWA2TradeID" value="#{LegacyEmployerWA2TradeUI.LegacyEmployerWA2Trade.municipality}" completeMethod="#{LegacyEmployerWA2TradeUI.completeLegacyEmployerWA2Trade}"
                            var="rv" itemLabel="#{rv.LegacyEmployerWA2TradeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyEmployerWA2TradeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyEmployerWA2Trade" style="white-space: nowrap">#{rv.LegacyEmployerWA2TradeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
