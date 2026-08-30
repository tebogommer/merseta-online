package haj.com.convertors;

import haj.com.entity.UserLearnership;
import haj.com.service.UserLearnershipService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "UserLearnershipConvertor")
public class UserLearnershipConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a UserLearnership
 	 * @author TechFinium 
 	 * @see    UserLearnership
 	 * @return UserLearnership
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new UserLearnershipService()
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
	 * Convert UserLearnership key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((UserLearnership)arg2).getId();
	}

/*
       <p:selectOneMenu id="UserLearnershipId" value="#{xxxUI.UserLearnership.xxxType}" converter="UserLearnershipConvertor" style="width:95%">
         <f:selectItems value="#{UserLearnershipUI.UserLearnershipList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="UserLearnership" for="UserLearnershipID"/>
        <p:autoComplete id="UserLearnershipID" value="#{UserLearnershipUI.UserLearnership.municipality}" completeMethod="#{UserLearnershipUI.completeUserLearnership}"
                            var="rv" itemLabel="#{rv.UserLearnershipDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="UserLearnershipConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="UserLearnership" style="white-space: nowrap">#{rv.UserLearnershipDescription}</p:column>
       </p:autoComplete>         
       
*/

}
