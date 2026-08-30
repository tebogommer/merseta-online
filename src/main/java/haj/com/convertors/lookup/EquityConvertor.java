package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.Equity;
import haj.com.service.lookup.EquityService;

// TODO: Auto-generated Javadoc
/**
 * The Class EquityConvertor.
 */
@FacesConverter(value = "EquityConvertor")
public class EquityConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a Equity.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return Equity
	 * @see    Equity
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new EquityService()
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
	 * Convert Equity key to String object.
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
		return ""+((Equity)arg2).getId();
	}

/*
       <p:selectOneMenu id="EquityId" value="#{xxxUI.Equity.xxxType}" converter="EquityConvertor" style="width:95%">
         <f:selectItems value="#{EquityUI.EquityList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="Equity" for="EquityID"/>
        <p:autoComplete id="EquityID" value="#{EquityUI.Equity.municipality}" completeMethod="#{EquityUI.completeEquity}"
                            var="rv" itemLabel="#{rv.EquityDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="EquityConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="Equity" style="white-space: nowrap">#{rv.EquityDescription}</p:column>
       </p:autoComplete>         
       
*/

}
