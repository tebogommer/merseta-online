package haj.com.convertors;

import haj.com.entity.WorkPlaceApprovalToolList;
import haj.com.service.WorkPlaceApprovalToolListService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "WorkPlaceApprovalToolListConvertor")
public class WorkPlaceApprovalToolListConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a WorkPlaceApprovalToolList
 	 * @author TechFinium 
 	 * @see    WorkPlaceApprovalToolList
 	 * @return WorkPlaceApprovalToolList
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new WorkPlaceApprovalToolListService()
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
	 * Convert WorkPlaceApprovalToolList key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((WorkPlaceApprovalToolList)arg2).getId();
	}

/*
       <p:selectOneMenu id="WorkPlaceApprovalToolListId" value="#{xxxUI.WorkPlaceApprovalToolList.xxxType}" converter="WorkPlaceApprovalToolListConvertor" style="width:95%">
         <f:selectItems value="#{WorkPlaceApprovalToolListUI.WorkPlaceApprovalToolListList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="WorkPlaceApprovalToolList" for="WorkPlaceApprovalToolListID"/>
        <p:autoComplete id="WorkPlaceApprovalToolListID" value="#{WorkPlaceApprovalToolListUI.WorkPlaceApprovalToolList.municipality}" completeMethod="#{WorkPlaceApprovalToolListUI.completeWorkPlaceApprovalToolList}"
                            var="rv" itemLabel="#{rv.WorkPlaceApprovalToolListDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="WorkPlaceApprovalToolListConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="WorkPlaceApprovalToolList" style="white-space: nowrap">#{rv.WorkPlaceApprovalToolListDescription}</p:column>
       </p:autoComplete>         
       
*/

}
