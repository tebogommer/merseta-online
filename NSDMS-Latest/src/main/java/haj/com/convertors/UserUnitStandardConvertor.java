package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.UserUnitStandard;
import haj.com.service.UserUnitStandardService;

// TODO: Auto-generated Javadoc
/**
 * The Class UserUnitStandardConvertor.
 */
@FacesConverter(value = "UserUnitStandardConvertor")
public class UserUnitStandardConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a UserUnitStandard.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return UserUnitStandard
	 * @see    UserUnitStandard
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new UserUnitStandardService()
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
	 * Convert UserUnitStandard key to String object.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param arg2 the arg 2
	 * @return String
	 * @see    String
	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return ""+((UserUnitStandard)arg2).getId();
	}

/*
       <p:selectOneMenu id="UserUnitStandardId" value="#{xxxUI.UserUnitStandard.xxxType}" converter="UserUnitStandardConvertor" style="width:95%">
         <f:selectItems value="#{UserUnitStandardUI.UserUnitStandardList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="UserUnitStandard" for="UserUnitStandardID"/>
        <p:autoComplete id="UserUnitStandardID" value="#{UserUnitStandardUI.UserUnitStandard.municipality}" completeMethod="#{UserUnitStandardUI.completeUserUnitStandard}"
                            var="rv" itemLabel="#{rv.UserUnitStandardDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="UserUnitStandardConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="UserUnitStandard" style="white-space: nowrap">#{rv.UserUnitStandardDescription}</p:column>
       </p:autoComplete>         
       
*/

}
