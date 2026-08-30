package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.TrainingComittee;
import haj.com.service.TrainingComitteeService;

// TODO: Auto-generated Javadoc
/**
 * The Class TrainingComitteeConvertor.
 */
@FacesConverter(value = "TrainingComitteeConvertor")
public class TrainingComitteeConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a TrainingComittee.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return TrainingComittee
	 * @see    TrainingComittee
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new TrainingComitteeService()
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
	 * Convert TrainingComittee key to String object.
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
		return ""+((TrainingComittee)arg2).getId();
	}

/*
       <p:selectOneMenu id="TrainingComitteeId" value="#{xxxUI.TrainingComittee.xxxType}" converter="TrainingComitteeConvertor" style="width:95%">
         <f:selectItems value="#{TrainingComitteeUI.TrainingComitteeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="TrainingComittee" for="TrainingComitteeID"/>
        <p:autoComplete id="TrainingComitteeID" value="#{TrainingComitteeUI.TrainingComittee.municipality}" completeMethod="#{TrainingComitteeUI.completeTrainingComittee}"
                            var="rv" itemLabel="#{rv.TrainingComitteeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="TrainingComitteeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="TrainingComittee" style="white-space: nowrap">#{rv.TrainingComitteeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
