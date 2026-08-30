package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.InterventionLevel;
import haj.com.service.lookup.InterventionLevelService;

// TODO: Auto-generated Javadoc
/**
 * The Class InterventionLevelConvertor.
 */
@FacesConverter(value = "InterventionLevelConvertor")
public class InterventionLevelConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a InterventionLevel.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return InterventionLevel
	 * @see    InterventionLevel
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new InterventionLevelService()
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
	 * Convert InterventionLevel key to String object.
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
		if (arg2 == null) {
			return "";
		}
		return ""+((InterventionLevel)arg2).getId();
	}

/*
       <p:selectOneMenu id="InterventionLevelId" value="#{xxxUI.InterventionLevel.xxxType}" converter="InterventionLevelConvertor" style="width:95%">
         <f:selectItems value="#{InterventionLevelUI.InterventionLevelList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="InterventionLevel" for="InterventionLevelID"/>
        <p:autoComplete id="InterventionLevelID" value="#{InterventionLevelUI.InterventionLevel.municipality}" completeMethod="#{InterventionLevelUI.completeInterventionLevel}"
                            var="rv" itemLabel="#{rv.InterventionLevelDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="InterventionLevelConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="InterventionLevel" style="white-space: nowrap">#{rv.InterventionLevelDescription}</p:column>
       </p:autoComplete>         
       
*/

}
