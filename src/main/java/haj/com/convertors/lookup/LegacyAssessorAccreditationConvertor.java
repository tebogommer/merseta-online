package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyAssessorAccreditation;
import haj.com.service.lookup.LegacyAssessorAccreditationService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyAssessorAccreditationConvertor")
public class LegacyAssessorAccreditationConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyAssessorAccreditation
 	 * @author TechFinium 
 	 * @see    LegacyAssessorAccreditation
 	 * @return LegacyAssessorAccreditation
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyAssessorAccreditationService()
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
	 * Convert LegacyAssessorAccreditation key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyAssessorAccreditation)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyAssessorAccreditationId" value="#{xxxUI.LegacyAssessorAccreditation.xxxType}" converter="LegacyAssessorAccreditationConvertor" style="width:95%">
         <f:selectItems value="#{LegacyAssessorAccreditationUI.LegacyAssessorAccreditationList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyAssessorAccreditation" for="LegacyAssessorAccreditationID"/>
        <p:autoComplete id="LegacyAssessorAccreditationID" value="#{LegacyAssessorAccreditationUI.LegacyAssessorAccreditation.municipality}" completeMethod="#{LegacyAssessorAccreditationUI.completeLegacyAssessorAccreditation}"
                            var="rv" itemLabel="#{rv.LegacyAssessorAccreditationDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyAssessorAccreditationConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyAssessorAccreditation" style="white-space: nowrap">#{rv.LegacyAssessorAccreditationDescription}</p:column>
       </p:autoComplete>         
       
*/

}
