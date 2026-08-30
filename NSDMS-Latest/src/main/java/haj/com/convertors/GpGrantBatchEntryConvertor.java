package haj.com.convertors;

import haj.com.entity.GpGrantBatchEntry;
import haj.com.service.GpGrantBatchEntryService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "GpGrantBatchEntryConvertor")
public class GpGrantBatchEntryConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a GpGrantBatchEntry
 	 * @author TechFinium 
 	 * @see    GpGrantBatchEntry
 	 * @return GpGrantBatchEntry
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new GpGrantBatchEntryService()
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
	 * Convert GpGrantBatchEntry key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((GpGrantBatchEntry)arg2).getId();
	}

/*
       <p:selectOneMenu id="GpGrantBatchEntryId" value="#{xxxUI.GpGrantBatchEntry.xxxType}" converter="GpGrantBatchEntryConvertor" style="width:95%">
         <f:selectItems value="#{GpGrantBatchEntryUI.GpGrantBatchEntryList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="GpGrantBatchEntry" for="GpGrantBatchEntryID"/>
        <p:autoComplete id="GpGrantBatchEntryID" value="#{GpGrantBatchEntryUI.GpGrantBatchEntry.municipality}" completeMethod="#{GpGrantBatchEntryUI.completeGpGrantBatchEntry}"
                            var="rv" itemLabel="#{rv.GpGrantBatchEntryDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="GpGrantBatchEntryConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="GpGrantBatchEntry" style="white-space: nowrap">#{rv.GpGrantBatchEntryDescription}</p:column>
       </p:autoComplete>         
       
*/

}
