package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.WorkPlaceApproval;
import haj.com.service.WorkPlaceApprovalService;

@FacesConverter(value = "WorkPlaceApprovalConvertor")
public class WorkPlaceApprovalConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a WorkPlaceApproval
 	 * @author TechFinium 
 	 * @see    WorkPlaceApproval
 	 * @return WorkPlaceApproval
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new WorkPlaceApprovalService()
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
	 * Convert WorkPlaceApproval key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((WorkPlaceApproval)arg2).getId();
	}

/*
       <p:selectOneMenu id="WorkPlaceApprovalId" value="#{xxxUI.WorkPlaceApproval.xxxType}" converter="WorkPlaceApprovalConvertor" style="width:95%">
         <f:selectItems value="#{WorkPlaceApprovalUI.WorkPlaceApprovalList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="WorkPlaceApproval" for="WorkPlaceApprovalID"/>
        <p:autoComplete id="WorkPlaceApprovalID" value="#{WorkPlaceApprovalUI.WorkPlaceApproval.municipality}" completeMethod="#{WorkPlaceApprovalUI.completeWorkPlaceApproval}"
                            var="rv" itemLabel="#{rv.WorkPlaceApprovalDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="WorkPlaceApprovalConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="WorkPlaceApproval" style="white-space: nowrap">#{rv.WorkPlaceApprovalDescription}</p:column>
       </p:autoComplete>         
       
*/

}
