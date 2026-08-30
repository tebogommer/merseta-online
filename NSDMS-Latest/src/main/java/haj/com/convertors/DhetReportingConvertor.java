package haj.com.convertors;

import haj.com.entity.DhetReporting;
import haj.com.service.DhetReportingService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "DhetReportingConvertor")
public class DhetReportingConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a DhetReporting
 	 * @author TechFinium 
 	 * @see    DhetReporting
 	 * @return DhetReporting
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new DhetReportingService()
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
	 * Convert DhetReporting key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((DhetReporting)arg2).getId();
	}

/*
       <p:selectOneMenu id="DhetReportingId" value="#{xxxUI.DhetReporting.xxxType}" converter="DhetReportingConvertor" style="width:95%">
         <f:selectItems value="#{DhetReportingUI.DhetReportingList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="DhetReporting" for="DhetReportingID"/>
        <p:autoComplete id="DhetReportingID" value="#{DhetReportingUI.DhetReporting.municipality}" completeMethod="#{DhetReportingUI.completeDhetReporting}"
                            var="rv" itemLabel="#{rv.DhetReportingDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="DhetReportingConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="DhetReporting" style="white-space: nowrap">#{rv.DhetReportingDescription}</p:column>
       </p:autoComplete>         
       
*/

}
