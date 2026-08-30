package haj.com.convertors.lookup;

import haj.com.entity.lookup.WspHistoricData;
import haj.com.service.lookup.WspHistoricDataService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "WspHistoricDataConvertor")
public class WspHistoricDataConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a WspHistoricData
 	 * @author TechFinium 
 	 * @see    WspHistoricData
 	 * @return WspHistoricData
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new WspHistoricDataService()
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
	 * Convert WspHistoricData key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((WspHistoricData)arg2).getId();
	}

/*
       <p:selectOneMenu id="WspHistoricDataId" value="#{xxxUI.WspHistoricData.xxxType}" converter="WspHistoricDataConvertor" style="width:95%">
         <f:selectItems value="#{WspHistoricDataUI.WspHistoricDataList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="WspHistoricData" for="WspHistoricDataID"/>
        <p:autoComplete id="WspHistoricDataID" value="#{WspHistoricDataUI.WspHistoricData.municipality}" completeMethod="#{WspHistoricDataUI.completeWspHistoricData}"
                            var="rv" itemLabel="#{rv.WspHistoricDataDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="WspHistoricDataConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="WspHistoricData" style="white-space: nowrap">#{rv.WspHistoricDataDescription}</p:column>
       </p:autoComplete>         
       
*/

}
