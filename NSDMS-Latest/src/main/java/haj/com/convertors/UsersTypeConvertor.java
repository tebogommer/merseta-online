package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.UsersType;
import haj.com.service.UsersTypeService;

// TODO: Auto-generated Javadoc
/**
 * The Class UsersTypeConvertor.
 */
@FacesConverter(value = "UsersTypeConvertor")
public class UsersTypeConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a UsersType.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return UsersType
	 * @see    UsersType
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new UsersTypeService()
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
	 * Convert UsersType key to String object.
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
		return ""+((UsersType)arg2).getId();
	}

/*
       <p:selectOneMenu id="UsersTypeId" value="#{xxxUI.UsersType.xxxType}" converter="UsersTypeConvertor" style="width:95%">
         <f:selectItems value="#{UsersTypeUI.UsersTypeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="UsersType" for="UsersTypeID"/>
        <p:autoComplete id="UsersTypeID" value="#{UsersTypeUI.UsersType.municipality}" completeMethod="#{UsersTypeUI.completeUsersType}"
                            var="rv" itemLabel="#{rv.UsersTypeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="UsersTypeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="UsersType" style="white-space: nowrap">#{rv.UsersTypeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
