package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.CategorizationData;
import haj.com.service.CategorizationDataService;

@FacesConverter(value = "CategorizationDataConvertor")
public class CategorizationDataConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a CategorizationData
 	 * @author TechFinium 
 	 * @see    CategorizationData
 	 * @return CategorizationData
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new CategorizationDataService()
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
	 * Convert CategorizationData key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((CategorizationData)arg2).getId();
	}

/*
       <p:selectOneMenu id="CategorizationDataId" value="#{xxxUI.CategorizationData.xxxType}" converter="CategorizationDataConvertor" style="width:95%">
         <f:selectItems value="#{CategorizationDataUI.CategorizationDataList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="CategorizationData" for="CategorizationDataID"/>
        <p:autoComplete id="CategorizationDataID" value="#{CategorizationDataUI.CategorizationData.municipality}" completeMethod="#{CategorizationDataUI.completeCategorizationData}"
                            var="rv" itemLabel="#{rv.CategorizationDataDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="CategorizationDataConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="CategorizationData" style="white-space: nowrap">#{rv.CategorizationDataDescription}</p:column>
       </p:autoComplete>         
       
*/

}
