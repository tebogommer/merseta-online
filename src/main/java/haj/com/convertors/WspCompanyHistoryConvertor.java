package haj.com.convertors;

import haj.com.entity.WspCompanyHistory;
import haj.com.service.WspCompanyHistoryService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "WspCompanyHistoryConvertor")
public class WspCompanyHistoryConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a WspCompanyHistory
 	 * @author TechFinium 
 	 * @see    WspCompanyHistory
 	 * @return WspCompanyHistory
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new WspCompanyHistoryService()
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
	 * Convert WspCompanyHistory key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((WspCompanyHistory)arg2).getId();
	}

/*
       <p:selectOneMenu id="WspCompanyHistoryId" value="#{xxxUI.WspCompanyHistory.xxxType}" converter="WspCompanyHistoryConvertor" style="width:95%">
         <f:selectItems value="#{WspCompanyHistoryUI.WspCompanyHistoryList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="WspCompanyHistory" for="WspCompanyHistoryID"/>
        <p:autoComplete id="WspCompanyHistoryID" value="#{WspCompanyHistoryUI.WspCompanyHistory.municipality}" completeMethod="#{WspCompanyHistoryUI.completeWspCompanyHistory}"
                            var="rv" itemLabel="#{rv.WspCompanyHistoryDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="WspCompanyHistoryConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="WspCompanyHistory" style="white-space: nowrap">#{rv.WspCompanyHistoryDescription}</p:column>
       </p:autoComplete>         
       
*/

}
