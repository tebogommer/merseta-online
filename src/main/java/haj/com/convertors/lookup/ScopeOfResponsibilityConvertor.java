package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.ScopeOfResponsibility;
import haj.com.service.lookup.ScopeOfResponsibilityService;

@FacesConverter(value = "ScopeOfResponsibilityConvertor")
public class ScopeOfResponsibilityConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a ScopeOfResponsibility
 	 * @author TechFinium 
 	 * @see    ScopeOfResponsibility
 	 * @return ScopeOfResponsibility
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new ScopeOfResponsibilityService()
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
	 * Convert ScopeOfResponsibility key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((ScopeOfResponsibility)arg2).getId();
	}

/*
       <p:selectOneMenu id="ScopeOfResponsibilityId" value="#{xxxUI.ScopeOfResponsibility.xxxType}" converter="ScopeOfResponsibilityConvertor" style="width:95%">
         <f:selectItems value="#{ScopeOfResponsibilityUI.ScopeOfResponsibilityList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="ScopeOfResponsibility" for="ScopeOfResponsibilityID"/>
        <p:autoComplete id="ScopeOfResponsibilityID" value="#{ScopeOfResponsibilityUI.ScopeOfResponsibility.municipality}" completeMethod="#{ScopeOfResponsibilityUI.completeScopeOfResponsibility}"
                            var="rv" itemLabel="#{rv.ScopeOfResponsibilityDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="ScopeOfResponsibilityConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="ScopeOfResponsibility" style="white-space: nowrap">#{rv.ScopeOfResponsibilityDescription}</p:column>
       </p:autoComplete>         
       
*/

}
