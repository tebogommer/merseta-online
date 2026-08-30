package haj.com.convertors.lookup;

import haj.com.entity.lookup.AccreditationStatus;
import haj.com.service.lookup.AccreditationStatusService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "AccreditationStatusConvertor")
public class AccreditationStatusConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a AccreditationStatus
 	 * @author TechFinium 
 	 * @see    AccreditationStatus
 	 * @return AccreditationStatus
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new AccreditationStatusService()
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
	 * Convert AccreditationStatus key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((AccreditationStatus)arg2).getId();
	}

/*
       <p:selectOneMenu id="AccreditationStatusId" value="#{xxxUI.AccreditationStatus.xxxType}" converter="AccreditationStatusConvertor" style="width:95%">
         <f:selectItems value="#{AccreditationStatusUI.AccreditationStatusList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="AccreditationStatus" for="AccreditationStatusID"/>
        <p:autoComplete id="AccreditationStatusID" value="#{AccreditationStatusUI.AccreditationStatus.municipality}" completeMethod="#{AccreditationStatusUI.completeAccreditationStatus}"
                            var="rv" itemLabel="#{rv.AccreditationStatusDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="AccreditationStatusConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="AccreditationStatus" style="white-space: nowrap">#{rv.AccreditationStatusDescription}</p:column>
       </p:autoComplete>         
       
*/

}
