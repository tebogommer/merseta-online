package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.NqfLevels;
import haj.com.service.NqfLevelsService;

// TODO: Auto-generated Javadoc
/**
 * The Class NqfLevelsConvertor.
 */
@FacesConverter(value = "NqfLevelsConvertor")
public class NqfLevelsConvertor implements Converter {

	/** The logger. */
	protected final Log logger = LogFactory.getLog(this.getClass());

	/**
	 * Used by JSF to get a NqfLevels.
	 *
	 * @author TechFinium
	 * @param arg0
	 *            the arg 0
	 * @param arg1
	 *            the arg 1
	 * @param value
	 *            the value
	 * @return NqfLevels
	 * @see NqfLevels
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new NqfLevelsService().findByKey(Integer.valueOf(value));
			} catch (NumberFormatException e) {
				logger.fatal(e);
			} catch (Exception e) {
				logger.fatal(e);
			}

		}
		return null;
	}

	/**
	 * Convert NqfLevels key to String object.
	 *
	 * @author TechFinium
	 * @param arg0
	 *            the arg 0
	 * @param arg1
	 *            the arg 1
	 * @param arg2
	 *            the arg 2
	 * @return String
	 * @see String
	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) arg2.toString();
		return "" + ((NqfLevels) arg2).getId();
	}

	/*
	 * <p:selectOneMenu id="NqfLevelsId" value="#{xxxUI.NqfLevels.xxxType}"
	 * converter="NqfLevelsConvertor" style="width:95%"> <f:selectItems
	 * value="#{NqfLevelsUI.NqfLevelsList}" var="rv" itemLabel="#{rv.a}"
	 * itemValue="#{rv}"/> </p:selectOneMenu>
	 * 
	 * <h:outputLabel value="NqfLevels" for="NqfLevelsID"/> <p:autoComplete
	 * id="NqfLevelsID" value="#{NqfLevelsUI.NqfLevels.municipality}"
	 * completeMethod="#{NqfLevelsUI.completeNqfLevels}" var="rv"
	 * itemLabel="#{rv.NqfLevelsDescription}" itemValue="#{rv}"
	 * forceSelection="true" converter="NqfLevelsConvertor" dropdown="true"
	 * minQueryLength="3" maxResults="10" > <p:column headerText="NqfLevels"
	 * style="white-space: nowrap">#{rv.NqfLevelsDescription}</p:column>
	 * </p:autoComplete>
	 * 
	 */

}
