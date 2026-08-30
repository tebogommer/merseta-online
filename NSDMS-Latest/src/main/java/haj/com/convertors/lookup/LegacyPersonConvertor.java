package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacyPerson;
import haj.com.service.lookup.LegacyPersonService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacyPersonConvertor")
public class LegacyPersonConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacyPerson
 	 * @author TechFinium 
 	 * @see    LegacyPerson
 	 * @return LegacyPerson
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacyPersonService()
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
	 * Convert LegacyPerson key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacyPerson)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacyPersonId" value="#{xxxUI.LegacyPerson.xxxType}" converter="LegacyPersonConvertor" style="width:95%">
         <f:selectItems value="#{LegacyPersonUI.LegacyPersonList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacyPerson" for="LegacyPersonID"/>
        <p:autoComplete id="LegacyPersonID" value="#{LegacyPersonUI.LegacyPerson.municipality}" completeMethod="#{LegacyPersonUI.completeLegacyPerson}"
                            var="rv" itemLabel="#{rv.LegacyPersonDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacyPersonConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacyPerson" style="white-space: nowrap">#{rv.LegacyPersonDescription}</p:column>
       </p:autoComplete>         
       
*/

}
