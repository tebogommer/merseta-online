package haj.com.convertors.lookup;

import haj.com.entity.lookup.HomeAffairs;
import haj.com.service.lookup.HomeAffairsService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "HomeAffairsConvertor")
public class HomeAffairsConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a HomeAffairs
 	 * @author TechFinium 
 	 * @see    HomeAffairs
 	 * @return HomeAffairs
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new HomeAffairsService()
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
	 * Convert HomeAffairs key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((HomeAffairs)arg2).getId();
	}

/*
       <p:selectOneMenu id="HomeAffairsId" value="#{xxxUI.HomeAffairs.xxxType}" converter="HomeAffairsConvertor" style="width:95%">
         <f:selectItems value="#{HomeAffairsUI.HomeAffairsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="HomeAffairs" for="HomeAffairsID"/>
        <p:autoComplete id="HomeAffairsID" value="#{HomeAffairsUI.HomeAffairs.municipality}" completeMethod="#{HomeAffairsUI.completeHomeAffairs}"
                            var="rv" itemLabel="#{rv.HomeAffairsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="HomeAffairsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="HomeAffairs" style="white-space: nowrap">#{rv.HomeAffairsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
