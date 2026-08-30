package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.BankingDetails;
import haj.com.service.BankingDetailsService;

// TODO: Auto-generated Javadoc
/**
 * The Class BankingDetailsConvertor.
 */
@FacesConverter(value = "BankingDetailsConvertor")
public class BankingDetailsConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a BankingDetails.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return BankingDetails
	 * @see    BankingDetails
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new BankingDetailsService()
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
	 * Convert BankingDetails key to String object.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param arg2 the arg 2
	 * @return String
	 * @see    String
	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return ""+((BankingDetails)arg2).getId();
	}

/*
       <p:selectOneMenu id="BankingDetailsId" value="#{xxxUI.BankingDetails.xxxType}" converter="BankingDetailsConvertor" style="width:95%">
         <f:selectItems value="#{BankingDetailsUI.BankingDetailsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="BankingDetails" for="BankingDetailsID"/>
        <p:autoComplete id="BankingDetailsID" value="#{BankingDetailsUI.BankingDetails.municipality}" completeMethod="#{BankingDetailsUI.completeBankingDetails}"
                            var="rv" itemLabel="#{rv.BankingDetailsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="BankingDetailsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="BankingDetails" style="white-space: nowrap">#{rv.BankingDetailsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
