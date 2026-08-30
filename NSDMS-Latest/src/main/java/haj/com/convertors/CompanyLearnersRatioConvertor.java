package haj.com.convertors;

import haj.com.entity.CompanyLearnersRatio;
import haj.com.service.CompanyLearnersRatioService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "CompanyLearnersRatioConvertor")
public class CompanyLearnersRatioConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a CompanyLearnersRatio
 	 * @author TechFinium 
 	 * @see    CompanyLearnersRatio
 	 * @return CompanyLearnersRatio
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new CompanyLearnersRatioService()
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
	 * Convert CompanyLearnersRatio key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((CompanyLearnersRatio)arg2).getId();
	}

/*
       <p:selectOneMenu id="CompanyLearnersRatioId" value="#{xxxUI.CompanyLearnersRatio.xxxType}" converter="CompanyLearnersRatioConvertor" style="width:95%">
         <f:selectItems value="#{CompanyLearnersRatioUI.CompanyLearnersRatioList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="CompanyLearnersRatio" for="CompanyLearnersRatioID"/>
        <p:autoComplete id="CompanyLearnersRatioID" value="#{CompanyLearnersRatioUI.CompanyLearnersRatio.municipality}" completeMethod="#{CompanyLearnersRatioUI.completeCompanyLearnersRatio}"
                            var="rv" itemLabel="#{rv.CompanyLearnersRatioDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="CompanyLearnersRatioConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="CompanyLearnersRatio" style="white-space: nowrap">#{rv.CompanyLearnersRatioDescription}</p:column>
       </p:autoComplete>         
       
*/

}
