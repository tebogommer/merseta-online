package haj.com.convertors;

import haj.com.entity.TradeTestTaskResult;
import haj.com.service.TradeTestTaskResultService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "TradeTestTaskResultConvertor")
public class TradeTestTaskResultConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a TradeTestTaskResult
 	 * @author TechFinium 
 	 * @see    TradeTestTaskResult
 	 * @return TradeTestTaskResult
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new TradeTestTaskResultService()
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
	 * Convert TradeTestTaskResult key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((TradeTestTaskResult)arg2).getId();
	}

/*
       <p:selectOneMenu id="TradeTestTaskResultId" value="#{xxxUI.TradeTestTaskResult.xxxType}" converter="TradeTestTaskResultConvertor" style="width:95%">
         <f:selectItems value="#{TradeTestTaskResultUI.TradeTestTaskResultList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="TradeTestTaskResult" for="TradeTestTaskResultID"/>
        <p:autoComplete id="TradeTestTaskResultID" value="#{TradeTestTaskResultUI.TradeTestTaskResult.municipality}" completeMethod="#{TradeTestTaskResultUI.completeTradeTestTaskResult}"
                            var="rv" itemLabel="#{rv.TradeTestTaskResultDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="TradeTestTaskResultConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="TradeTestTaskResult" style="white-space: nowrap">#{rv.TradeTestTaskResultDescription}</p:column>
       </p:autoComplete>         
       
*/

}
