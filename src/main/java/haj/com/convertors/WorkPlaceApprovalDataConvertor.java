package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.WorkPlaceApprovalData;
import haj.com.service.WorkPlaceApprovalDataService;

@FacesConverter(value = "WorkPlaceApprovalDataConvertor")
public class WorkPlaceApprovalDataConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a WorkPlaceApprovalData
 	 * @author TechFinium 
 	 * @see    WorkPlaceApprovalData
 	 * @return WorkPlaceApprovalData
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new WorkPlaceApprovalDataService()
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
	 * Convert WorkPlaceApprovalData key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((WorkPlaceApprovalData)arg2).getId();
	}

/*
       <p:selectOneMenu id="WorkPlaceApprovalDataId" value="#{xxxUI.WorkPlaceApprovalData.xxxType}" converter="WorkPlaceApprovalDataConvertor" style="width:95%">
         <f:selectItems value="#{WorkPlaceApprovalDataUI.WorkPlaceApprovalDataList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="WorkPlaceApprovalData" for="WorkPlaceApprovalDataID"/>
        <p:autoComplete id="WorkPlaceApprovalDataID" value="#{WorkPlaceApprovalDataUI.WorkPlaceApprovalData.municipality}" completeMethod="#{WorkPlaceApprovalDataUI.completeWorkPlaceApprovalData}"
                            var="rv" itemLabel="#{rv.WorkPlaceApprovalDataDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="WorkPlaceApprovalDataConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="WorkPlaceApprovalData" style="white-space: nowrap">#{rv.WorkPlaceApprovalDataDescription}</p:column>
       </p:autoComplete>         
       
*/

}
