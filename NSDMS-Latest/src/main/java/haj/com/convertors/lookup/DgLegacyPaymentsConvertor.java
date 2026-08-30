package haj.com.convertors.lookup;

import haj.com.entity.lookup.DgLegacyPayments;
import haj.com.service.lookup.DgLegacyPaymentsService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "DgLegacyPaymentsConvertor")
public class DgLegacyPaymentsConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a DgLegacyPayments
 	 * @author TechFinium 
 	 * @see    DgLegacyPayments
 	 * @return DgLegacyPayments
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new DgLegacyPaymentsService()
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
	 * Convert DgLegacyPayments key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((DgLegacyPayments)arg2).getId();
	}

/*
       <p:selectOneMenu id="DgLegacyPaymentsId" value="#{xxxUI.DgLegacyPayments.xxxType}" converter="DgLegacyPaymentsConvertor" style="width:95%">
         <f:selectItems value="#{DgLegacyPaymentsUI.DgLegacyPaymentsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="DgLegacyPayments" for="DgLegacyPaymentsID"/>
        <p:autoComplete id="DgLegacyPaymentsID" value="#{DgLegacyPaymentsUI.DgLegacyPayments.municipality}" completeMethod="#{DgLegacyPaymentsUI.completeDgLegacyPayments}"
                            var="rv" itemLabel="#{rv.DgLegacyPaymentsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="DgLegacyPaymentsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="DgLegacyPayments" style="white-space: nowrap">#{rv.DgLegacyPaymentsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
