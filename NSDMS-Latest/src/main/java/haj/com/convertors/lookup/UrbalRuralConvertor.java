package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.UrbalRural;
import haj.com.service.lookup.UrbalRuralService;

// TODO: Auto-generated Javadoc
/**
 * The Class UrbalRuralConvertor.
 */
@FacesConverter(value = "UrbalRuralConvertor")
public class UrbalRuralConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a UrbalRural.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return UrbalRural
	 * @see    UrbalRural
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new UrbalRuralService()
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
	 * Convert UrbalRural key to String object.
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
		return ""+((UrbalRural)arg2).getId();
	}

/*
       <p:selectOneMenu id="UrbalRuralId" value="#{xxxUI.UrbalRural.xxxType}" converter="UrbalRuralConvertor" style="width:95%">
         <f:selectItems value="#{UrbalRuralUI.UrbalRuralList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="UrbalRural" for="UrbalRuralID"/>
        <p:autoComplete id="UrbalRuralID" value="#{UrbalRuralUI.UrbalRural.municipality}" completeMethod="#{UrbalRuralUI.completeUrbalRural}"
                            var="rv" itemLabel="#{rv.UrbalRuralDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="UrbalRuralConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="UrbalRural" style="white-space: nowrap">#{rv.UrbalRuralDescription}</p:column>
       </p:autoComplete>         
       
*/

}
