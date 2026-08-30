package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.CompanyLearnersTransfer;
import haj.com.service.CompanyLearnersTransferService;

@FacesConverter(value = "CompanyLearnersTransferConvertor")
public class CompanyLearnersTransferConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a CompanyLearnersTransfer
 	 * @author TechFinium 
 	 * @see    CompanyLearnersTransfer
 	 * @return CompanyLearnersTransfer
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new CompanyLearnersTransferService()
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
	 * Convert CompanyLearnersTransfer key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((CompanyLearnersTransfer)arg2).getId();
	}

/*
       <p:selectOneMenu id="CompanyLearnersTransferId" value="#{xxxUI.CompanyLearnersTransfer.xxxType}" converter="CompanyLearnersTransferConvertor" style="width:95%">
         <f:selectItems value="#{CompanyLearnersTransferUI.CompanyLearnersTransferList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="CompanyLearnersTransfer" for="CompanyLearnersTransferID"/>
        <p:autoComplete id="CompanyLearnersTransferID" value="#{CompanyLearnersTransferUI.CompanyLearnersTransfer.municipality}" completeMethod="#{CompanyLearnersTransferUI.completeCompanyLearnersTransfer}"
                            var="rv" itemLabel="#{rv.CompanyLearnersTransferDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="CompanyLearnersTransferConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="CompanyLearnersTransfer" style="white-space: nowrap">#{rv.CompanyLearnersTransferDescription}</p:column>
       </p:autoComplete>         
       
*/

}
