package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.WspCalculationData;
import haj.com.service.WspCalculationDataService;

@FacesConverter(value = "WspCalculationDataConvertor")
public class WspCalculationDataConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a WspCalculationData
 	 * @author TechFinium 
 	 * @see    WspCalculationData
 	 * @return WspCalculationData
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new WspCalculationDataService()
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
	 * Convert WspCalculationData key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((WspCalculationData)arg2).getId();
	}

/*
       <p:selectOneMenu id="WspCalculationDataId" value="#{xxxUI.WspCalculationData.xxxType}" converter="WspCalculationDataConvertor" style="width:95%">
         <f:selectItems value="#{WspCalculationDataUI.WspCalculationDataList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="WspCalculationData" for="WspCalculationDataID"/>
        <p:autoComplete id="WspCalculationDataID" value="#{WspCalculationDataUI.WspCalculationData.municipality}" completeMethod="#{WspCalculationDataUI.completeWspCalculationData}"
                            var="rv" itemLabel="#{rv.WspCalculationDataDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="WspCalculationDataConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="WspCalculationData" style="white-space: nowrap">#{rv.WspCalculationDataDescription}</p:column>
       </p:autoComplete>         
       
*/

}
