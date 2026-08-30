package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.Ratio;
import haj.com.service.lookup.RatioService;

@FacesConverter(value = "RatioConvertor")
public class RatioConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a Ratio
 	 * @author TechFinium 
 	 * @see    Ratio
 	 * @return Ratio
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new RatioService()
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
	 * Convert Ratio key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((Ratio)arg2).getId();
	}

/*
       <p:selectOneMenu id="RatioId" value="#{xxxUI.Ratio.xxxType}" converter="RatioConvertor" style="width:95%">
         <f:selectItems value="#{RatioUI.RatioList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="Ratio" for="RatioID"/>
        <p:autoComplete id="RatioID" value="#{RatioUI.Ratio.municipality}" completeMethod="#{RatioUI.completeRatio}"
                            var="rv" itemLabel="#{rv.RatioDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="RatioConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="Ratio" style="white-space: nowrap">#{rv.RatioDescription}</p:column>
       </p:autoComplete>         
       
*/

}
