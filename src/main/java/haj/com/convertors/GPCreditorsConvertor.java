package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.GPCreditors;
import haj.com.service.lookup.GPCreditorsService;

@FacesConverter(value = "GPCreditorsConvertor")
public class GPCreditorsConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a GPCreditors
 	 * @author TechFinium 
 	 * @see    GPCreditors
 	 * @return GPCreditors
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new GPCreditorsService()
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
	 * Convert GPCreditors key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((GPCreditors)arg2).getId();
	}

/*
       <p:selectOneMenu id="GPCreditorsId" value="#{xxxUI.GPCreditors.xxxType}" converter="GPCreditorsConvertor" style="width:95%">
         <f:selectItems value="#{GPCreditorsUI.GPCreditorsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="GPCreditors" for="GPCreditorsID"/>
        <p:autoComplete id="GPCreditorsID" value="#{GPCreditorsUI.GPCreditors.municipality}" completeMethod="#{GPCreditorsUI.completeGPCreditors}"
                            var="rv" itemLabel="#{rv.GPCreditorsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="GPCreditorsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="GPCreditors" style="white-space: nowrap">#{rv.GPCreditorsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
