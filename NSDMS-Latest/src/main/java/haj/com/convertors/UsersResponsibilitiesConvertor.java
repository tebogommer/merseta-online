package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.UsersResponsibilities;
import haj.com.service.UsersResponsibilitiesService;

@FacesConverter(value = "UsersResponsibilitiesConvertor")
public class UsersResponsibilitiesConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a UsersResponsibilities
 	 * @author TechFinium 
 	 * @see    UsersResponsibilities
 	 * @return UsersResponsibilities
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new UsersResponsibilitiesService()
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
	 * Convert UsersResponsibilities key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((UsersResponsibilities)arg2).getId();
	}

/*
       <p:selectOneMenu id="UsersResponsibilitiesId" value="#{xxxUI.UsersResponsibilities.xxxType}" converter="UsersResponsibilitiesConvertor" style="width:95%">
         <f:selectItems value="#{UsersResponsibilitiesUI.UsersResponsibilitiesList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="UsersResponsibilities" for="UsersResponsibilitiesID"/>
        <p:autoComplete id="UsersResponsibilitiesID" value="#{UsersResponsibilitiesUI.UsersResponsibilities.municipality}" completeMethod="#{UsersResponsibilitiesUI.completeUsersResponsibilities}"
                            var="rv" itemLabel="#{rv.UsersResponsibilitiesDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="UsersResponsibilitiesConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="UsersResponsibilities" style="white-space: nowrap">#{rv.UsersResponsibilitiesDescription}</p:column>
       </p:autoComplete>         
       
*/

}
