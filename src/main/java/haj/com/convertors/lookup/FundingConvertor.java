package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.Funding;
import haj.com.service.lookup.FundingService;

// TODO: Auto-generated Javadoc
/**
 * The Class FundingConvertor.
 */
@FacesConverter(value = "FundingConvertor")
public class FundingConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a Funding.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return Funding
	 * @see    Funding
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new FundingService()
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
	 * Convert Funding key to String object.
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
		if (arg2 == null)return "";
		return ""+((Funding)arg2).getId();
	}

/*
       <p:selectOneMenu id="FundingId" value="#{xxxUI.Funding.xxxType}" converter="FundingConvertor" style="width:95%">
         <f:selectItems value="#{FundingUI.FundingList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="Funding" for="FundingID"/>
        <p:autoComplete id="FundingID" value="#{FundingUI.Funding.municipality}" completeMethod="#{FundingUI.completeFunding}"
                            var="rv" itemLabel="#{rv.FundingDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="FundingConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="Funding" style="white-space: nowrap">#{rv.FundingDescription}</p:column>
       </p:autoComplete>         
       
*/

}
