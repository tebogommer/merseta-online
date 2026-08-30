package haj.com.convertors;

import haj.com.entity.WspCompanyTrainingComitteeHistory;
import haj.com.service.WspCompanyTrainingComitteeHistoryService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "WspCompanyTrainingComitteeHistoryConvertor")
public class WspCompanyTrainingComitteeHistoryConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a WspCompanyTrainingComitteeHistory
 	 * @author TechFinium 
 	 * @see    WspCompanyTrainingComitteeHistory
 	 * @return WspCompanyTrainingComitteeHistory
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new WspCompanyTrainingComitteeHistoryService()
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
	 * Convert WspCompanyTrainingComitteeHistory key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((WspCompanyTrainingComitteeHistory)arg2).getId();
	}

/*
       <p:selectOneMenu id="WspCompanyTrainingComitteeHistoryId" value="#{xxxUI.WspCompanyTrainingComitteeHistory.xxxType}" converter="WspCompanyTrainingComitteeHistoryConvertor" style="width:95%">
         <f:selectItems value="#{WspCompanyTrainingComitteeHistoryUI.WspCompanyTrainingComitteeHistoryList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="WspCompanyTrainingComitteeHistory" for="WspCompanyTrainingComitteeHistoryID"/>
        <p:autoComplete id="WspCompanyTrainingComitteeHistoryID" value="#{WspCompanyTrainingComitteeHistoryUI.WspCompanyTrainingComitteeHistory.municipality}" completeMethod="#{WspCompanyTrainingComitteeHistoryUI.completeWspCompanyTrainingComitteeHistory}"
                            var="rv" itemLabel="#{rv.WspCompanyTrainingComitteeHistoryDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="WspCompanyTrainingComitteeHistoryConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="WspCompanyTrainingComitteeHistory" style="white-space: nowrap">#{rv.WspCompanyTrainingComitteeHistoryDescription}</p:column>
       </p:autoComplete>         
       
*/

}
