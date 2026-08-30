package haj.com.convertors.lookup;

import haj.com.entity.lookup.LegacySECTTwentyEight;
import haj.com.service.lookup.LegacySECTTwentyEightService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LegacySECTTwentyEightConvertor")
public class LegacySECTTwentyEightConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LegacySECTTwentyEight
 	 * @author TechFinium 
 	 * @see    LegacySECTTwentyEight
 	 * @return LegacySECTTwentyEight
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LegacySECTTwentyEightService()
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
	 * Convert LegacySECTTwentyEight key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LegacySECTTwentyEight)arg2).getId();
	}

/*
       <p:selectOneMenu id="LegacySECTTwentyEightId" value="#{xxxUI.LegacySECTTwentyEight.xxxType}" converter="LegacySECTTwentyEightConvertor" style="width:95%">
         <f:selectItems value="#{LegacySECTTwentyEightUI.LegacySECTTwentyEightList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LegacySECTTwentyEight" for="LegacySECTTwentyEightID"/>
        <p:autoComplete id="LegacySECTTwentyEightID" value="#{LegacySECTTwentyEightUI.LegacySECTTwentyEight.municipality}" completeMethod="#{LegacySECTTwentyEightUI.completeLegacySECTTwentyEight}"
                            var="rv" itemLabel="#{rv.LegacySECTTwentyEightDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LegacySECTTwentyEightConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LegacySECTTwentyEight" style="white-space: nowrap">#{rv.LegacySECTTwentyEightDescription}</p:column>
       </p:autoComplete>         
       
*/

}
