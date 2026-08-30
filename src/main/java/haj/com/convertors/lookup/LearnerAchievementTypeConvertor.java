package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.LearnerAchievementType;
import haj.com.service.lookup.LearnerAchievementTypeService;

// TODO: Auto-generated Javadoc
/**
 * The Class LearnerAchievementTypeConvertor.
 */
@FacesConverter(value = "LearnerAchievementTypeConvertor")
public class LearnerAchievementTypeConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LearnerAchievementType.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return LearnerAchievementType
	 * @see    LearnerAchievementType
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LearnerAchievementTypeService()
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
	 * Convert LearnerAchievementType key to String object.
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
		return ""+((LearnerAchievementType)arg2).getId();
	}

/*
       <p:selectOneMenu id="LearnerAchievementTypeId" value="#{xxxUI.LearnerAchievementType.xxxType}" converter="LearnerAchievementTypeConvertor" style="width:95%">
         <f:selectItems value="#{LearnerAchievementTypeUI.LearnerAchievementTypeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LearnerAchievementType" for="LearnerAchievementTypeID"/>
        <p:autoComplete id="LearnerAchievementTypeID" value="#{LearnerAchievementTypeUI.LearnerAchievementType.municipality}" completeMethod="#{LearnerAchievementTypeUI.completeLearnerAchievementType}"
                            var="rv" itemLabel="#{rv.LearnerAchievementTypeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LearnerAchievementTypeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LearnerAchievementType" style="white-space: nowrap">#{rv.LearnerAchievementTypeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
