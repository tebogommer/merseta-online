package haj.com.convertors.lookup;

import haj.com.entity.lookup.FinYearQuartersLookUp;
import haj.com.service.lookup.FinYearQuartersLookUpService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "FinYearQuartersLookUpConvertor")
public class FinYearQuartersLookUpConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a FinYearQuartersLookUp
 	 * @author TechFinium 
 	 * @see    FinYearQuartersLookUp
 	 * @return FinYearQuartersLookUp
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new FinYearQuartersLookUpService()
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
	 * Convert FinYearQuartersLookUp key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((FinYearQuartersLookUp)arg2).getId();
	}

/*
       <p:selectOneMenu id="FinYearQuartersLookUpId" value="#{xxxUI.FinYearQuartersLookUp.xxxType}" converter="FinYearQuartersLookUpConvertor" style="width:95%">
         <f:selectItems value="#{FinYearQuartersLookUpUI.FinYearQuartersLookUpList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="FinYearQuartersLookUp" for="FinYearQuartersLookUpID"/>
        <p:autoComplete id="FinYearQuartersLookUpID" value="#{FinYearQuartersLookUpUI.FinYearQuartersLookUp.municipality}" completeMethod="#{FinYearQuartersLookUpUI.completeFinYearQuartersLookUp}"
                            var="rv" itemLabel="#{rv.FinYearQuartersLookUpDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="FinYearQuartersLookUpConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="FinYearQuartersLookUp" style="white-space: nowrap">#{rv.FinYearQuartersLookUpDescription}</p:column>
       </p:autoComplete>         
       
*/

}
