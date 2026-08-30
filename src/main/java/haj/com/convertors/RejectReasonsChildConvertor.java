package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.RejectReasonsChild;
import haj.com.service.RejectReasonsChildService;

// TODO: Auto-generated Javadoc
/**
 * The Class RejectReasonsChildConvertor.
 */
@FacesConverter(value = "RejectReasonsChildConvertor")
public class RejectReasonsChildConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a RejectReasonsChild.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return RejectReasonsChild
	 * @see    RejectReasonsChild
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new RejectReasonsChildService()
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
	 * Convert RejectReasonsChild key to String object.
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
		return ""+((RejectReasonsChild)arg2).getId();
	}

/*
       <p:selectOneMenu id="RejectReasonsChildId" value="#{xxxUI.RejectReasonsChild.xxxType}" converter="RejectReasonsChildConvertor" style="width:95%">
         <f:selectItems value="#{RejectReasonsChildUI.RejectReasonsChildList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="RejectReasonsChild" for="RejectReasonsChildID"/>
        <p:autoComplete id="RejectReasonsChildID" value="#{RejectReasonsChildUI.RejectReasonsChild.municipality}" completeMethod="#{RejectReasonsChildUI.completeRejectReasonsChild}"
                            var="rv" itemLabel="#{rv.RejectReasonsChildDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="RejectReasonsChildConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="RejectReasonsChild" style="white-space: nowrap">#{rv.RejectReasonsChildDescription}</p:column>
       </p:autoComplete>         
       
*/

}
