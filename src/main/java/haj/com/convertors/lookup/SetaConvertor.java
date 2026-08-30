package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.Seta;
import haj.com.service.lookup.SetaService;

// TODO: Auto-generated Javadoc
/**
 * The Class SetaConvertor.
 */
@FacesConverter(value = "SetaConvertor")
public class SetaConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a Seta.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return Seta
	 * @see    Seta
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SetaService()
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
	 * Convert Seta key to String object.
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
		if (arg2 == null) {
			return "";
			
		}
		return ""+((Seta)arg2).getId();
	}

/*
       <p:selectOneMenu id="SetaId" value="#{xxxUI.Seta.xxxType}" converter="SetaConvertor" style="width:95%">
         <f:selectItems value="#{SetaUI.SetaList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="Seta" for="SetaID"/>
        <p:autoComplete id="SetaID" value="#{SetaUI.Seta.municipality}" completeMethod="#{SetaUI.completeSeta}"
                            var="rv" itemLabel="#{rv.SetaDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SetaConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="Seta" style="white-space: nowrap">#{rv.SetaDescription}</p:column>
       </p:autoComplete>         
       
*/

}
