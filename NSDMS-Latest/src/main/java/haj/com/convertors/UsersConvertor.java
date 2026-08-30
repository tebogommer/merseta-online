package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.Users;
import haj.com.service.UsersService;

// TODO: Auto-generated Javadoc
/**
 * The Class UsersConvertor.
 */
@FacesConverter(value = "UsersConvertor")
public class UsersConvertor implements Converter {

	/** The logger. */
	protected final Log logger = LogFactory.getLog(this.getClass());

	/**
	 * Used by JSF to get a Users.
	 * @author TechFinium
	 * @param arg0
	 * the arg 0
	 * @param arg1
	 * the arg 1
	 * @param value
	 * the value
	 * @return Users
	 * @see Users
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new UsersService().findByKey(Integer.valueOf(value));
			} catch (NumberFormatException e) {
				logger.fatal(e);
			} catch (Exception e) {
				logger.fatal(e);
			}

		}
		return null;
	}

	/**
	 * Convert Users key to String object.
	 * @author TechFinium
	 * @param arg0
	 * the arg 0
	 * @param arg1
	 * the arg 1
	 * @param arg2
	 * the arg 2
	 * @return String
	 * @see String
	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return "" + ((Users) arg2).getId();
	}

	/*
	 * <p:selectOneMenu id="UsersId" value="#{xxxUI.Users.xxxType}" converter="UsersConvertor" style="width:95%"> <f:selectItems value="#{UsersUI.UsersList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/> </p:selectOneMenu>
	 * 
	 * <h:outputLabel value="Users" for="UsersID"/> <p:autoComplete id="UsersID" value="#{UsersUI.Users.municipality}" completeMethod="#{UsersUI.completeUsers}" var="rv" itemLabel="#{rv.UsersDescription}" itemValue="#{rv}" forceSelection="true" converter="UsersConvertor" dropdown="true" minQueryLength="3" maxResults="10" > <p:column headerText="Users" style="white-space: nowrap">#{rv.UsersDescription}</p:column> </p:autoComplete>
	 * 
	 */

}
