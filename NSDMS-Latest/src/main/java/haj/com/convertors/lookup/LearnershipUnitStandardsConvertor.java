package haj.com.convertors.lookup;

import haj.com.entity.lookup.LearnershipUnitStandards;
import haj.com.service.lookup.LearnershipUnitStandardsService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LearnershipUnitStandardsConvertor")
public class LearnershipUnitStandardsConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LearnershipUnitStandards
 	 * @author TechFinium 
 	 * @see    LearnershipUnitStandards
 	 * @return LearnershipUnitStandards
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LearnershipUnitStandardsService()
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
	 * Convert LearnershipUnitStandards key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LearnershipUnitStandards)arg2).getId();
	}

/*
       <p:selectOneMenu id="LearnershipUnitStandardsId" value="#{xxxUI.LearnershipUnitStandards.xxxType}" converter="LearnershipUnitStandardsConvertor" style="width:95%">
         <f:selectItems value="#{LearnershipUnitStandardsUI.LearnershipUnitStandardsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LearnershipUnitStandards" for="LearnershipUnitStandardsID"/>
        <p:autoComplete id="LearnershipUnitStandardsID" value="#{LearnershipUnitStandardsUI.LearnershipUnitStandards.municipality}" completeMethod="#{LearnershipUnitStandardsUI.completeLearnershipUnitStandards}"
                            var="rv" itemLabel="#{rv.LearnershipUnitStandardsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LearnershipUnitStandardsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LearnershipUnitStandards" style="white-space: nowrap">#{rv.LearnershipUnitStandardsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
