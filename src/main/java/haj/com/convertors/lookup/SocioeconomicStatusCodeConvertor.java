package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.SocioeconomicStatusCode;
import haj.com.service.lookup.SocioeconomicStatusCodeService;

// TODO: Auto-generated Javadoc
/**
 * The Class SocioeconomicStatusCodeConvertor.
 */
@FacesConverter(value = "SocioeconomicStatusCodeConvertor")
public class SocioeconomicStatusCodeConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SocioeconomicStatusCode.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return SocioeconomicStatusCode
	 * @see    SocioeconomicStatusCode
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SocioeconomicStatusCodeService()
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
	 * Convert SocioeconomicStatusCode key to String object.
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
		return ""+((SocioeconomicStatusCode)arg2).getId();
	}

/*
       <p:selectOneMenu id="SocioeconomicStatusCodeId" value="#{xxxUI.SocioeconomicStatusCode.xxxType}" converter="SocioeconomicStatusCodeConvertor" style="width:95%">
         <f:selectItems value="#{SocioeconomicStatusCodeUI.SocioeconomicStatusCodeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SocioeconomicStatusCode" for="SocioeconomicStatusCodeID"/>
        <p:autoComplete id="SocioeconomicStatusCodeID" value="#{SocioeconomicStatusCodeUI.SocioeconomicStatusCode.municipality}" completeMethod="#{SocioeconomicStatusCodeUI.completeSocioeconomicStatusCode}"
                            var="rv" itemLabel="#{rv.SocioeconomicStatusCodeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SocioeconomicStatusCodeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SocioeconomicStatusCode" style="white-space: nowrap">#{rv.SocioeconomicStatusCodeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
