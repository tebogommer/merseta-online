package haj.com.convertors.lookup;

import haj.com.entity.lookup.AllocationList;
import haj.com.service.lookup.AllocationListService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "AllocationListConvertor")
public class AllocationListConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a AllocationList
 	 * @author TechFinium 
 	 * @see    AllocationList
 	 * @return AllocationList
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new AllocationListService()
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
	 * Convert AllocationList key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((AllocationList)arg2).getId();
	}

/*
       <p:selectOneMenu id="AllocationListId" value="#{xxxUI.AllocationList.xxxType}" converter="AllocationListConvertor" style="width:95%">
         <f:selectItems value="#{AllocationListUI.AllocationListList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="AllocationList" for="AllocationListID"/>
        <p:autoComplete id="AllocationListID" value="#{AllocationListUI.AllocationList.municipality}" completeMethod="#{AllocationListUI.completeAllocationList}"
                            var="rv" itemLabel="#{rv.AllocationListDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="AllocationListConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="AllocationList" style="white-space: nowrap">#{rv.AllocationListDescription}</p:column>
       </p:autoComplete>         
       
*/

}
