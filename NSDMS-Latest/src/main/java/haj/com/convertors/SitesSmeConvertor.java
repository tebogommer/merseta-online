package haj.com.convertors;

import haj.com.entity.SitesSme;
import haj.com.service.SitesSmeService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "SitesSmeConvertor")
public class SitesSmeConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SitesSme
 	 * @author TechFinium 
 	 * @see    SitesSme
 	 * @return SitesSme
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SitesSmeService()
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
	 * Convert SitesSme key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((SitesSme)arg2).getId();
	}

/*
       <p:selectOneMenu id="SitesSmeId" value="#{xxxUI.SitesSme.xxxType}" converter="SitesSmeConvertor" style="width:95%">
         <f:selectItems value="#{SitesSmeUI.SitesSmeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SitesSme" for="SitesSmeID"/>
        <p:autoComplete id="SitesSmeID" value="#{SitesSmeUI.SitesSme.municipality}" completeMethod="#{SitesSmeUI.completeSitesSme}"
                            var="rv" itemLabel="#{rv.SitesSmeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SitesSmeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SitesSme" style="white-space: nowrap">#{rv.SitesSmeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
