package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.EnrolmentStatusReason;
import haj.com.service.lookup.EnrolmentStatusReasonService;

// TODO: Auto-generated Javadoc
/**
 * The Class EnrolmentStatusReasonConvertor.
 */
@FacesConverter(value = "EnrolmentStatusReasonConvertor")
public class EnrolmentStatusReasonConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a EnrolmentStatusReason.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return EnrolmentStatusReason
	 * @see    EnrolmentStatusReason
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new EnrolmentStatusReasonService()
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
	 * Convert EnrolmentStatusReason key to String object.
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
		return ""+((EnrolmentStatusReason)arg2).getId();
	}

/*
       <p:selectOneMenu id="EnrolmentStatusReasonId" value="#{xxxUI.EnrolmentStatusReason.xxxType}" converter="EnrolmentStatusReasonConvertor" style="width:95%">
         <f:selectItems value="#{EnrolmentStatusReasonUI.EnrolmentStatusReasonList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="EnrolmentStatusReason" for="EnrolmentStatusReasonID"/>
        <p:autoComplete id="EnrolmentStatusReasonID" value="#{EnrolmentStatusReasonUI.EnrolmentStatusReason.municipality}" completeMethod="#{EnrolmentStatusReasonUI.completeEnrolmentStatusReason}"
                            var="rv" itemLabel="#{rv.EnrolmentStatusReasonDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="EnrolmentStatusReasonConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="EnrolmentStatusReason" style="white-space: nowrap">#{rv.EnrolmentStatusReasonDescription}</p:column>
       </p:autoComplete>         
       
*/

}
