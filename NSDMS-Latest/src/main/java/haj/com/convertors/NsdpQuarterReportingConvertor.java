package haj.com.convertors;

import haj.com.entity.NsdpQuarterReporting;
import haj.com.service.NsdpQuarterReportingService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "NsdpQuarterReportingConvertor")
public class NsdpQuarterReportingConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a NsdpQuarterReporting
 	 * @author TechFinium 
 	 * @see    NsdpQuarterReporting
 	 * @return NsdpQuarterReporting
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new NsdpQuarterReportingService()
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
	 * Convert NsdpQuarterReporting key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((NsdpQuarterReporting)arg2).getId();
	}

/*
       <p:selectOneMenu id="NsdpQuarterReportingId" value="#{xxxUI.NsdpQuarterReporting.xxxType}" converter="NsdpQuarterReportingConvertor" style="width:95%">
         <f:selectItems value="#{NsdpQuarterReportingUI.NsdpQuarterReportingList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="NsdpQuarterReporting" for="NsdpQuarterReportingID"/>
        <p:autoComplete id="NsdpQuarterReportingID" value="#{NsdpQuarterReportingUI.NsdpQuarterReporting.municipality}" completeMethod="#{NsdpQuarterReportingUI.completeNsdpQuarterReporting}"
                            var="rv" itemLabel="#{rv.NsdpQuarterReportingDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="NsdpQuarterReportingConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="NsdpQuarterReporting" style="white-space: nowrap">#{rv.NsdpQuarterReportingDescription}</p:column>
       </p:autoComplete>         
       
*/

}
