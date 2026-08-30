package haj.com.convertors;

import haj.com.entity.SarsLevyDetailCalculation;
import haj.com.service.SarsLevyDetailCalculationService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "SarsLevyDetailCalculationConvertor")
public class SarsLevyDetailCalculationConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SarsLevyDetailCalculation
 	 * @author TechFinium 
 	 * @see    SarsLevyDetailCalculation
 	 * @return SarsLevyDetailCalculation
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SarsLevyDetailCalculationService()
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
	 * Convert SarsLevyDetailCalculation key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((SarsLevyDetailCalculation)arg2).getId();
	}

/*
       <p:selectOneMenu id="SarsLevyDetailCalculationId" value="#{xxxUI.SarsLevyDetailCalculation.xxxType}" converter="SarsLevyDetailCalculationConvertor" style="width:95%">
         <f:selectItems value="#{SarsLevyDetailCalculationUI.SarsLevyDetailCalculationList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SarsLevyDetailCalculation" for="SarsLevyDetailCalculationID"/>
        <p:autoComplete id="SarsLevyDetailCalculationID" value="#{SarsLevyDetailCalculationUI.SarsLevyDetailCalculation.municipality}" completeMethod="#{SarsLevyDetailCalculationUI.completeSarsLevyDetailCalculation}"
                            var="rv" itemLabel="#{rv.SarsLevyDetailCalculationDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SarsLevyDetailCalculationConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SarsLevyDetailCalculation" style="white-space: nowrap">#{rv.SarsLevyDetailCalculationDescription}</p:column>
       </p:autoComplete>         
       
*/

}
