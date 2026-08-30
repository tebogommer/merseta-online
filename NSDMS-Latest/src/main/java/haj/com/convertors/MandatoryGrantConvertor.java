package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.MandatoryGrant;
import haj.com.service.MandatoryGrantService;

@FacesConverter(value = "MandatoryGrantConvertor")
public class MandatoryGrantConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a MandatoryGrant
 	 * @author TechFinium 
 	 * @see    MandatoryGrant
 	 * @return MandatoryGrant
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new MandatoryGrantService()
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
	 * Convert MandatoryGrant key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((MandatoryGrant)arg2).getId();
	}

/*
       <p:selectOneMenu id="MandatoryGrantId" value="#{xxxUI.MandatoryGrant.xxxType}" converter="MandatoryGrantConvertor" style="width:95%">
         <f:selectItems value="#{MandatoryGrantUI.MandatoryGrantList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="MandatoryGrant" for="MandatoryGrantID"/>
        <p:autoComplete id="MandatoryGrantID" value="#{MandatoryGrantUI.MandatoryGrant.municipality}" completeMethod="#{MandatoryGrantUI.completeMandatoryGrant}"
                            var="rv" itemLabel="#{rv.MandatoryGrantDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="MandatoryGrantConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="MandatoryGrant" style="white-space: nowrap">#{rv.MandatoryGrantDescription}</p:column>
       </p:autoComplete>         
       
*/

}
