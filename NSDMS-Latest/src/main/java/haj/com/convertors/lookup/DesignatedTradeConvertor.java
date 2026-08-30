package haj.com.convertors.lookup;

import haj.com.entity.lookup.DesignatedTrade;
import haj.com.service.lookup.DesignatedTradeService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "DesignatedTradeConvertor")
public class DesignatedTradeConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a DesignatedTrade
 	 * @author TechFinium 
 	 * @see    DesignatedTrade
 	 * @return DesignatedTrade
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new DesignatedTradeService()
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
	 * Convert DesignatedTrade key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((DesignatedTrade)arg2).getId();
	}

/*
       <p:selectOneMenu id="DesignatedTradeId" value="#{xxxUI.DesignatedTrade.xxxType}" converter="DesignatedTradeConvertor" style="width:95%">
         <f:selectItems value="#{DesignatedTradeUI.DesignatedTradeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="DesignatedTrade" for="DesignatedTradeID"/>
        <p:autoComplete id="DesignatedTradeID" value="#{DesignatedTradeUI.DesignatedTrade.municipality}" completeMethod="#{DesignatedTradeUI.completeDesignatedTrade}"
                            var="rv" itemLabel="#{rv.DesignatedTradeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="DesignatedTradeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="DesignatedTrade" style="white-space: nowrap">#{rv.DesignatedTradeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
