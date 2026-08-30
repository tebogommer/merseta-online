package haj.com.convertors;

import haj.com.entity.TrainingSite;
import haj.com.service.TrainingSiteService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "TrainingSiteConvertor")
public class TrainingSiteConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a TrainingSite
 	 * @author TechFinium 
 	 * @see    TrainingSite
 	 * @return TrainingSite
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new TrainingSiteService()
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
	 * Convert TrainingSite key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((TrainingSite)arg2).getId();
	}

/*
       <p:selectOneMenu id="TrainingSiteId" value="#{xxxUI.TrainingSite.xxxType}" converter="TrainingSiteConvertor" style="width:95%">
         <f:selectItems value="#{TrainingSiteUI.TrainingSiteList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="TrainingSite" for="TrainingSiteID"/>
        <p:autoComplete id="TrainingSiteID" value="#{TrainingSiteUI.TrainingSite.municipality}" completeMethod="#{TrainingSiteUI.completeTrainingSite}"
                            var="rv" itemLabel="#{rv.TrainingSiteDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="TrainingSiteConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="TrainingSite" style="white-space: nowrap">#{rv.TrainingSiteDescription}</p:column>
       </p:autoComplete>         
       
*/

}
