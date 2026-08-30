package haj.com.convertors;

import haj.com.entity.UploadTrackerAtrWsp;
import haj.com.service.UploadTrackerAtrWspService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "UploadTrackerAtrWspConvertor")
public class UploadTrackerAtrWspConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a UploadTrackerAtrWsp
 	 * @author TechFinium 
 	 * @see    UploadTrackerAtrWsp
 	 * @return UploadTrackerAtrWsp
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new UploadTrackerAtrWspService()
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
	 * Convert UploadTrackerAtrWsp key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((UploadTrackerAtrWsp)arg2).getId();
	}

/*
       <p:selectOneMenu id="UploadTrackerAtrWspId" value="#{xxxUI.UploadTrackerAtrWsp.xxxType}" converter="UploadTrackerAtrWspConvertor" style="width:95%">
         <f:selectItems value="#{UploadTrackerAtrWspUI.UploadTrackerAtrWspList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="UploadTrackerAtrWsp" for="UploadTrackerAtrWspID"/>
        <p:autoComplete id="UploadTrackerAtrWspID" value="#{UploadTrackerAtrWspUI.UploadTrackerAtrWsp.municipality}" completeMethod="#{UploadTrackerAtrWspUI.completeUploadTrackerAtrWsp}"
                            var="rv" itemLabel="#{rv.UploadTrackerAtrWspDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="UploadTrackerAtrWspConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="UploadTrackerAtrWsp" style="white-space: nowrap">#{rv.UploadTrackerAtrWspDescription}</p:column>
       </p:autoComplete>         
       
*/

}
