package haj.com.convertors.lookup;

import haj.com.entity.lookup.TradeTestCentersHistoric;
import haj.com.service.lookup.TradeTestCentersHistoricService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "TradeTestCentersHistoricConvertor")
public class TradeTestCentersHistoricConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a TradeTestCentersHistoric
 	 * @author TechFinium 
 	 * @see    TradeTestCentersHistoric
 	 * @return TradeTestCentersHistoric
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new TradeTestCentersHistoricService()
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
	 * Convert TradeTestCentersHistoric key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((TradeTestCentersHistoric)arg2).getId();
	}

/*
       <p:selectOneMenu id="TradeTestCentersHistoricId" value="#{xxxUI.TradeTestCentersHistoric.xxxType}" converter="TradeTestCentersHistoricConvertor" style="width:95%">
         <f:selectItems value="#{TradeTestCentersHistoricUI.TradeTestCentersHistoricList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="TradeTestCentersHistoric" for="TradeTestCentersHistoricID"/>
        <p:autoComplete id="TradeTestCentersHistoricID" value="#{TradeTestCentersHistoricUI.TradeTestCentersHistoric.municipality}" completeMethod="#{TradeTestCentersHistoricUI.completeTradeTestCentersHistoric}"
                            var="rv" itemLabel="#{rv.TradeTestCentersHistoricDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="TradeTestCentersHistoricConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="TradeTestCentersHistoric" style="white-space: nowrap">#{rv.TradeTestCentersHistoricDescription}</p:column>
       </p:autoComplete>         
       
*/

}
