package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.Institution;
import haj.com.service.lookup.InstitutionService;

// TODO: Auto-generated Javadoc
/**
 * The Class InstitutionConvertor.
 */
@FacesConverter(value = "InstitutionConvertor")
public class InstitutionConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a Institution.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return Institution
	 * @see    Institution
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new InstitutionService()
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
	 * Convert Institution key to String object.
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
		return ""+((Institution)arg2).getId();
	}

/*
       <p:selectOneMenu id="InstitutionId" value="#{xxxUI.Institution.xxxType}" converter="InstitutionConvertor" style="width:95%">
         <f:selectItems value="#{InstitutionUI.InstitutionList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="Institution" for="InstitutionID"/>
        <p:autoComplete id="InstitutionID" value="#{InstitutionUI.Institution.municipality}" completeMethod="#{InstitutionUI.completeInstitution}"
                            var="rv" itemLabel="#{rv.InstitutionDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="InstitutionConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="Institution" style="white-space: nowrap">#{rv.InstitutionDescription}</p:column>
       </p:autoComplete>         
       
*/

}
