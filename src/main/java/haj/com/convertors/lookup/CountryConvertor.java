package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.Country;
import haj.com.service.lookup.CountryService;

// TODO: Auto-generated Javadoc
/**
 * The Class CountryConvertor.
 */
@FacesConverter(value = "CountryConvertor")
public class CountryConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a Country.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return Country
	 * @see    Country
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new CountryService()
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
	 * Convert Country key to String object.
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
		return ""+((Country)arg2).getId();
	}

/*
       <p:selectOneMenu id="CountryId" value="#{xxxUI.Country.xxxType}" converter="CountryConvertor" style="width:95%">
         <f:selectItems value="#{CountryUI.CountryList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="Country" for="CountryID"/>
        <p:autoComplete id="CountryID" value="#{CountryUI.Country.municipality}" completeMethod="#{CountryUI.completeCountry}"
                            var="rv" itemLabel="#{rv.CountryDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="CountryConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="Country" style="white-space: nowrap">#{rv.CountryDescription}</p:column>
       </p:autoComplete>         
       
*/

}
