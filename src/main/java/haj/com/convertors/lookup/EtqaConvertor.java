package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.Etqa;
import haj.com.service.lookup.EtqaService;

// TODO: Auto-generated Javadoc
/**
 * The Class EtqaConvertor.
 */
@FacesConverter(value = "EtqaConvertor")
public class EtqaConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a Etqa.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return Etqa
	 * @see    Etqa
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new EtqaService()
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
	 * Convert Etqa key to String object.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param arg2 the arg 2
	 * @return String
	 * @see    String
	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		return ""+((Etqa)arg2).getId();
	}

/*
       <p:selectOneMenu id="EtqaId" value="#{xxxUI.Etqa.xxxType}" converter="EtqaConvertor" style="width:95%">
         <f:selectItems value="#{EtqaUI.EtqaList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="Etqa" for="EtqaID"/>
        <p:autoComplete id="EtqaID" value="#{EtqaUI.Etqa.municipality}" completeMethod="#{EtqaUI.completeEtqa}"
                            var="rv" itemLabel="#{rv.EtqaDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="EtqaConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="Etqa" style="white-space: nowrap">#{rv.EtqaDescription}</p:column>
       </p:autoComplete>         
       
*/

}
