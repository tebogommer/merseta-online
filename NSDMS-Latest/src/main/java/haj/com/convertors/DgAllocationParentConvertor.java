package haj.com.convertors;

import haj.com.entity.DgAllocationParent;
import haj.com.service.DgAllocationParentService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "DgAllocationParentConvertor")
public class DgAllocationParentConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a DgAllocationParent
 	 * @author TechFinium 
 	 * @see    DgAllocationParent
 	 * @return DgAllocationParent
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new DgAllocationParentService()
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
	 * Convert DgAllocationParent key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((DgAllocationParent)arg2).getId();
	}

/*
       <p:selectOneMenu id="DgAllocationParentId" value="#{xxxUI.DgAllocationParent.xxxType}" converter="DgAllocationParentConvertor" style="width:95%">
         <f:selectItems value="#{DgAllocationParentUI.DgAllocationParentList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="DgAllocationParent" for="DgAllocationParentID"/>
        <p:autoComplete id="DgAllocationParentID" value="#{DgAllocationParentUI.DgAllocationParent.municipality}" completeMethod="#{DgAllocationParentUI.completeDgAllocationParent}"
                            var="rv" itemLabel="#{rv.DgAllocationParentDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="DgAllocationParentConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="DgAllocationParent" style="white-space: nowrap">#{rv.DgAllocationParentDescription}</p:column>
       </p:autoComplete>         
       
*/

}
