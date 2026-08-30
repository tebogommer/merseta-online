package haj.com.convertors;

import haj.com.entity.TransactionsCompanyValidiation;
import haj.com.service.TransactionsCompanyValidiationService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "TransactionsCompanyValidiationConvertor")
public class TransactionsCompanyValidiationConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a TransactionsCompanyValidiation
 	 * @author TechFinium 
 	 * @see    TransactionsCompanyValidiation
 	 * @return TransactionsCompanyValidiation
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new TransactionsCompanyValidiationService()
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
	 * Convert TransactionsCompanyValidiation key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((TransactionsCompanyValidiation)arg2).getId();
	}

/*
       <p:selectOneMenu id="TransactionsCompanyValidiationId" value="#{xxxUI.TransactionsCompanyValidiation.xxxType}" converter="TransactionsCompanyValidiationConvertor" style="width:95%">
         <f:selectItems value="#{TransactionsCompanyValidiationUI.TransactionsCompanyValidiationList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="TransactionsCompanyValidiation" for="TransactionsCompanyValidiationID"/>
        <p:autoComplete id="TransactionsCompanyValidiationID" value="#{TransactionsCompanyValidiationUI.TransactionsCompanyValidiation.municipality}" completeMethod="#{TransactionsCompanyValidiationUI.completeTransactionsCompanyValidiation}"
                            var="rv" itemLabel="#{rv.TransactionsCompanyValidiationDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="TransactionsCompanyValidiationConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="TransactionsCompanyValidiation" style="white-space: nowrap">#{rv.TransactionsCompanyValidiationDescription}</p:column>
       </p:autoComplete>         
       
*/

}
