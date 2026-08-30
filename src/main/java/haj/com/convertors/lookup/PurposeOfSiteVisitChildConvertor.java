package haj.com.convertors.lookup;

import haj.com.entity.lookup.PurposeOfSiteVisitChild;
import haj.com.service.lookup.PurposeOfSiteVisitChildService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "PurposeOfSiteVisitChildConvertor")
public class PurposeOfSiteVisitChildConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a PurposeOfSiteVisitChild
 	 * @author TechFinium 
 	 * @see    PurposeOfSiteVisitChild
 	 * @return PurposeOfSiteVisitChild
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new PurposeOfSiteVisitChildService()
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
	 * Convert PurposeOfSiteVisitChild key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((PurposeOfSiteVisitChild)arg2).getId();
	}

/*
       <p:selectOneMenu id="PurposeOfSiteVisitChildId" value="#{xxxUI.PurposeOfSiteVisitChild.xxxType}" converter="PurposeOfSiteVisitChildConvertor" style="width:95%">
         <f:selectItems value="#{PurposeOfSiteVisitChildUI.PurposeOfSiteVisitChildList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="PurposeOfSiteVisitChild" for="PurposeOfSiteVisitChildID"/>
        <p:autoComplete id="PurposeOfSiteVisitChildID" value="#{PurposeOfSiteVisitChildUI.PurposeOfSiteVisitChild.municipality}" completeMethod="#{PurposeOfSiteVisitChildUI.completePurposeOfSiteVisitChild}"
                            var="rv" itemLabel="#{rv.PurposeOfSiteVisitChildDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="PurposeOfSiteVisitChildConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="PurposeOfSiteVisitChild" style="white-space: nowrap">#{rv.PurposeOfSiteVisitChildDescription}</p:column>
       </p:autoComplete>         
       
*/

}
