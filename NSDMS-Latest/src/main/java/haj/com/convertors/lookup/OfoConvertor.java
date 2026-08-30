package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.Ofo;
import haj.com.service.lookup.OfoService;

// TODO: Auto-generated Javadoc
/**
 * The Class OfoConvertor.
 */
@FacesConverter(value = "OfoConvertor")
public class OfoConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a Ofo.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return Ofo
	 * @see    Ofo
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new OfoService()
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
	 * Convert Ofo key to String object.
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
		return ""+((Ofo)arg2).getId();
	}

/*
       <p:selectOneMenu id="OfoId" value="#{xxxUI.Ofo.xxxType}" converter="OfoConvertor" style="width:95%">
         <f:selectItems value="#{OfoUI.OfoList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="Ofo" for="OfoID"/>
        <p:autoComplete id="OfoID" value="#{OfoUI.Ofo.municipality}" completeMethod="#{OfoUI.completeOfo}"
                            var="rv" itemLabel="#{rv.OfoDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="OfoConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="Ofo" style="white-space: nowrap">#{rv.OfoDescription}</p:column>
       </p:autoComplete>         
       
*/

}
