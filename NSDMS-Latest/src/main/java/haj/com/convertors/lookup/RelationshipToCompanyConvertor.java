package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.RelationshipToCompany;
import haj.com.service.lookup.RelationshipToCompanyService;

@FacesConverter(value = "RelationshipToCompanyConvertor")
public class RelationshipToCompanyConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a RelationshipToCompany
 	 * @author TechFinium 
 	 * @see    RelationshipToCompany
 	 * @return RelationshipToCompany
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new RelationshipToCompanyService()
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
	 * Convert RelationshipToCompany key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((RelationshipToCompany)arg2).getId();
	}

/*
       <p:selectOneMenu id="RelationshipToCompanyId" value="#{xxxUI.RelationshipToCompany.xxxType}" converter="RelationshipToCompanyConvertor" style="width:95%">
         <f:selectItems value="#{RelationshipToCompanyUI.RelationshipToCompanyList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="RelationshipToCompany" for="RelationshipToCompanyID"/>
        <p:autoComplete id="RelationshipToCompanyID" value="#{RelationshipToCompanyUI.RelationshipToCompany.municipality}" completeMethod="#{RelationshipToCompanyUI.completeRelationshipToCompany}"
                            var="rv" itemLabel="#{rv.RelationshipToCompanyDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="RelationshipToCompanyConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="RelationshipToCompany" style="white-space: nowrap">#{rv.RelationshipToCompanyDescription}</p:column>
       </p:autoComplete>         
       
*/

}
