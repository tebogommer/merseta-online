package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.Learners;
import haj.com.service.LearnersService;

@FacesConverter(value = "LearnersConvertor")
public class LearnersConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a Learners
 	 * @author TechFinium 
 	 * @see    Learners
 	 * @return Learners
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LearnersService()
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
	 * Convert Learners key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return ""+((Learners)arg2).getId();
	}

/*
       <p:selectOneMenu id="LearnersId" value="#{xxxUI.Learners.xxxType}" converter="LearnersConvertor" style="width:95%">
         <f:selectItems value="#{LearnersUI.LearnersList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="Learners" for="LearnersID"/>
        <p:autoComplete id="LearnersID" value="#{LearnersUI.Learners.municipality}" completeMethod="#{LearnersUI.completeLearners}"
                            var="rv" itemLabel="#{rv.LearnersDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LearnersConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="Learners" style="white-space: nowrap">#{rv.LearnersDescription}</p:column>
       </p:autoComplete>         
       
*/

}
