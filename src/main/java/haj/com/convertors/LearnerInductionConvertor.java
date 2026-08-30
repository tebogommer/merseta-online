package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.LearnerInduction;
import haj.com.service.LearnerInductionService;

@FacesConverter(value = "LearnerInductionConvertor")
public class LearnerInductionConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LearnerInduction
 	 * @author TechFinium 
 	 * @see    LearnerInduction
 	 * @return LearnerInduction
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LearnerInductionService()
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
	 * Convert LearnerInduction key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LearnerInduction)arg2).getId();
	}

/*
       <p:selectOneMenu id="LearnerInductionId" value="#{xxxUI.LearnerInduction.xxxType}" converter="LearnerInductionConvertor" style="width:95%">
         <f:selectItems value="#{LearnerInductionUI.LearnerInductionList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LearnerInduction" for="LearnerInductionID"/>
        <p:autoComplete id="LearnerInductionID" value="#{LearnerInductionUI.LearnerInduction.municipality}" completeMethod="#{LearnerInductionUI.completeLearnerInduction}"
                            var="rv" itemLabel="#{rv.LearnerInductionDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LearnerInductionConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LearnerInduction" style="white-space: nowrap">#{rv.LearnerInductionDescription}</p:column>
       </p:autoComplete>         
       
*/

}
