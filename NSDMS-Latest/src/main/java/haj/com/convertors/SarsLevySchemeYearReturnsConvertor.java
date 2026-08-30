package haj.com.convertors;

import haj.com.entity.SarsLevySchemeYearReturns;
import haj.com.service.SarsLevySchemeYearReturnsService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "SarsLevySchemeYearReturnsConvertor")
public class SarsLevySchemeYearReturnsConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SarsLevySchemeYearReturns
 	 * @author TechFinium 
 	 * @see    SarsLevySchemeYearReturns
 	 * @return SarsLevySchemeYearReturns
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SarsLevySchemeYearReturnsService()
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
	 * Convert SarsLevySchemeYearReturns key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((SarsLevySchemeYearReturns)arg2).getId();
	}

/*
       <p:selectOneMenu id="SarsLevySchemeYearReturnsId" value="#{xxxUI.SarsLevySchemeYearReturns.xxxType}" converter="SarsLevySchemeYearReturnsConvertor" style="width:95%">
         <f:selectItems value="#{SarsLevySchemeYearReturnsUI.SarsLevySchemeYearReturnsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SarsLevySchemeYearReturns" for="SarsLevySchemeYearReturnsID"/>
        <p:autoComplete id="SarsLevySchemeYearReturnsID" value="#{SarsLevySchemeYearReturnsUI.SarsLevySchemeYearReturns.municipality}" completeMethod="#{SarsLevySchemeYearReturnsUI.completeSarsLevySchemeYearReturns}"
                            var="rv" itemLabel="#{rv.SarsLevySchemeYearReturnsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SarsLevySchemeYearReturnsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SarsLevySchemeYearReturns" style="white-space: nowrap">#{rv.SarsLevySchemeYearReturnsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
