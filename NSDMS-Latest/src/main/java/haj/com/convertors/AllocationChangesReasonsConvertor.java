package haj.com.convertors;

import haj.com.entity.AllocationChangesReasons;
import haj.com.service.AllocationChangesReasonsService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "AllocationChangesReasonsConvertor")
public class AllocationChangesReasonsConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a AllocationChangesReasons
 	 * @author TechFinium 
 	 * @see    AllocationChangesReasons
 	 * @return AllocationChangesReasons
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new AllocationChangesReasonsService()
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
	 * Convert AllocationChangesReasons key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((AllocationChangesReasons)arg2).getId();
	}

/*
       <p:selectOneMenu id="AllocationChangesReasonsId" value="#{xxxUI.AllocationChangesReasons.xxxType}" converter="AllocationChangesReasonsConvertor" style="width:95%">
         <f:selectItems value="#{AllocationChangesReasonsUI.AllocationChangesReasonsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="AllocationChangesReasons" for="AllocationChangesReasonsID"/>
        <p:autoComplete id="AllocationChangesReasonsID" value="#{AllocationChangesReasonsUI.AllocationChangesReasons.municipality}" completeMethod="#{AllocationChangesReasonsUI.completeAllocationChangesReasons}"
                            var="rv" itemLabel="#{rv.AllocationChangesReasonsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="AllocationChangesReasonsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="AllocationChangesReasons" style="white-space: nowrap">#{rv.AllocationChangesReasonsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
