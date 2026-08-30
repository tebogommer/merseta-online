package haj.com.convertors;

import haj.com.entity.ActiveContracts;
import haj.com.service.ActiveContractsService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "ActiveContractsConvertor")
public class ActiveContractsConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a ActiveContracts
 	 * @author TechFinium 
 	 * @see    ActiveContracts
 	 * @return ActiveContracts
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new ActiveContractsService()
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
	 * Convert ActiveContracts key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((ActiveContracts)arg2).getId();
	}

/*
       <p:selectOneMenu id="ActiveContractsId" value="#{xxxUI.ActiveContracts.xxxType}" converter="ActiveContractsConvertor" style="width:95%">
         <f:selectItems value="#{ActiveContractsUI.ActiveContractsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="ActiveContracts" for="ActiveContractsID"/>
        <p:autoComplete id="ActiveContractsID" value="#{ActiveContractsUI.ActiveContracts.municipality}" completeMethod="#{ActiveContractsUI.completeActiveContracts}"
                            var="rv" itemLabel="#{rv.ActiveContractsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="ActiveContractsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="ActiveContracts" style="white-space: nowrap">#{rv.ActiveContractsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
