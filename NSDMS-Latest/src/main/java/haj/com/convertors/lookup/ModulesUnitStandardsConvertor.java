package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.ModulesUnitStandards;
import haj.com.service.lookup.ModulesUnitStandardsService;

@FacesConverter(value = "ModulesUnitStandardsConvertor")
public class ModulesUnitStandardsConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a ModulesUnitStandards
 	 * @author TechFinium 
 	 * @see    ModulesUnitStandards
 	 * @return ModulesUnitStandards
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new ModulesUnitStandardsService()
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
	 * Convert ModulesUnitStandards key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((ModulesUnitStandards)arg2).getId();
	}

/*
       <p:selectOneMenu id="ModulesUnitStandardsId" value="#{xxxUI.ModulesUnitStandards.xxxType}" converter="ModulesUnitStandardsConvertor" style="width:95%">
         <f:selectItems value="#{ModulesUnitStandardsUI.ModulesUnitStandardsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="ModulesUnitStandards" for="ModulesUnitStandardsID"/>
        <p:autoComplete id="ModulesUnitStandardsID" value="#{ModulesUnitStandardsUI.ModulesUnitStandards.municipality}" completeMethod="#{ModulesUnitStandardsUI.completeModulesUnitStandards}"
                            var="rv" itemLabel="#{rv.ModulesUnitStandardsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="ModulesUnitStandardsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="ModulesUnitStandards" style="white-space: nowrap">#{rv.ModulesUnitStandardsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
