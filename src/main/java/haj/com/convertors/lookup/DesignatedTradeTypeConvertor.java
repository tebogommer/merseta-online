package haj.com.convertors.lookup;

import haj.com.entity.lookup.DesignatedTradeType;
import haj.com.service.lookup.DesignatedTradeTypeService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "DesignatedTradeTypeConvertor")
public class DesignatedTradeTypeConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a DesignatedTradeType
 	 * @author TechFinium 
 	 * @see    DesignatedTradeType
 	 * @return DesignatedTradeType
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new DesignatedTradeTypeService()
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
	 * Convert DesignatedTradeType key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((DesignatedTradeType)arg2).getId();
	}

/*
       <p:selectOneMenu id="DesignatedTradeTypeId" value="#{xxxUI.DesignatedTradeType.xxxType}" converter="DesignatedTradeTypeConvertor" style="width:95%">
         <f:selectItems value="#{DesignatedTradeTypeUI.DesignatedTradeTypeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="DesignatedTradeType" for="DesignatedTradeTypeID"/>
        <p:autoComplete id="DesignatedTradeTypeID" value="#{DesignatedTradeTypeUI.DesignatedTradeType.municipality}" completeMethod="#{DesignatedTradeTypeUI.completeDesignatedTradeType}"
                            var="rv" itemLabel="#{rv.DesignatedTradeTypeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="DesignatedTradeTypeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="DesignatedTradeType" style="white-space: nowrap">#{rv.DesignatedTradeTypeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
