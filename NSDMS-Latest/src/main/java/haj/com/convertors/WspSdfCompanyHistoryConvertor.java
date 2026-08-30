package haj.com.convertors;

import haj.com.entity.WspSdfCompanyHistory;
import haj.com.service.WspSdfCompanyHistoryService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "WspSdfCompanyHistoryConvertor")
public class WspSdfCompanyHistoryConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a WspSdfCompanyHistory
 	 * @author TechFinium 
 	 * @see    WspSdfCompanyHistory
 	 * @return WspSdfCompanyHistory
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new WspSdfCompanyHistoryService()
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
	 * Convert WspSdfCompanyHistory key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((WspSdfCompanyHistory)arg2).getId();
	}

/*
       <p:selectOneMenu id="WspSdfCompanyHistoryId" value="#{xxxUI.WspSdfCompanyHistory.xxxType}" converter="WspSdfCompanyHistoryConvertor" style="width:95%">
         <f:selectItems value="#{WspSdfCompanyHistoryUI.WspSdfCompanyHistoryList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="WspSdfCompanyHistory" for="WspSdfCompanyHistoryID"/>
        <p:autoComplete id="WspSdfCompanyHistoryID" value="#{WspSdfCompanyHistoryUI.WspSdfCompanyHistory.municipality}" completeMethod="#{WspSdfCompanyHistoryUI.completeWspSdfCompanyHistory}"
                            var="rv" itemLabel="#{rv.WspSdfCompanyHistoryDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="WspSdfCompanyHistoryConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="WspSdfCompanyHistory" style="white-space: nowrap">#{rv.WspSdfCompanyHistoryDescription}</p:column>
       </p:autoComplete>         
       
*/

}
