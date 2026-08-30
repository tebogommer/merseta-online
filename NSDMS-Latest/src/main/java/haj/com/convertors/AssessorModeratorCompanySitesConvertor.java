package haj.com.convertors;

import haj.com.entity.AssessorModeratorCompanySites;
import haj.com.service.AssessorModeratorCompanySitesService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "AssessorModeratorCompanySitesConvertor")
public class AssessorModeratorCompanySitesConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a AssessorModeratorCompanySites
 	 * @author TechFinium 
 	 * @see    AssessorModeratorCompanySites
 	 * @return AssessorModeratorCompanySites
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new AssessorModeratorCompanySitesService()
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
	 * Convert AssessorModeratorCompanySites key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((AssessorModeratorCompanySites)arg2).getId();
	}

/*
       <p:selectOneMenu id="AssessorModeratorCompanySitesId" value="#{xxxUI.AssessorModeratorCompanySites.xxxType}" converter="AssessorModeratorCompanySitesConvertor" style="width:95%">
         <f:selectItems value="#{AssessorModeratorCompanySitesUI.AssessorModeratorCompanySitesList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="AssessorModeratorCompanySites" for="AssessorModeratorCompanySitesID"/>
        <p:autoComplete id="AssessorModeratorCompanySitesID" value="#{AssessorModeratorCompanySitesUI.AssessorModeratorCompanySites.municipality}" completeMethod="#{AssessorModeratorCompanySitesUI.completeAssessorModeratorCompanySites}"
                            var="rv" itemLabel="#{rv.AssessorModeratorCompanySitesDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="AssessorModeratorCompanySitesConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="AssessorModeratorCompanySites" style="white-space: nowrap">#{rv.AssessorModeratorCompanySitesDescription}</p:column>
       </p:autoComplete>         
       
*/

}
