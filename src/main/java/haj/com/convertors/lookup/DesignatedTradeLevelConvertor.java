package haj.com.convertors.lookup;

import haj.com.entity.lookup.DesignatedTradeLevel;
import haj.com.service.lookup.DesignatedTradeLevelService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "DesignatedTradeLevelConvertor")
public class DesignatedTradeLevelConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a DesignatedTradeLevel
 	 * @author TechFinium 
 	 * @see    DesignatedTradeLevel
 	 * @return DesignatedTradeLevel
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new DesignatedTradeLevelService()
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
	 * Convert DesignatedTradeLevel key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((DesignatedTradeLevel)arg2).getId();
	}

/*
       <p:selectOneMenu id="DesignatedTradeLevelId" value="#{xxxUI.DesignatedTradeLevel.xxxType}" converter="DesignatedTradeLevelConvertor" style="width:95%">
         <f:selectItems value="#{DesignatedTradeLevelUI.DesignatedTradeLevelList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="DesignatedTradeLevel" for="DesignatedTradeLevelID"/>
        <p:autoComplete id="DesignatedTradeLevelID" value="#{DesignatedTradeLevelUI.DesignatedTradeLevel.municipality}" completeMethod="#{DesignatedTradeLevelUI.completeDesignatedTradeLevel}"
                            var="rv" itemLabel="#{rv.DesignatedTradeLevelDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="DesignatedTradeLevelConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="DesignatedTradeLevel" style="white-space: nowrap">#{rv.DesignatedTradeLevelDescription}</p:column>
       </p:autoComplete>         
       
*/

}
