package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.VacancyReasons;
import haj.com.service.lookup.VacancyReasonsService;

@FacesConverter(value = "VacancyReasonsConvertor")
public class VacancyReasonsConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a VacancyReasons
 	 * @author TechFinium 
 	 * @see    VacancyReasons
 	 * @return VacancyReasons
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new VacancyReasonsService()
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
	 * Convert VacancyReasons key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((VacancyReasons)arg2).getId();
	}

/*
       <p:selectOneMenu id="VacancyReasonsId" value="#{xxxUI.VacancyReasons.xxxType}" converter="VacancyReasonsConvertor" style="width:95%">
         <f:selectItems value="#{VacancyReasonsUI.VacancyReasonsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="VacancyReasons" for="VacancyReasonsID"/>
        <p:autoComplete id="VacancyReasonsID" value="#{VacancyReasonsUI.VacancyReasons.municipality}" completeMethod="#{VacancyReasonsUI.completeVacancyReasons}"
                            var="rv" itemLabel="#{rv.VacancyReasonsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="VacancyReasonsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="VacancyReasons" style="white-space: nowrap">#{rv.VacancyReasonsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
