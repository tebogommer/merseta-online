package haj.com.convertors;

import haj.com.entity.WspStrategicPriorities;
import haj.com.service.WspStrategicPrioritiesService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "WspStrategicPrioritiesConvertor")
public class WspStrategicPrioritiesConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a WspStrategicPriorities
 	 * @author TechFinium 
 	 * @see    WspStrategicPriorities
 	 * @return WspStrategicPriorities
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new WspStrategicPrioritiesService()
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
	 * Convert WspStrategicPriorities key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((WspStrategicPriorities)arg2).getId();
	}

/*
       <p:selectOneMenu id="WspStrategicPrioritiesId" value="#{xxxUI.WspStrategicPriorities.xxxType}" converter="WspStrategicPrioritiesConvertor" style="width:95%">
         <f:selectItems value="#{WspStrategicPrioritiesUI.WspStrategicPrioritiesList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="WspStrategicPriorities" for="WspStrategicPrioritiesID"/>
        <p:autoComplete id="WspStrategicPrioritiesID" value="#{WspStrategicPrioritiesUI.WspStrategicPriorities.municipality}" completeMethod="#{WspStrategicPrioritiesUI.completeWspStrategicPriorities}"
                            var="rv" itemLabel="#{rv.WspStrategicPrioritiesDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="WspStrategicPrioritiesConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="WspStrategicPriorities" style="white-space: nowrap">#{rv.WspStrategicPrioritiesDescription}</p:column>
       </p:autoComplete>         
       
*/

}
