package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.Language;
import haj.com.service.lookup.LanguageService;

// TODO: Auto-generated Javadoc
/**
 * The Class LanguageConvertor.
 */
@FacesConverter(value = "LanguageConvertor")
public class LanguageConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a Language.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return Language
	 * @see    Language
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LanguageService()
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
	 * Convert Language key to String object.
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
		return ""+((Language)arg2).getId();
	}

/*
       <p:selectOneMenu id="LanguageId" value="#{xxxUI.Language.xxxType}" converter="LanguageConvertor" style="width:95%">
         <f:selectItems value="#{LanguageUI.LanguageList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="Language" for="LanguageID"/>
        <p:autoComplete id="LanguageID" value="#{LanguageUI.Language.municipality}" completeMethod="#{LanguageUI.completeLanguage}"
                            var="rv" itemLabel="#{rv.LanguageDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LanguageConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="Language" style="white-space: nowrap">#{rv.LanguageDescription}</p:column>
       </p:autoComplete>         
       
*/

}
