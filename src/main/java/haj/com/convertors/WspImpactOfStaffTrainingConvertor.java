package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.WspImpactOfStaffTraining;
import haj.com.service.WspImpactOfStaffTrainingService;

@FacesConverter(value = "WspImpactOfStaffTrainingConvertor")
public class WspImpactOfStaffTrainingConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a WspImpactOfStaffTraining
 	 * @author TechFinium 
 	 * @see    WspImpactOfStaffTraining
 	 * @return WspImpactOfStaffTraining
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new WspImpactOfStaffTrainingService()
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
	 * Convert WspImpactOfStaffTraining key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((WspImpactOfStaffTraining)arg2).getId();
	}

/*
       <p:selectOneMenu id="WspImpactOfStaffTrainingId" value="#{xxxUI.WspImpactOfStaffTraining.xxxType}" converter="WspImpactOfStaffTrainingConvertor" style="width:95%">
         <f:selectItems value="#{WspImpactOfStaffTrainingUI.WspImpactOfStaffTrainingList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="WspImpactOfStaffTraining" for="WspImpactOfStaffTrainingID"/>
        <p:autoComplete id="WspImpactOfStaffTrainingID" value="#{WspImpactOfStaffTrainingUI.WspImpactOfStaffTraining.municipality}" completeMethod="#{WspImpactOfStaffTrainingUI.completeWspImpactOfStaffTraining}"
                            var="rv" itemLabel="#{rv.WspImpactOfStaffTrainingDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="WspImpactOfStaffTrainingConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="WspImpactOfStaffTraining" style="white-space: nowrap">#{rv.WspImpactOfStaffTrainingDescription}</p:column>
       </p:autoComplete>         
       
*/

}
