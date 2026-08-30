package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.Chamber;
import haj.com.service.lookup.ChamberService;

// TODO: Auto-generated Javadoc
/**
 * The Class ChamberConvertor.
 */
@FacesConverter(value = "ChamberConvertor")
public class ChamberConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a Chamber.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return Chamber
	 * @see    Chamber
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new ChamberService()
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
	 * Convert Chamber key to String object.
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
		return ""+((Chamber)arg2).getId();
	}

/*
       <p:selectOneMenu id="ChamberId" value="#{xxxUI.Chamber.xxxType}" converter="ChamberConvertor" style="width:95%">
         <f:selectItems value="#{ChamberUI.ChamberList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="Chamber" for="ChamberID"/>
        <p:autoComplete id="ChamberID" value="#{ChamberUI.Chamber.municipality}" completeMethod="#{ChamberUI.completeChamber}"
                            var="rv" itemLabel="#{rv.ChamberDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="ChamberConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="Chamber" style="white-space: nowrap">#{rv.ChamberDescription}</p:column>
       </p:autoComplete>         
       
*/

}
