package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.AuditorMonitorReview;
import haj.com.service.lookup.AuditorMonitorReviewService;

@FacesConverter(value = "AuditorMonitorReviewConvertor")
public class AuditorMonitorReviewConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a AuditorMonitorReview
 	 * @author TechFinium 
 	 * @see    AuditorMonitorReview
 	 * @return AuditorMonitorReview
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new AuditorMonitorReviewService()
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
	 * Convert AuditorMonitorReview key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((AuditorMonitorReview)arg2).getId();
	}

/*
       <p:selectOneMenu id="AuditorMonitorReviewId" value="#{xxxUI.AuditorMonitorReview.xxxType}" converter="AuditorMonitorReviewConvertor" style="width:95%">
         <f:selectItems value="#{AuditorMonitorReviewUI.AuditorMonitorReviewList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="AuditorMonitorReview" for="AuditorMonitorReviewID"/>
        <p:autoComplete id="AuditorMonitorReviewID" value="#{AuditorMonitorReviewUI.AuditorMonitorReview.municipality}" completeMethod="#{AuditorMonitorReviewUI.completeAuditorMonitorReview}"
                            var="rv" itemLabel="#{rv.AuditorMonitorReviewDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="AuditorMonitorReviewConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="AuditorMonitorReview" style="white-space: nowrap">#{rv.AuditorMonitorReviewDescription}</p:column>
       </p:autoComplete>         
       
*/

}
