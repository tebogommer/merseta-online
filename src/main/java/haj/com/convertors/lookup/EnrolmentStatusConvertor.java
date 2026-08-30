package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.EnrolmentStatus;
import haj.com.service.lookup.EnrolmentStatusService;

// TODO: Auto-generated Javadoc
/**
 * The Class EnrolmentStatusConvertor.
 */
@FacesConverter(value = "EnrolmentStatusConvertor")
public class EnrolmentStatusConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a EnrolmentStatus.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return EnrolmentStatus
	 * @see    EnrolmentStatus
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new EnrolmentStatusService()
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
	 * Convert EnrolmentStatus key to String object.
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
		return ""+((EnrolmentStatus)arg2).getId();
	}

/*
       <p:selectOneMenu id="EnrolmentStatusId" value="#{xxxUI.EnrolmentStatus.xxxType}" converter="EnrolmentStatusConvertor" style="width:95%">
         <f:selectItems value="#{EnrolmentStatusUI.EnrolmentStatusList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="EnrolmentStatus" for="EnrolmentStatusID"/>
        <p:autoComplete id="EnrolmentStatusID" value="#{EnrolmentStatusUI.EnrolmentStatus.municipality}" completeMethod="#{EnrolmentStatusUI.completeEnrolmentStatus}"
                            var="rv" itemLabel="#{rv.EnrolmentStatusDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="EnrolmentStatusConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="EnrolmentStatus" style="white-space: nowrap">#{rv.EnrolmentStatusDescription}</p:column>
       </p:autoComplete>         
       
*/

}
