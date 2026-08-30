package haj.com.convertors;

import haj.com.entity.WspCompanyAddressHistory;
import haj.com.service.WspCompanyAddressHistoryService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "WspCompanyAddressHistoryConvertor")
public class WspCompanyAddressHistoryConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a WspCompanyAddressHistory
 	 * @author TechFinium 
 	 * @see    WspCompanyAddressHistory
 	 * @return WspCompanyAddressHistory
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new WspCompanyAddressHistoryService()
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
	 * Convert WspCompanyAddressHistory key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((WspCompanyAddressHistory)arg2).getId();
	}

/*
       <p:selectOneMenu id="WspCompanyAddressHistoryId" value="#{xxxUI.WspCompanyAddressHistory.xxxType}" converter="WspCompanyAddressHistoryConvertor" style="width:95%">
         <f:selectItems value="#{WspCompanyAddressHistoryUI.WspCompanyAddressHistoryList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="WspCompanyAddressHistory" for="WspCompanyAddressHistoryID"/>
        <p:autoComplete id="WspCompanyAddressHistoryID" value="#{WspCompanyAddressHistoryUI.WspCompanyAddressHistory.municipality}" completeMethod="#{WspCompanyAddressHistoryUI.completeWspCompanyAddressHistory}"
                            var="rv" itemLabel="#{rv.WspCompanyAddressHistoryDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="WspCompanyAddressHistoryConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="WspCompanyAddressHistory" style="white-space: nowrap">#{rv.WspCompanyAddressHistoryDescription}</p:column>
       </p:autoComplete>         
       
*/

}
