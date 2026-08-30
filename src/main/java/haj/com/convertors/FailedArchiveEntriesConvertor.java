package haj.com.convertors;

import haj.com.entity.FailedArchiveEntries;
import haj.com.service.FailedArchiveEntriesService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "FailedArchiveEntriesConvertor")
public class FailedArchiveEntriesConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a FailedArchiveEntries
 	 * @author TechFinium 
 	 * @see    FailedArchiveEntries
 	 * @return FailedArchiveEntries
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new FailedArchiveEntriesService()
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
	 * Convert FailedArchiveEntries key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((FailedArchiveEntries)arg2).getId();
	}

/*
       <p:selectOneMenu id="FailedArchiveEntriesId" value="#{xxxUI.FailedArchiveEntries.xxxType}" converter="FailedArchiveEntriesConvertor" style="width:95%">
         <f:selectItems value="#{FailedArchiveEntriesUI.FailedArchiveEntriesList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="FailedArchiveEntries" for="FailedArchiveEntriesID"/>
        <p:autoComplete id="FailedArchiveEntriesID" value="#{FailedArchiveEntriesUI.FailedArchiveEntries.municipality}" completeMethod="#{FailedArchiveEntriesUI.completeFailedArchiveEntries}"
                            var="rv" itemLabel="#{rv.FailedArchiveEntriesDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="FailedArchiveEntriesConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="FailedArchiveEntries" style="white-space: nowrap">#{rv.FailedArchiveEntriesDescription}</p:column>
       </p:autoComplete>         
       
*/

}
