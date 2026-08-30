package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.RegionTown;
import haj.com.service.lookup.RegionTownService;

@FacesConverter(value = "RegionTownConvertor")
public class RegionTownConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a RegionTown
 	 * @author TechFinium 
 	 * @see    RegionTown
 	 * @return RegionTown
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new RegionTownService()
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
	 * Convert RegionTown key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((RegionTown)arg2).getId();
	}

/*
       <p:selectOneMenu id="RegionTownId" value="#{xxxUI.RegionTown.xxxType}" converter="RegionTownConvertor" style="width:95%">
         <f:selectItems value="#{RegionTownUI.RegionTownList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="RegionTown" for="RegionTownID"/>
        <p:autoComplete id="RegionTownID" value="#{RegionTownUI.RegionTown.municipality}" completeMethod="#{RegionTownUI.completeRegionTown}"
                            var="rv" itemLabel="#{rv.RegionTownDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="RegionTownConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="RegionTown" style="white-space: nowrap">#{rv.RegionTownDescription}</p:column>
       </p:autoComplete>         
       
*/

}
