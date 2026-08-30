package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.Town;
import haj.com.service.lookup.TownService;

@FacesConverter(value = "TownConvertor")
public class TownConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a Town
 	 * @author TechFinium 
 	 * @see    Town
 	 * @return Town
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new TownService()
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
	 * Convert Town key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((Town)arg2).getId();
	}

/*
       <p:selectOneMenu id="TownId" value="#{xxxUI.Town.xxxType}" converter="TownConvertor" style="width:95%">
         <f:selectItems value="#{TownUI.TownList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="Town" for="TownID"/>
        <p:autoComplete id="TownID" value="#{TownUI.Town.municipality}" completeMethod="#{TownUI.completeTown}"
                            var="rv" itemLabel="#{rv.TownDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="TownConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="Town" style="white-space: nowrap">#{rv.TownDescription}</p:column>
       </p:autoComplete>         
       
*/

}
