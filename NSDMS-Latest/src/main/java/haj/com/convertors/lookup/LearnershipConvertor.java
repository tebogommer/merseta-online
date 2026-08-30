package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.Learnership;

import haj.com.service.lookup.LearnershipService;


// TODO: Auto-generated Javadoc
/**
 * The Class LearnershipConvertor.
 */
@FacesConverter(value = "LearnershipConvertor")
public class LearnershipConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
		
		/**
		 * Used by JSF to get a MeetingAgenda
	 	 * @author TechFinium 
	 	 * @see    MeetingAgenda
	 	 * @return MeetingAgenda
	 	 */
		@Override
		public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
			if (StringUtils.isBlank(value)) {
				return null;
			} else {

				try {
					return new LearnershipService()
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
		 * Convert MeetingAgenda key to String object
	 	 * @author TechFinium 
	 	 * @see    String
	 	 * @return String
	 	 */
		@Override
		public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
			if (arg2 == null) return "";
			if (arg2 instanceof String) return arg2.toString();
			return ""+((Learnership)arg2).getId();
		}

}
