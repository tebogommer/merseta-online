package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.TempSignoff;
import haj.com.service.TempSignoffService;

@FacesConverter(value = "TempSignoffConvertor")
public class TempSignoffConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a TempSignoff
 	 * @author TechFinium 
 	 * @see    TempSignoff
 	 * @return TempSignoff
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new TempSignoffService()
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
	 * Convert TempSignoff key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((TempSignoff)arg2).getId();
	}

/*
       <p:selectOneMenu id="TempSignoffId" value="#{xxxUI.TempSignoff.xxxType}" converter="TempSignoffConvertor" style="width:95%">
         <f:selectItems value="#{TempSignoffUI.TempSignoffList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="TempSignoff" for="TempSignoffID"/>
        <p:autoComplete id="TempSignoffID" value="#{TempSignoffUI.TempSignoff.municipality}" completeMethod="#{TempSignoffUI.completeTempSignoff}"
                            var="rv" itemLabel="#{rv.TempSignoffDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="TempSignoffConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="TempSignoff" style="white-space: nowrap">#{rv.TempSignoffDescription}</p:column>
       </p:autoComplete>         
       
*/

}
