package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.Bank;
import haj.com.service.lookup.BankService;

// TODO: Auto-generated Javadoc
/**
 * The Class BankConvertor.
 */
@FacesConverter(value = "BankConvertor")
public class BankConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a Bank.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return Bank
	 * @see    Bank
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new BankService()
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
	 * Convert Bank key to String object.
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
		if (arg2 == null) {
			return "";
		}
		return ""+((Bank)arg2).getId();
	}

/*
       <p:selectOneMenu id="BankId" value="#{xxxUI.Bank.xxxType}" converter="BankConvertor" style="width:95%">
         <f:selectItems value="#{BankUI.BankList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="Bank" for="BankID"/>
        <p:autoComplete id="BankID" value="#{BankUI.Bank.municipality}" completeMethod="#{BankUI.completeBank}"
                            var="rv" itemLabel="#{rv.BankDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="BankConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="Bank" style="white-space: nowrap">#{rv.BankDescription}</p:column>
       </p:autoComplete>         
       
*/

}
