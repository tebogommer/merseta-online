package haj.com.convertors;

import haj.com.entity.MandatoryGrantDetailArchive;
import haj.com.service.MandatoryGrantDetailArchiveService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "MandatoryGrantDetailArchiveConvertor")
public class MandatoryGrantDetailArchiveConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a MandatoryGrantDetailArchive
 	 * @author TechFinium 
 	 * @see    MandatoryGrantDetailArchive
 	 * @return MandatoryGrantDetailArchive
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new MandatoryGrantDetailArchiveService()
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
	 * Convert MandatoryGrantDetailArchive key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((MandatoryGrantDetailArchive)arg2).getId();
	}

/*
       <p:selectOneMenu id="MandatoryGrantDetailArchiveId" value="#{xxxUI.MandatoryGrantDetailArchive.xxxType}" converter="MandatoryGrantDetailArchiveConvertor" style="width:95%">
         <f:selectItems value="#{MandatoryGrantDetailArchiveUI.MandatoryGrantDetailArchiveList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="MandatoryGrantDetailArchive" for="MandatoryGrantDetailArchiveID"/>
        <p:autoComplete id="MandatoryGrantDetailArchiveID" value="#{MandatoryGrantDetailArchiveUI.MandatoryGrantDetailArchive.municipality}" completeMethod="#{MandatoryGrantDetailArchiveUI.completeMandatoryGrantDetailArchive}"
                            var="rv" itemLabel="#{rv.MandatoryGrantDetailArchiveDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="MandatoryGrantDetailArchiveConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="MandatoryGrantDetailArchive" style="white-space: nowrap">#{rv.MandatoryGrantDetailArchiveDescription}</p:column>
       </p:autoComplete>         
       
*/

}
