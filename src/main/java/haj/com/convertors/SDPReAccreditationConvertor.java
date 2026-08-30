package haj.com.convertors;

import haj.com.entity.SDPReAccreditation;
import haj.com.service.SDPReAccreditationService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "SDPReAccreditationConvertor")
public class SDPReAccreditationConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SDPReAccreditation
 	 * @author TechFinium 
 	 * @see    SDPReAccreditation
 	 * @return SDPReAccreditation
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SDPReAccreditationService()
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
	 * Convert SDPReAccreditation key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((SDPReAccreditation)arg2).getId();
	}

/*
       <p:selectOneMenu id="SDPReAccreditationId" value="#{xxxUI.SDPReAccreditation.xxxType}" converter="SDPReAccreditationConvertor" style="width:95%">
         <f:selectItems value="#{SDPReAccreditationUI.SDPReAccreditationList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SDPReAccreditation" for="SDPReAccreditationID"/>
        <p:autoComplete id="SDPReAccreditationID" value="#{SDPReAccreditationUI.SDPReAccreditation.municipality}" completeMethod="#{SDPReAccreditationUI.completeSDPReAccreditation}"
                            var="rv" itemLabel="#{rv.SDPReAccreditationDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SDPReAccreditationConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SDPReAccreditation" style="white-space: nowrap">#{rv.SDPReAccreditationDescription}</p:column>
       </p:autoComplete>         
       
*/

}
