package haj.com.convertors;

import haj.com.entity.WspCompanyMainHistory;
import haj.com.service.WspCompanyMainHistoryService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "WspCompanyMainHistoryConvertor")
public class WspCompanyMainHistoryConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a WspCompanyMainHistory
 	 * @author TechFinium 
 	 * @see    WspCompanyMainHistory
 	 * @return WspCompanyMainHistory
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new WspCompanyMainHistoryService()
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
	 * Convert WspCompanyMainHistory key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((WspCompanyMainHistory)arg2).getId();
	}

/*
       <p:selectOneMenu id="WspCompanyMainHistoryId" value="#{xxxUI.WspCompanyMainHistory.xxxType}" converter="WspCompanyMainHistoryConvertor" style="width:95%">
         <f:selectItems value="#{WspCompanyMainHistoryUI.WspCompanyMainHistoryList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="WspCompanyMainHistory" for="WspCompanyMainHistoryID"/>
        <p:autoComplete id="WspCompanyMainHistoryID" value="#{WspCompanyMainHistoryUI.WspCompanyMainHistory.municipality}" completeMethod="#{WspCompanyMainHistoryUI.completeWspCompanyMainHistory}"
                            var="rv" itemLabel="#{rv.WspCompanyMainHistoryDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="WspCompanyMainHistoryConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="WspCompanyMainHistory" style="white-space: nowrap">#{rv.WspCompanyMainHistoryDescription}</p:column>
       </p:autoComplete>         
       
*/

}
