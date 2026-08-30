package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.GrantAmount;
import haj.com.service.lookup.GrantAmountService;

@FacesConverter(value = "GrantAmountConvertor")
public class GrantAmountConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a GrantAmount
 	 * @author TechFinium 
 	 * @see    GrantAmount
 	 * @return GrantAmount
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new GrantAmountService()
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
	 * Convert GrantAmount key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((GrantAmount)arg2).getId();
	}

/*
       <p:selectOneMenu id="GrantAmountId" value="#{xxxUI.GrantAmount.xxxType}" converter="GrantAmountConvertor" style="width:95%">
         <f:selectItems value="#{GrantAmountUI.GrantAmountList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="GrantAmount" for="GrantAmountID"/>
        <p:autoComplete id="GrantAmountID" value="#{GrantAmountUI.GrantAmount.municipality}" completeMethod="#{GrantAmountUI.completeGrantAmount}"
                            var="rv" itemLabel="#{rv.GrantAmountDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="GrantAmountConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="GrantAmount" style="white-space: nowrap">#{rv.GrantAmountDescription}</p:column>
       </p:autoComplete>         
       
*/

}
