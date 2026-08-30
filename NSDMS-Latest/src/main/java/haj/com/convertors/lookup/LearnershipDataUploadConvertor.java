package haj.com.convertors.lookup;

import haj.com.entity.lookup.LearnershipDataUpload;
import haj.com.service.lookup.LearnershipDataUploadService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "LearnershipDataUploadConvertor")
public class LearnershipDataUploadConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a LearnershipDataUpload
 	 * @author TechFinium 
 	 * @see    LearnershipDataUpload
 	 * @return LearnershipDataUpload
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new LearnershipDataUploadService()
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
	 * Convert LearnershipDataUpload key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((LearnershipDataUpload)arg2).getId();
	}

/*
       <p:selectOneMenu id="LearnershipDataUploadId" value="#{xxxUI.LearnershipDataUpload.xxxType}" converter="LearnershipDataUploadConvertor" style="width:95%">
         <f:selectItems value="#{LearnershipDataUploadUI.LearnershipDataUploadList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="LearnershipDataUpload" for="LearnershipDataUploadID"/>
        <p:autoComplete id="LearnershipDataUploadID" value="#{LearnershipDataUploadUI.LearnershipDataUpload.municipality}" completeMethod="#{LearnershipDataUploadUI.completeLearnershipDataUpload}"
                            var="rv" itemLabel="#{rv.LearnershipDataUploadDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="LearnershipDataUploadConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="LearnershipDataUpload" style="white-space: nowrap">#{rv.LearnershipDataUploadDescription}</p:column>
       </p:autoComplete>         
       
*/

}
