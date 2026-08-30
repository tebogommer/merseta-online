package haj.com.convertors;

import haj.com.entity.LegacyReporting;
import haj.com.service.LegacyReportingService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyReportingConvertor")
public class LegacyReportingConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyReporting
 	 * @author TechFinium 
 	 * @see    LegacyReporting
 	 * @return LegacyReporting
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyReportingService()
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
	 * Convert LegacyReporting key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyReporting)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyReportingId" value="#{xxxUI.LegacyReporting.xxxType}" converter="LegacyReportingConvertor" style="width:95%">
         <f:selectItems value="#{LegacyReportingUI.LegacyReportingList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyReporting" for="LegacyReportingID"/>
        <p:autoComplete id="LegacyReportingID" value="#{LegacyReportingUI.LegacyReporting.municipality}" completeMethod="#{LegacyReportingUI.completeLegacyReporting}"
                            var="rv" itemLabel="#{rv.LegacyReportingDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyReportingConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyReporting" style="white-space: nowrap">#{rv.LegacyReportingDescription}</p:column>
       </p:autoComplete>         
       
*/

}
