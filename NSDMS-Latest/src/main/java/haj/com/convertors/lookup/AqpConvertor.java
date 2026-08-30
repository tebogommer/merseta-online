package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.Aqp;
import haj.com.service.lookup.AqpService;

@FacesConverter(value = "AqpConvertor")
public class AqpConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a Aqp
 	 * @author TechFinium 
 	 * @see    Aqp
 	 * @return Aqp
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new AqpService()
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
	 * Convert Aqp key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((Aqp)arg2).getId();
	}

/*
       <p:selectOneMenu id="AqpId" value="#{xxxUI.Aqp.xxxType}" converter="AqpConvertor" style="width:95%">
         <f:selectItems value="#{AqpUI.AqpList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="Aqp" for="AqpID"/>
        <p:autoComplete id="AqpID" value="#{AqpUI.Aqp.municipality}" completeMethod="#{AqpUI.completeAqp}"
                            var="rv" itemLabel="#{rv.AqpDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="AqpConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="Aqp" style="white-space: nowrap">#{rv.AqpDescription}</p:column>
       </p:autoComplete>         
       
*/

}
