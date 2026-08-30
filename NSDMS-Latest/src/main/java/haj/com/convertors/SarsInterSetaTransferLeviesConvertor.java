package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.sars.SarsInterSetaTransferLevies;
import haj.com.service.SarsInterSetaTransferLeviesService;

@FacesConverter(value = "SarsInterSetaTransferLeviesConvertor")
public class SarsInterSetaTransferLeviesConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SarsInterSetaTransferLevies
 	 * @author TechFinium 
 	 * @see    SarsInterSetaTransferLevies
 	 * @return SarsInterSetaTransferLevies
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SarsInterSetaTransferLeviesService()
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
	 * Convert SarsInterSetaTransferLevies key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((SarsInterSetaTransferLevies)arg2).getId();
	}

/*
       <p:selectOneMenu id="SarsInterSetaTransferLeviesId" value="#{xxxUI.SarsInterSetaTransferLevies.xxxType}" converter="SarsInterSetaTransferLeviesConvertor" style="width:95%">
         <f:selectItems value="#{SarsInterSetaTransferLeviesUI.SarsInterSetaTransferLeviesList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SarsInterSetaTransferLevies" for="SarsInterSetaTransferLeviesID"/>
        <p:autoComplete id="SarsInterSetaTransferLeviesID" value="#{SarsInterSetaTransferLeviesUI.SarsInterSetaTransferLevies.municipality}" completeMethod="#{SarsInterSetaTransferLeviesUI.completeSarsInterSetaTransferLevies}"
                            var="rv" itemLabel="#{rv.SarsInterSetaTransferLeviesDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SarsInterSetaTransferLeviesConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SarsInterSetaTransferLevies" style="white-space: nowrap">#{rv.SarsInterSetaTransferLeviesDescription}</p:column>
       </p:autoComplete>         
       
*/

}
