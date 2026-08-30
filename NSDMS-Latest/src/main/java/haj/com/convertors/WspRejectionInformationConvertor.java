package haj.com.convertors;

import haj.com.entity.WspRejectionInformation;
import haj.com.service.WspRejectionInformationService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "WspRejectionInformationConvertor")
public class WspRejectionInformationConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a WspRejectionInformation
 	 * @author TechFinium 
 	 * @see    WspRejectionInformation
 	 * @return WspRejectionInformation
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new WspRejectionInformationService()
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
	 * Convert WspRejectionInformation key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((WspRejectionInformation)arg2).getId();
	}

/*
       <p:selectOneMenu id="WspRejectionInformationId" value="#{xxxUI.WspRejectionInformation.xxxType}" converter="WspRejectionInformationConvertor" style="width:95%">
         <f:selectItems value="#{WspRejectionInformationUI.WspRejectionInformationList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="WspRejectionInformation" for="WspRejectionInformationID"/>
        <p:autoComplete id="WspRejectionInformationID" value="#{WspRejectionInformationUI.WspRejectionInformation.municipality}" completeMethod="#{WspRejectionInformationUI.completeWspRejectionInformation}"
                            var="rv" itemLabel="#{rv.WspRejectionInformationDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="WspRejectionInformationConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="WspRejectionInformation" style="white-space: nowrap">#{rv.WspRejectionInformationDescription}</p:column>
       </p:autoComplete>         
       
*/

}
