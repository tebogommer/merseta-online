package haj.com.convertors;

import haj.com.entity.StrategicPriorities;
import haj.com.service.StrategicPrioritiesService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "StrategicPrioritiesConvertor")
public class StrategicPrioritiesConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a StrategicPriorities
 	 * @author TechFinium 
 	 * @see    StrategicPriorities
 	 * @return StrategicPriorities
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new StrategicPrioritiesService()
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
	 * Convert StrategicPriorities key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((StrategicPriorities)arg2).getId();
	}

/*
       <p:selectOneMenu id="StrategicPrioritiesId" value="#{xxxUI.StrategicPriorities.xxxType}" converter="StrategicPrioritiesConvertor" style="width:95%">
         <f:selectItems value="#{StrategicPrioritiesUI.StrategicPrioritiesList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="StrategicPriorities" for="StrategicPrioritiesID"/>
        <p:autoComplete id="StrategicPrioritiesID" value="#{StrategicPrioritiesUI.StrategicPriorities.municipality}" completeMethod="#{StrategicPrioritiesUI.completeStrategicPriorities}"
                            var="rv" itemLabel="#{rv.StrategicPrioritiesDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="StrategicPrioritiesConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="StrategicPriorities" style="white-space: nowrap">#{rv.StrategicPrioritiesDescription}</p:column>
       </p:autoComplete>         
       
*/

}
