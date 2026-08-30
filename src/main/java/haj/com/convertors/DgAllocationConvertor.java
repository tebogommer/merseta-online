package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.DgAllocation;
import haj.com.service.DgAllocationService;

@FacesConverter(value = "DgAllocationConvertor")
public class DgAllocationConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a DgAllocation
 	 * @author TechFinium 
 	 * @see    DgAllocation
 	 * @return DgAllocation
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new DgAllocationService()
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
	 * Convert DgAllocation key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((DgAllocation)arg2).getId();
	}

/*
       <p:selectOneMenu id="DgAllocationId" value="#{xxxUI.DgAllocation.xxxType}" converter="DgAllocationConvertor" style="width:95%">
         <f:selectItems value="#{DgAllocationUI.DgAllocationList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="DgAllocation" for="DgAllocationID"/>
        <p:autoComplete id="DgAllocationID" value="#{DgAllocationUI.DgAllocation.municipality}" completeMethod="#{DgAllocationUI.completeDgAllocation}"
                            var="rv" itemLabel="#{rv.DgAllocationDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="DgAllocationConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="DgAllocation" style="white-space: nowrap">#{rv.DgAllocationDescription}</p:column>
       </p:autoComplete>         
       
*/

}
