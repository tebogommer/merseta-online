package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.Wsp;
import haj.com.service.WspService;

// TODO: Auto-generated Javadoc
/**
 * The Class WspConvertor.
 */
@FacesConverter(value = "WspConvertor")
public class WspConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a Wsp.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return Wsp
	 * @see    Wsp
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new WspService()
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
	 * Convert Wsp key to String object.
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
		return ""+((Wsp)arg2).getId();
	}

/*
       <p:selectOneMenu id="WspId" value="#{xxxUI.Wsp.xxxType}" converter="WspConvertor" style="width:95%">
         <f:selectItems value="#{WspUI.WspList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="Wsp" for="WspID"/>
        <p:autoComplete id="WspID" value="#{WspUI.Wsp.municipality}" completeMethod="#{WspUI.completeWsp}"
                            var="rv" itemLabel="#{rv.WspDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="WspConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="Wsp" style="white-space: nowrap">#{rv.WspDescription}</p:column>
       </p:autoComplete>         
       
*/

}
