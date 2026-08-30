package haj.com.convertors;

import haj.com.entity.JasperDownloadTracker;
import haj.com.service.JasperDownloadTrackerService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "JasperDownloadTrackerConvertor")
public class JasperDownloadTrackerConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a JasperDownloadTracker
 	 * @author TechFinium 
 	 * @see    JasperDownloadTracker
 	 * @return JasperDownloadTracker
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new JasperDownloadTrackerService()
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
	 * Convert JasperDownloadTracker key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((JasperDownloadTracker)arg2).getId();
	}

/*
       <p:selectOneMenu id="JasperDownloadTrackerId" value="#{xxxUI.JasperDownloadTracker.xxxType}" converter="JasperDownloadTrackerConvertor" style="width:95%">
         <f:selectItems value="#{JasperDownloadTrackerUI.JasperDownloadTrackerList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="JasperDownloadTracker" for="JasperDownloadTrackerID"/>
        <p:autoComplete id="JasperDownloadTrackerID" value="#{JasperDownloadTrackerUI.JasperDownloadTracker.municipality}" completeMethod="#{JasperDownloadTrackerUI.completeJasperDownloadTracker}"
                            var="rv" itemLabel="#{rv.JasperDownloadTrackerDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="JasperDownloadTrackerConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="JasperDownloadTracker" style="white-space: nowrap">#{rv.JasperDownloadTrackerDescription}</p:column>
       </p:autoComplete>         
       
*/

}
