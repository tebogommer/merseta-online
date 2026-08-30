package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.NoHardToFillVacancies;
import haj.com.service.lookup.NoHardToFillVacanciesService;

@FacesConverter(value = "NoHardToFillVacanciesConvertor")
public class NoHardToFillVacanciesConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a NoHardToFillVacancies
 	 * @author TechFinium 
 	 * @see    NoHardToFillVacancies
 	 * @return NoHardToFillVacancies
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new NoHardToFillVacanciesService()
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
	 * Convert NoHardToFillVacancies key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((NoHardToFillVacancies)arg2).getId();
	}

/*
       <p:selectOneMenu id="NoHardToFillVacanciesId" value="#{xxxUI.NoHardToFillVacancies.xxxType}" converter="NoHardToFillVacanciesConvertor" style="width:95%">
         <f:selectItems value="#{NoHardToFillVacanciesUI.NoHardToFillVacanciesList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="NoHardToFillVacancies" for="NoHardToFillVacanciesID"/>
        <p:autoComplete id="NoHardToFillVacanciesID" value="#{NoHardToFillVacanciesUI.NoHardToFillVacancies.municipality}" completeMethod="#{NoHardToFillVacanciesUI.completeNoHardToFillVacancies}"
                            var="rv" itemLabel="#{rv.NoHardToFillVacanciesDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="NoHardToFillVacanciesConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="NoHardToFillVacancies" style="white-space: nowrap">#{rv.NoHardToFillVacanciesDescription}</p:column>
       </p:autoComplete>         
       
*/

}
