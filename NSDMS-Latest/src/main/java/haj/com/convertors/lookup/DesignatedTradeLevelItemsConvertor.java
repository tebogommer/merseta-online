package haj.com.convertors.lookup;

import haj.com.entity.lookup.DesignatedTradeLevelItems;
import haj.com.service.lookup.DesignatedTradeLevelItemsService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "DesignatedTradeLevelItemsConvertor")
public class DesignatedTradeLevelItemsConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a DesignatedTradeLevelItems
 	 * @author TechFinium 
 	 * @see    DesignatedTradeLevelItems
 	 * @return DesignatedTradeLevelItems
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new DesignatedTradeLevelItemsService()
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
	 * Convert DesignatedTradeLevelItems key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((DesignatedTradeLevelItems)arg2).getId();
	}

/*
       <p:selectOneMenu id="DesignatedTradeLevelItemsId" value="#{xxxUI.DesignatedTradeLevelItems.xxxType}" converter="DesignatedTradeLevelItemsConvertor" style="width:95%">
         <f:selectItems value="#{DesignatedTradeLevelItemsUI.DesignatedTradeLevelItemsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="DesignatedTradeLevelItems" for="DesignatedTradeLevelItemsID"/>
        <p:autoComplete id="DesignatedTradeLevelItemsID" value="#{DesignatedTradeLevelItemsUI.DesignatedTradeLevelItems.municipality}" completeMethod="#{DesignatedTradeLevelItemsUI.completeDesignatedTradeLevelItems}"
                            var="rv" itemLabel="#{rv.DesignatedTradeLevelItemsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="DesignatedTradeLevelItemsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="DesignatedTradeLevelItems" style="white-space: nowrap">#{rv.DesignatedTradeLevelItemsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
