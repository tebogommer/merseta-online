package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.MgVerificationCompleted;
import haj.com.service.MgVerificationCompletedService;

@FacesConverter(value = "MgVerificationCompletedConvertor")
public class MgVerificationCompletedConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a MgVerificationCompleted
 	 * @author TechFinium 
 	 * @see    MgVerificationCompleted
 	 * @return MgVerificationCompleted
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new MgVerificationCompletedService()
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
	 * Convert MgVerificationCompleted key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((MgVerificationCompleted)arg2).getId();
	}

/*
       <p:selectOneMenu id="MgVerificationCompletedId" value="#{xxxUI.MgVerificationCompleted.xxxType}" converter="MgVerificationCompletedConvertor" style="width:95%">
         <f:selectItems value="#{MgVerificationCompletedUI.MgVerificationCompletedList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="MgVerificationCompleted" for="MgVerificationCompletedID"/>
        <p:autoComplete id="MgVerificationCompletedID" value="#{MgVerificationCompletedUI.MgVerificationCompleted.municipality}" completeMethod="#{MgVerificationCompletedUI.completeMgVerificationCompleted}"
                            var="rv" itemLabel="#{rv.MgVerificationCompletedDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="MgVerificationCompletedConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="MgVerificationCompleted" style="white-space: nowrap">#{rv.MgVerificationCompletedDescription}</p:column>
       </p:autoComplete>         
       
*/

}
