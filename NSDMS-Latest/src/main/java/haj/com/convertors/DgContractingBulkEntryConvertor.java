package haj.com.convertors;

import haj.com.entity.DgContractingBulkEntry;
import haj.com.service.DgContractingBulkEntryService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "DgContractingBulkEntryConvertor")
public class DgContractingBulkEntryConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a DgContractingBulkEntry
 	 * @author TechFinium 
 	 * @see    DgContractingBulkEntry
 	 * @return DgContractingBulkEntry
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new DgContractingBulkEntryService()
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
	 * Convert DgContractingBulkEntry key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((DgContractingBulkEntry)arg2).getId();
	}

/*
       <p:selectOneMenu id="DgContractingBulkEntryId" value="#{xxxUI.DgContractingBulkEntry.xxxType}" converter="DgContractingBulkEntryConvertor" style="width:95%">
         <f:selectItems value="#{DgContractingBulkEntryUI.DgContractingBulkEntryList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="DgContractingBulkEntry" for="DgContractingBulkEntryID"/>
        <p:autoComplete id="DgContractingBulkEntryID" value="#{DgContractingBulkEntryUI.DgContractingBulkEntry.municipality}" completeMethod="#{DgContractingBulkEntryUI.completeDgContractingBulkEntry}"
                            var="rv" itemLabel="#{rv.DgContractingBulkEntryDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="DgContractingBulkEntryConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="DgContractingBulkEntry" style="white-space: nowrap">#{rv.DgContractingBulkEntryDescription}</p:column>
       </p:autoComplete>         
       
*/

}
