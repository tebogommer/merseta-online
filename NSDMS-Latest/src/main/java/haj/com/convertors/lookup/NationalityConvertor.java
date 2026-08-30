package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.Nationality;
import haj.com.service.lookup.NationalityService;

// TODO: Auto-generated Javadoc
/**
 * The Class NationalityConvertor.
 */
@FacesConverter(value = "NationalityConvertor")
public class NationalityConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a Nationality.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return Nationality
	 * @see    Nationality
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new NationalityService()
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
	 * Convert Nationality key to String object.
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
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((Nationality)arg2).getId();
	}

/*
       <p:selectOneMenu id="NationalityId" value="#{xxxUI.Nationality.xxxType}" converter="NationalityConvertor" style="width:95%">
         <f:selectItems value="#{NationalityUI.NationalityList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="Nationality" for="NationalityID"/>
        <p:autoComplete id="NationalityID" value="#{NationalityUI.Nationality.municipality}" completeMethod="#{NationalityUI.completeNationality}"
                            var="rv" itemLabel="#{rv.NationalityDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="NationalityConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="Nationality" style="white-space: nowrap">#{rv.NationalityDescription}</p:column>
       </p:autoComplete>         
       
*/

}
