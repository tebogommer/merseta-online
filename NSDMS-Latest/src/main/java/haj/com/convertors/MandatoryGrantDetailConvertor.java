package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.MandatoryGrantDetail;
import haj.com.service.MandatoryGrantDetailService;

@FacesConverter(value = "MandatoryGrantDetailConvertor")
public class MandatoryGrantDetailConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a MandatoryGrantDetail
 	 * @author TechFinium 
 	 * @see    MandatoryGrantDetail
 	 * @return MandatoryGrantDetail
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new MandatoryGrantDetailService()
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
	 * Convert MandatoryGrantDetail key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((MandatoryGrantDetail)arg2).getId();
	}

/*
       <p:selectOneMenu id="MandatoryGrantDetailId" value="#{xxxUI.MandatoryGrantDetail.xxxType}" converter="MandatoryGrantDetailConvertor" style="width:95%">
         <f:selectItems value="#{MandatoryGrantDetailUI.MandatoryGrantDetailList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="MandatoryGrantDetail" for="MandatoryGrantDetailID"/>
        <p:autoComplete id="MandatoryGrantDetailID" value="#{MandatoryGrantDetailUI.MandatoryGrantDetail.municipality}" completeMethod="#{MandatoryGrantDetailUI.completeMandatoryGrantDetail}"
                            var="rv" itemLabel="#{rv.MandatoryGrantDetailDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="MandatoryGrantDetailConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="MandatoryGrantDetail" style="white-space: nowrap">#{rv.MandatoryGrantDetailDescription}</p:column>
       </p:autoComplete>         
       
*/

}
