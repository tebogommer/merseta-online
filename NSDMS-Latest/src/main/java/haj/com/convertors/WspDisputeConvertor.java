package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.WspDispute;
import haj.com.service.WspDisputeService;

// TODO: Auto-generated Javadoc
/**
 * The Class WspDisputeConvertor.
 */
@FacesConverter(value = "WspDisputeConvertor")
public class WspDisputeConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a WspDispute.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return WspDispute
	 * @see    WspDispute
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new WspDisputeService()
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
	 * Convert WspDispute key to String object.
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
		return ""+((WspDispute)arg2).getId();
	}

/*
       <p:selectOneMenu id="WspDisputeId" value="#{xxxUI.WspDispute.xxxType}" converter="WspDisputeConvertor" style="width:95%">
         <f:selectItems value="#{WspDisputeUI.WspDisputeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="WspDispute" for="WspDisputeID"/>
        <p:autoComplete id="WspDisputeID" value="#{WspDisputeUI.WspDispute.municipality}" completeMethod="#{WspDisputeUI.completeWspDispute}"
                            var="rv" itemLabel="#{rv.WspDisputeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="WspDisputeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="WspDispute" style="white-space: nowrap">#{rv.WspDisputeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
