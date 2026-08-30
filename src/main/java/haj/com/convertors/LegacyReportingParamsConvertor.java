package haj.com.convertors;

import haj.com.entity.LegacyReportingParams;
import haj.com.service.LegacyReportingParamsService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyReportingParamsConvertor")
public class LegacyReportingParamsConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyReportingParams
 	 * @author TechFinium 
 	 * @see    LegacyReportingParams
 	 * @return LegacyReportingParams
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyReportingParamsService()
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
	 * Convert LegacyReportingParams key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyReportingParams)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyReportingParamsId" value="#{xxxUI.LegacyReportingParams.xxxType}" converter="LegacyReportingParamsConvertor" style="width:95%">
         <f:selectItems value="#{LegacyReportingParamsUI.LegacyReportingParamsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyReportingParams" for="LegacyReportingParamsID"/>
        <p:autoComplete id="LegacyReportingParamsID" value="#{LegacyReportingParamsUI.LegacyReportingParams.municipality}" completeMethod="#{LegacyReportingParamsUI.completeLegacyReportingParams}"
                            var="rv" itemLabel="#{rv.LegacyReportingParamsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyReportingParamsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyReportingParams" style="white-space: nowrap">#{rv.LegacyReportingParamsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
