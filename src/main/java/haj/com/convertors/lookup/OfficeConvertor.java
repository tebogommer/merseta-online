package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.Office;
import haj.com.service.lookup.OfficeService;

@FacesConverter(value = "OfficeConvertor")
public class OfficeConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a Office
 	 * @author TechFinium 
 	 * @see    Office
 	 * @return Office
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new OfficeService()
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
	 * Convert Office key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((Office)arg2).getId();
	}

/*
       <p:selectOneMenu id="OfficeId" value="#{xxxUI.Office.xxxType}" converter="OfficeConvertor" style="width:95%">
         <f:selectItems value="#{OfficeUI.OfficeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="Office" for="OfficeID"/>
        <p:autoComplete id="OfficeID" value="#{OfficeUI.Office.municipality}" completeMethod="#{OfficeUI.completeOffice}"
                            var="rv" itemLabel="#{rv.OfficeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="OfficeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="Office" style="white-space: nowrap">#{rv.OfficeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
