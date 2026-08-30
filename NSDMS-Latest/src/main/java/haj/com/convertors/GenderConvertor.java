package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.Gender;
import haj.com.service.GenderService;

// TODO: Auto-generated Javadoc
/**
 * The Class GenderConvertor.
 */
@FacesConverter(value = "GenderConvertor")
public class GenderConvertor implements Converter {
	
	/** The logger. */
	protected final Log logger = LogFactory.getLog(this.getClass());

	/**
	 * Used by JSF to get a Gender.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return Gender
	 * @see Gender
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new GenderService().findByKey(Integer.valueOf(value));
			} catch (NumberFormatException e) {
				logger.fatal(e);
			} catch (Exception e) {
				logger.fatal(e);
			}

		}
		return null;
	}

	/**
	 * Convert Gender key to String object.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param arg2 the arg 2
	 * @return String
	 * @see String
	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) {
			return "";
		}
		return "" + ((Gender) arg2).getId();
	}

	/*
	 * <p:selectOneMenu id="GenderId" value="#{xxxUI.Gender.xxxType}"
	 * converter="GenderConvertor" style="width:95%"> <f:selectItems
	 * value="#{GenderUI.GenderList}" var="rv" itemLabel="#{rv.a}"
	 * itemValue="#{rv}"/> </p:selectOneMenu>
	 * 
	 * <h:outputLabel value="Gender" for="GenderID"/> <p:autoComplete
	 * id="GenderID" value="#{GenderUI.Gender.municipality}"
	 * completeMethod="#{GenderUI.completeGender}" var="rv"
	 * itemLabel="#{rv.GenderDescription}" itemValue="#{rv}"
	 * forceSelection="true" converter="GenderConvertor" dropdown="true"
	 * minQueryLength="3" maxResults="10" > <p:column headerText="Gender"
	 * style="white-space: nowrap">#{rv.GenderDescription}</p:column>
	 * </p:autoComplete>
	 * 
	 */

}
