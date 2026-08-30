package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.ImpactOfStaffTraining;
import haj.com.service.ImpactOfStaffTrainingService;

// TODO: Auto-generated Javadoc
/**
 * The Class ImpactOfStaffTrainingConvertor.
 */
@FacesConverter(value = "ImpactOfStaffTrainingConvertor")
public class ImpactOfStaffTrainingConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a ImpactOfStaffTraining.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return ImpactOfStaffTraining
	 * @see    ImpactOfStaffTraining
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new ImpactOfStaffTrainingService()
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
	 * Convert ImpactOfStaffTraining key to String object.
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
		return ""+((ImpactOfStaffTraining)arg2).getId();
	}

/*
       <p:selectOneMenu id="ImpactOfStaffTrainingId" value="#{xxxUI.ImpactOfStaffTraining.xxxType}" converter="ImpactOfStaffTrainingConvertor" style="width:95%">
         <f:selectItems value="#{ImpactOfStaffTrainingUI.ImpactOfStaffTrainingList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="ImpactOfStaffTraining" for="ImpactOfStaffTrainingID"/>
        <p:autoComplete id="ImpactOfStaffTrainingID" value="#{ImpactOfStaffTrainingUI.ImpactOfStaffTraining.municipality}" completeMethod="#{ImpactOfStaffTrainingUI.completeImpactOfStaffTraining}"
                            var="rv" itemLabel="#{rv.ImpactOfStaffTrainingDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="ImpactOfStaffTrainingConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="ImpactOfStaffTraining" style="white-space: nowrap">#{rv.ImpactOfStaffTrainingDescription}</p:column>
       </p:autoComplete>         
       
*/

}
