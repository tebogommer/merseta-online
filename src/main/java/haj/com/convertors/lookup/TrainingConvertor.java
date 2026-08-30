package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.Training;
import haj.com.service.lookup.TrainingService;

@FacesConverter(value = "TrainingConvertor")
public class TrainingConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a Training
 	 * @author TechFinium 
 	 * @see    Training
 	 * @return Training
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new TrainingService()
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
	 * Convert Training key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((Training)arg2).getId();
	}

/*
       <p:selectOneMenu id="TrainingId" value="#{xxxUI.Training.xxxType}" converter="TrainingConvertor" style="width:95%">
         <f:selectItems value="#{TrainingUI.TrainingList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="Training" for="TrainingID"/>
        <p:autoComplete id="TrainingID" value="#{TrainingUI.Training.municipality}" completeMethod="#{TrainingUI.completeTraining}"
                            var="rv" itemLabel="#{rv.TrainingDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="TrainingConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="Training" style="white-space: nowrap">#{rv.TrainingDescription}</p:column>
       </p:autoComplete>         
       
*/

}
