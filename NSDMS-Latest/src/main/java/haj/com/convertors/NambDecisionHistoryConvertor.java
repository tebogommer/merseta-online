package haj.com.convertors;

import haj.com.entity.NambDecisionHistory;
import haj.com.service.NambDecisionHistoryService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "NambDecisionHistoryConvertor")
public class NambDecisionHistoryConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a NambDecisionHistory
 	 * @author TechFinium 
 	 * @see    NambDecisionHistory
 	 * @return NambDecisionHistory
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new NambDecisionHistoryService()
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
	 * Convert NambDecisionHistory key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((NambDecisionHistory)arg2).getId();
	}

/*
       <p:selectOneMenu id="NambDecisionHistoryId" value="#{xxxUI.NambDecisionHistory.xxxType}" converter="NambDecisionHistoryConvertor" style="width:95%">
         <f:selectItems value="#{NambDecisionHistoryUI.NambDecisionHistoryList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="NambDecisionHistory" for="NambDecisionHistoryID"/>
        <p:autoComplete id="NambDecisionHistoryID" value="#{NambDecisionHistoryUI.NambDecisionHistory.municipality}" completeMethod="#{NambDecisionHistoryUI.completeNambDecisionHistory}"
                            var="rv" itemLabel="#{rv.NambDecisionHistoryDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="NambDecisionHistoryConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="NambDecisionHistory" style="white-space: nowrap">#{rv.NambDecisionHistoryDescription}</p:column>
       </p:autoComplete>         
       
*/

}
