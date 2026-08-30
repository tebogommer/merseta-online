package haj.com.convertors.lookup;

import haj.com.entity.lookup.AreaForImprovement;
import haj.com.service.lookup.AreaForImprovementService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "AreaForImprovementConvertor")
public class AreaForImprovementConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a AreaForImprovement
 	 * @author TechFinium 
 	 * @see    AreaForImprovement
 	 * @return AreaForImprovement
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new AreaForImprovementService()
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
	 * Convert AreaForImprovement key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((AreaForImprovement)arg2).getId();
	}

/*
       <p:selectOneMenu id="AreaForImprovementId" value="#{xxxUI.AreaForImprovement.xxxType}" converter="AreaForImprovementConvertor" style="width:95%">
         <f:selectItems value="#{AreaForImprovementUI.AreaForImprovementList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="AreaForImprovement" for="AreaForImprovementID"/>
        <p:autoComplete id="AreaForImprovementID" value="#{AreaForImprovementUI.AreaForImprovement.municipality}" completeMethod="#{AreaForImprovementUI.completeAreaForImprovement}"
                            var="rv" itemLabel="#{rv.AreaForImprovementDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="AreaForImprovementConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="AreaForImprovement" style="white-space: nowrap">#{rv.AreaForImprovementDescription}</p:column>
       </p:autoComplete>         
       
*/

}
