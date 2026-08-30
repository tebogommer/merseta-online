package haj.com.convertors.lookup;

import haj.com.entity.lookup.AllocationChange;
import haj.com.service.lookup.AllocationChangeService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "AllocationChangeConvertor")
public class AllocationChangeConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a AllocationChange
 	 * @author TechFinium 
 	 * @see    AllocationChange
 	 * @return AllocationChange
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new AllocationChangeService()
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
	 * Convert AllocationChange key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((AllocationChange)arg2).getId();
	}

/*
       <p:selectOneMenu id="AllocationChangeId" value="#{xxxUI.AllocationChange.xxxType}" converter="AllocationChangeConvertor" style="width:95%">
         <f:selectItems value="#{AllocationChangeUI.AllocationChangeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="AllocationChange" for="AllocationChangeID"/>
        <p:autoComplete id="AllocationChangeID" value="#{AllocationChangeUI.AllocationChange.municipality}" completeMethod="#{AllocationChangeUI.completeAllocationChange}"
                            var="rv" itemLabel="#{rv.AllocationChangeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="AllocationChangeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="AllocationChange" style="white-space: nowrap">#{rv.AllocationChangeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
