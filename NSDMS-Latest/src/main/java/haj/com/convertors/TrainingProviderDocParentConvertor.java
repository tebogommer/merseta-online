package haj.com.convertors;

import haj.com.entity.TrainingProviderDocParent;
import haj.com.service.TrainingProviderDocParentService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "TrainingProviderDocParentConvertor")
public class TrainingProviderDocParentConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a TrainingProviderDocParent
 	 * @author TechFinium 
 	 * @see    TrainingProviderDocParent
 	 * @return TrainingProviderDocParent
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new TrainingProviderDocParentService()
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
	 * Convert TrainingProviderDocParent key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((TrainingProviderDocParent)arg2).getId();
	}

/*
       <p:selectOneMenu id="TrainingProviderDocParentId" value="#{xxxUI.TrainingProviderDocParent.xxxType}" converter="TrainingProviderDocParentConvertor" style="width:95%">
         <f:selectItems value="#{TrainingProviderDocParentUI.TrainingProviderDocParentList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="TrainingProviderDocParent" for="TrainingProviderDocParentID"/>
        <p:autoComplete id="TrainingProviderDocParentID" value="#{TrainingProviderDocParentUI.TrainingProviderDocParent.municipality}" completeMethod="#{TrainingProviderDocParentUI.completeTrainingProviderDocParent}"
                            var="rv" itemLabel="#{rv.TrainingProviderDocParentDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="TrainingProviderDocParentConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="TrainingProviderDocParent" style="white-space: nowrap">#{rv.TrainingProviderDocParentDescription}</p:column>
       </p:autoComplete>         
       
*/

}
