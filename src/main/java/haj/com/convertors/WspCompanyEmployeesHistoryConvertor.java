package haj.com.convertors;

import haj.com.entity.WspCompanyEmployeesHistory;
import haj.com.service.WspCompanyEmployeesHistoryService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "WspCompanyEmployeesHistoryConvertor")
public class WspCompanyEmployeesHistoryConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a WspCompanyEmployeesHistory
 	 * @author TechFinium 
 	 * @see    WspCompanyEmployeesHistory
 	 * @return WspCompanyEmployeesHistory
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new WspCompanyEmployeesHistoryService()
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
	 * Convert WspCompanyEmployeesHistory key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((WspCompanyEmployeesHistory)arg2).getId();
	}

/*
       <p:selectOneMenu id="WspCompanyEmployeesHistoryId" value="#{xxxUI.WspCompanyEmployeesHistory.xxxType}" converter="WspCompanyEmployeesHistoryConvertor" style="width:95%">
         <f:selectItems value="#{WspCompanyEmployeesHistoryUI.WspCompanyEmployeesHistoryList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="WspCompanyEmployeesHistory" for="WspCompanyEmployeesHistoryID"/>
        <p:autoComplete id="WspCompanyEmployeesHistoryID" value="#{WspCompanyEmployeesHistoryUI.WspCompanyEmployeesHistory.municipality}" completeMethod="#{WspCompanyEmployeesHistoryUI.completeWspCompanyEmployeesHistory}"
                            var="rv" itemLabel="#{rv.WspCompanyEmployeesHistoryDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="WspCompanyEmployeesHistoryConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="WspCompanyEmployeesHistory" style="white-space: nowrap">#{rv.WspCompanyEmployeesHistoryDescription}</p:column>
       </p:autoComplete>         
       
*/

}
