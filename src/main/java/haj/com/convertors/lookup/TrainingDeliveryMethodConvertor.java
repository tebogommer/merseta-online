package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.TrainingDeliveryMethod;
import haj.com.service.lookup.TrainingDeliveryMethodService;

@FacesConverter(value = "TrainingDeliveryMethodConvertor")
public class TrainingDeliveryMethodConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a TrainingDeliveryMethod
 	 * @author TechFinium 
 	 * @see    TrainingDeliveryMethod
 	 * @return TrainingDeliveryMethod
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new TrainingDeliveryMethodService()
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
	 * Convert TrainingDeliveryMethod key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((TrainingDeliveryMethod)arg2).getId();
	}

/*
       <p:selectOneMenu id="TrainingDeliveryMethodId" value="#{xxxUI.TrainingDeliveryMethod.xxxType}" converter="TrainingDeliveryMethodConvertor" style="width:95%">
         <f:selectItems value="#{TrainingDeliveryMethodUI.TrainingDeliveryMethodList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="TrainingDeliveryMethod" for="TrainingDeliveryMethodID"/>
        <p:autoComplete id="TrainingDeliveryMethodID" value="#{TrainingDeliveryMethodUI.TrainingDeliveryMethod.municipality}" completeMethod="#{TrainingDeliveryMethodUI.completeTrainingDeliveryMethod}"
                            var="rv" itemLabel="#{rv.TrainingDeliveryMethodDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="TrainingDeliveryMethodConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="TrainingDeliveryMethod" style="white-space: nowrap">#{rv.TrainingDeliveryMethodDescription}</p:column>
       </p:autoComplete>         
       
*/

}
