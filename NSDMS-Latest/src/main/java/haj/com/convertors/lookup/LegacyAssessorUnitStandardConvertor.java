package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyAssessorUnitStandard;
import haj.com.service.lookup.LegacyAssessorUnitStandardService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyAssessorUnitStandardConvertor")
public class LegacyAssessorUnitStandardConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyAssessorUnitStandard
 	 * @author TechFinium 
 	 * @see    LegacyAssessorUnitStandard
 	 * @return LegacyAssessorUnitStandard
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyAssessorUnitStandardService()
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
	 * Convert LegacyAssessorUnitStandard key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyAssessorUnitStandard)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyAssessorUnitStandardId" value="#{xxxUI.LegacyAssessorUnitStandard.xxxType}" converter="LegacyAssessorUnitStandardConvertor" style="width:95%">
         <f:selectItems value="#{LegacyAssessorUnitStandardUI.LegacyAssessorUnitStandardList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyAssessorUnitStandard" for="LegacyAssessorUnitStandardID"/>
        <p:autoComplete id="LegacyAssessorUnitStandardID" value="#{LegacyAssessorUnitStandardUI.LegacyAssessorUnitStandard.municipality}" completeMethod="#{LegacyAssessorUnitStandardUI.completeLegacyAssessorUnitStandard}"
                            var="rv" itemLabel="#{rv.LegacyAssessorUnitStandardDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyAssessorUnitStandardConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyAssessorUnitStandard" style="white-space: nowrap">#{rv.LegacyAssessorUnitStandardDescription}</p:column>
       </p:autoComplete>         
       
*/

}
