package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.OrganisationType;
import haj.com.service.lookup.OrganisationTypeService;

@FacesConverter(value = "OrganisationTypeConvertor")
public class OrganisationTypeConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a OrganisationType
 	 * @author TechFinium 
 	 * @see    OrganisationType
 	 * @return OrganisationType
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new OrganisationTypeService()
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
	 * Convert OrganisationType key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((OrganisationType)arg2).getId();
	}

/*
       <p:selectOneMenu id="OrganisationTypeId" value="#{xxxUI.OrganisationType.xxxType}" converter="OrganisationTypeConvertor" style="width:95%">
         <f:selectItems value="#{OrganisationTypeUI.OrganisationTypeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="OrganisationType" for="OrganisationTypeID"/>
        <p:autoComplete id="OrganisationTypeID" value="#{OrganisationTypeUI.OrganisationType.municipality}" completeMethod="#{OrganisationTypeUI.completeOrganisationType}"
                            var="rv" itemLabel="#{rv.OrganisationTypeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="OrganisationTypeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="OrganisationType" style="white-space: nowrap">#{rv.OrganisationTypeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
