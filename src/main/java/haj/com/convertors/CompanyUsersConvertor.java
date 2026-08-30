package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.CompanyUsers;
import haj.com.service.CompanyUsersService;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanySDFConvertor.
 */
@FacesConverter(value = "CompanyUsersConvertor")
public class CompanyUsersConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a CompanySDF.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return CompanySDF
	 * @see    CompanyUsers
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new CompanyUsersService()
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
	 * Convert CompanySDF key to String object.
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
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((CompanyUsers)arg2).getId();
	}
}
