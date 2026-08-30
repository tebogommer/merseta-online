package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.SaqaUsQualification;
import haj.com.service.lookup.SaqaUsQualificationService;

@FacesConverter(value = "SaqaUsQualificationConvertor")
public class SaqaUsQualificationConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SaqaUsQualification
 	 * @author TechFinium 
 	 * @see    SaqaUsQualification
 	 * @return SaqaUsQualification
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SaqaUsQualificationService()
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
	 * Convert SaqaUsQualification key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((SaqaUsQualification)arg2).getId();
	}

/*
       <p:selectOneMenu id="SaqaUsQualificationId" value="#{xxxUI.SaqaUsQualification.xxxType}" converter="SaqaUsQualificationConvertor" style="width:95%">
         <f:selectItems value="#{SaqaUsQualificationUI.SaqaUsQualificationList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SaqaUsQualification" for="SaqaUsQualificationID"/>
        <p:autoComplete id="SaqaUsQualificationID" value="#{SaqaUsQualificationUI.SaqaUsQualification.municipality}" completeMethod="#{SaqaUsQualificationUI.completeSaqaUsQualification}"
                            var="rv" itemLabel="#{rv.SaqaUsQualificationDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SaqaUsQualificationConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SaqaUsQualification" style="white-space: nowrap">#{rv.SaqaUsQualificationDescription}</p:column>
       </p:autoComplete>         
       
*/

}
