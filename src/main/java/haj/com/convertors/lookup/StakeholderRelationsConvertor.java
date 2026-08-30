package haj.com.convertors.lookup;

import haj.com.entity.lookup.StakeholderRelations;
import haj.com.service.lookup.StakeholderRelationsService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "StakeholderRelationsConvertor")
public class StakeholderRelationsConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a StakeholderRelations
 	 * @author TechFinium 
 	 * @see    StakeholderRelations
 	 * @return StakeholderRelations
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new StakeholderRelationsService()
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
	 * Convert StakeholderRelations key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((StakeholderRelations)arg2).getId();
	}

/*
       <p:selectOneMenu id="StakeholderRelationsId" value="#{xxxUI.StakeholderRelations.xxxType}" converter="StakeholderRelationsConvertor" style="width:95%">
         <f:selectItems value="#{StakeholderRelationsUI.StakeholderRelationsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="StakeholderRelations" for="StakeholderRelationsID"/>
        <p:autoComplete id="StakeholderRelationsID" value="#{StakeholderRelationsUI.StakeholderRelations.municipality}" completeMethod="#{StakeholderRelationsUI.completeStakeholderRelations}"
                            var="rv" itemLabel="#{rv.StakeholderRelationsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="StakeholderRelationsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="StakeholderRelations" style="white-space: nowrap">#{rv.StakeholderRelationsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
