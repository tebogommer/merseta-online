package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.SaqaUnitstandard;
import haj.com.service.lookup.SaqaUnitstandardService;

// TODO: Auto-generated Javadoc
/**
 * The Class SaqaUnitstandardConvertor.
 */
@FacesConverter(value = "SaqaUnitstandardConvertor")
public class SaqaUnitstandardConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SaqaUnitstandard.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return SaqaUnitstandard
	 * @see    SaqaUnitstandard
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SaqaUnitstandardService()
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
	 * Convert SaqaUnitstandard key to String object.
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
		return ""+((SaqaUnitstandard)arg2).getId();
	}

/*
       <p:selectOneMenu id="SaqaUnitstandardId" value="#{xxxUI.SaqaUnitstandard.xxxType}" converter="SaqaUnitstandardConvertor" style="width:95%">
         <f:selectItems value="#{SaqaUnitstandardUI.SaqaUnitstandardList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SaqaUnitstandard" for="SaqaUnitstandardID"/>
        <p:autoComplete id="SaqaUnitstandardID" value="#{SaqaUnitstandardUI.SaqaUnitstandard.municipality}" completeMethod="#{SaqaUnitstandardUI.completeSaqaUnitstandard}"
                            var="rv" itemLabel="#{rv.SaqaUnitstandardDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SaqaUnitstandardConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SaqaUnitstandard" style="white-space: nowrap">#{rv.SaqaUnitstandardDescription}</p:column>
       </p:autoComplete>         
       
*/

}
