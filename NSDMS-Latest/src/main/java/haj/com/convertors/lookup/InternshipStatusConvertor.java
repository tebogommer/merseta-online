package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.InternshipStatus;
import haj.com.service.lookup.InternshipStatusService;

// TODO: Auto-generated Javadoc
/**
 * The Class InternshipStatusConvertor.
 */
@FacesConverter(value = "InternshipStatusConvertor")
public class InternshipStatusConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a InternshipStatus.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return InternshipStatus
	 * @see    InternshipStatus
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new InternshipStatusService()
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
	 * Convert InternshipStatus key to String object.
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
		return ""+((InternshipStatus)arg2).getId();
	}

/*
       <p:selectOneMenu id="InternshipStatusId" value="#{xxxUI.InternshipStatus.xxxType}" converter="InternshipStatusConvertor" style="width:95%">
         <f:selectItems value="#{InternshipStatusUI.InternshipStatusList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="InternshipStatus" for="InternshipStatusID"/>
        <p:autoComplete id="InternshipStatusID" value="#{InternshipStatusUI.InternshipStatus.municipality}" completeMethod="#{InternshipStatusUI.completeInternshipStatus}"
                            var="rv" itemLabel="#{rv.InternshipStatusDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="InternshipStatusConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="InternshipStatus" style="white-space: nowrap">#{rv.InternshipStatusDescription}</p:column>
       </p:autoComplete>         
       
*/

}
