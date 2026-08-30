package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.PrioritisationScale;
import haj.com.service.lookup.PrioritisationScaleService;

@FacesConverter(value = "PrioritisationScaleConvertor")
public class PrioritisationScaleConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a PrioritisationScale
 	 * @author TechFinium 
 	 * @see    PrioritisationScale
 	 * @return PrioritisationScale
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new PrioritisationScaleService()
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
	 * Convert PrioritisationScale key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((PrioritisationScale)arg2).getId();
	}

/*
       <p:selectOneMenu id="PrioritisationScaleId" value="#{xxxUI.PrioritisationScale.xxxType}" converter="PrioritisationScaleConvertor" style="width:95%">
         <f:selectItems value="#{PrioritisationScaleUI.PrioritisationScaleList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="PrioritisationScale" for="PrioritisationScaleID"/>
        <p:autoComplete id="PrioritisationScaleID" value="#{PrioritisationScaleUI.PrioritisationScale.municipality}" completeMethod="#{PrioritisationScaleUI.completePrioritisationScale}"
                            var="rv" itemLabel="#{rv.PrioritisationScaleDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="PrioritisationScaleConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="PrioritisationScale" style="white-space: nowrap">#{rv.PrioritisationScaleDescription}</p:column>
       </p:autoComplete>         
       
*/

}
