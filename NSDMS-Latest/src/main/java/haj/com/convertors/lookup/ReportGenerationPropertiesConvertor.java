package haj.com.convertors.lookup;

import haj.com.entity.lookup.ReportGenerationProperties;
import haj.com.service.lookup.ReportGenerationPropertiesService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "ReportGenerationPropertiesConvertor")
public class ReportGenerationPropertiesConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a ReportGenerationProperties
 	 * @author TechFinium 
 	 * @see    ReportGenerationProperties
 	 * @return ReportGenerationProperties
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new ReportGenerationPropertiesService()
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
	 * Convert ReportGenerationProperties key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((ReportGenerationProperties)arg2).getId();
	}

/*
       <p:selectOneMenu id="ReportGenerationPropertiesId" value="#{xxxUI.ReportGenerationProperties.xxxType}" converter="ReportGenerationPropertiesConvertor" style="width:95%">
         <f:selectItems value="#{ReportGenerationPropertiesUI.ReportGenerationPropertiesList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="ReportGenerationProperties" for="ReportGenerationPropertiesID"/>
        <p:autoComplete id="ReportGenerationPropertiesID" value="#{ReportGenerationPropertiesUI.ReportGenerationProperties.municipality}" completeMethod="#{ReportGenerationPropertiesUI.completeReportGenerationProperties}"
                            var="rv" itemLabel="#{rv.ReportGenerationPropertiesDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="ReportGenerationPropertiesConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="ReportGenerationProperties" style="white-space: nowrap">#{rv.ReportGenerationPropertiesDescription}</p:column>
       </p:autoComplete>         
       
*/

}
