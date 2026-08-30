package haj.com.convertors;

import haj.com.entity.DgContractingBulkItems;
import haj.com.service.DgContractingBulkItemsService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "DgContractingBulkItemsConvertor")
public class DgContractingBulkItemsConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a DgContractingBulkItems
 	 * @author TechFinium 
 	 * @see    DgContractingBulkItems
 	 * @return DgContractingBulkItems
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new DgContractingBulkItemsService()
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
	 * Convert DgContractingBulkItems key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((DgContractingBulkItems)arg2).getId();
	}

/*
       <p:selectOneMenu id="DgContractingBulkItemsId" value="#{xxxUI.DgContractingBulkItems.xxxType}" converter="DgContractingBulkItemsConvertor" style="width:95%">
         <f:selectItems value="#{DgContractingBulkItemsUI.DgContractingBulkItemsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="DgContractingBulkItems" for="DgContractingBulkItemsID"/>
        <p:autoComplete id="DgContractingBulkItemsID" value="#{DgContractingBulkItemsUI.DgContractingBulkItems.municipality}" completeMethod="#{DgContractingBulkItemsUI.completeDgContractingBulkItems}"
                            var="rv" itemLabel="#{rv.DgContractingBulkItemsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="DgContractingBulkItemsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="DgContractingBulkItems" style="white-space: nowrap">#{rv.DgContractingBulkItemsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
