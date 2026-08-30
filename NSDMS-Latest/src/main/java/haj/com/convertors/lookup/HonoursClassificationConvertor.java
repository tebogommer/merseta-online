package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.HonoursClassification;
import haj.com.service.lookup.HonoursClassificationService;

// TODO: Auto-generated Javadoc
/**
 * The Class HonoursClassificationConvertor.
 */
@FacesConverter(value = "HonoursClassificationConvertor")
public class HonoursClassificationConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a HonoursClassification.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return HonoursClassification
	 * @see    HonoursClassification
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new HonoursClassificationService()
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
	 * Convert HonoursClassification key to String object.
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
		return ""+((HonoursClassification)arg2).getId();
	}

/*
       <p:selectOneMenu id="HonoursClassificationId" value="#{xxxUI.HonoursClassification.xxxType}" converter="HonoursClassificationConvertor" style="width:95%">
         <f:selectItems value="#{HonoursClassificationUI.HonoursClassificationList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="HonoursClassification" for="HonoursClassificationID"/>
        <p:autoComplete id="HonoursClassificationID" value="#{HonoursClassificationUI.HonoursClassification.municipality}" completeMethod="#{HonoursClassificationUI.completeHonoursClassification}"
                            var="rv" itemLabel="#{rv.HonoursClassificationDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="HonoursClassificationConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="HonoursClassification" style="white-space: nowrap">#{rv.HonoursClassificationDescription}</p:column>
       </p:autoComplete>         
       
*/

}
