package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.Sites;
import haj.com.service.SitesService;

// TODO: Auto-generated Javadoc
/**
 * The Class SitesConvertor.
 */
@FacesConverter(value = "SitesConvertor")
public class SitesConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a Sites.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return Sites
	 * @see    Sites
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SitesService()
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
	 * Convert Sites key to String object.
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
		if (arg2 instanceof String) return arg2.toString();
		return ""+((Sites)arg2).getId();
	}

/*
       <p:selectOneMenu id="SitesId" value="#{xxxUI.Sites.xxxType}" converter="SitesConvertor" style="width:95%">
         <f:selectItems value="#{SitesUI.SitesList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="Sites" for="SitesID"/>
        <p:autoComplete id="SitesID" value="#{SitesUI.Sites.municipality}" completeMethod="#{SitesUI.completeSites}"
                            var="rv" itemLabel="#{rv.SitesDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SitesConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="Sites" style="white-space: nowrap">#{rv.SitesDescription}</p:column>
       </p:autoComplete>         
       
*/

}
