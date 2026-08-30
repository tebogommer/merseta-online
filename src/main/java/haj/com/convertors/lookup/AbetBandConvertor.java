package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.AbetBand;
import haj.com.service.lookup.AbetBandService;

// TODO: Auto-generated Javadoc
/**
 * The Class AbetBandConvertor.
 */
@FacesConverter(value = "AbetBandConvertor")
public class AbetBandConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a AbetBand.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return AbetBand
	 * @see    AbetBand
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new AbetBandService()
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
	 * Convert AbetBand key to String object.
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
		return ""+((AbetBand)arg2).getId();
	}

/*
       <p:selectOneMenu id="AbetBandId" value="#{xxxUI.AbetBand.xxxType}" converter="AbetBandConvertor" style="width:95%">
         <f:selectItems value="#{AbetBandUI.AbetBandList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="AbetBand" for="AbetBandID"/>
        <p:autoComplete id="AbetBandID" value="#{AbetBandUI.AbetBand.municipality}" completeMethod="#{AbetBandUI.completeAbetBand}"
                            var="rv" itemLabel="#{rv.AbetBandDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="AbetBandConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="AbetBand" style="white-space: nowrap">#{rv.AbetBandDescription}</p:column>
       </p:autoComplete>         
       
*/

}
