package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.LearnerAchievementStatus;
import haj.com.service.lookup.LearnerAchievementStatusService;

// TODO: Auto-generated Javadoc
/**
 * The Class LearnerAchievementStatusConvertor.
 */
@FacesConverter(value = "LearnerAchievementStatusConvertor")
public class LearnerAchievementStatusConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LearnerAchievementStatus.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return LearnerAchievementStatus
	 * @see    LearnerAchievementStatus
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LearnerAchievementStatusService()
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
	 * Convert LearnerAchievementStatus key to String object.
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
		return ""+((LearnerAchievementStatus)arg2).getId();
	}

/*
       <p:selectOneMenu id="LearnerAchievementStatusId" value="#{xxxUI.LearnerAchievementStatus.xxxType}" converter="LearnerAchievementStatusConvertor" style="width:95%">
         <f:selectItems value="#{LearnerAchievementStatusUI.LearnerAchievementStatusList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LearnerAchievementStatus" for="LearnerAchievementStatusID"/>
        <p:autoComplete id="LearnerAchievementStatusID" value="#{LearnerAchievementStatusUI.LearnerAchievementStatus.municipality}" completeMethod="#{LearnerAchievementStatusUI.completeLearnerAchievementStatus}"
                            var="rv" itemLabel="#{rv.LearnerAchievementStatusDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LearnerAchievementStatusConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LearnerAchievementStatus" style="white-space: nowrap">#{rv.LearnerAchievementStatusDescription}</p:column>
       </p:autoComplete>         
       
*/

}
