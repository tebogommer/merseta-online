package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.TrainingReportedAtrPtr;
import haj.com.service.lookup.TrainingReportedAtrPtrService;

@FacesConverter(value = "TrainingReportedAtrPtrConvertor")
public class TrainingReportedAtrPtrConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a TrainingReportedAtrPtr
 	 * @author TechFinium 
 	 * @see    TrainingReportedAtrPtr
 	 * @return TrainingReportedAtrPtr
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new TrainingReportedAtrPtrService()
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
	 * Convert TrainingReportedAtrPtr key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((TrainingReportedAtrPtr)arg2).getId();
	}

/*
       <p:selectOneMenu id="TrainingReportedAtrPtrId" value="#{xxxUI.TrainingReportedAtrPtr.xxxType}" converter="TrainingReportedAtrPtrConvertor" style="width:95%">
         <f:selectItems value="#{TrainingReportedAtrPtrUI.TrainingReportedAtrPtrList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="TrainingReportedAtrPtr" for="TrainingReportedAtrPtrID"/>
        <p:autoComplete id="TrainingReportedAtrPtrID" value="#{TrainingReportedAtrPtrUI.TrainingReportedAtrPtr.municipality}" completeMethod="#{TrainingReportedAtrPtrUI.completeTrainingReportedAtrPtr}"
                            var="rv" itemLabel="#{rv.TrainingReportedAtrPtrDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="TrainingReportedAtrPtrConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="TrainingReportedAtrPtr" style="white-space: nowrap">#{rv.TrainingReportedAtrPtrDescription}</p:column>
       </p:autoComplete>         
       
*/

}
