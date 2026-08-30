package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.Region;
import haj.com.service.lookup.RegionService;

// TODO: Auto-generated Javadoc
/**
 * The Class RegionConvertor.
 */
@FacesConverter(value = "RegionConvertor")
public class RegionConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a Region.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return Region
	 * @see    Region
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new RegionService()
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
	 * Convert Region key to String object.
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
		if (arg2 == null)
			return "";
		if (arg2 instanceof String)
			return arg2.toString();
		return ""+((Region)arg2).getId();
	}

/*
       <p:selectOneMenu id="RegionId" value="#{xxxUI.Region.xxxType}" converter="RegionConvertor" style="width:95%">
         <f:selectItems value="#{RegionUI.RegionList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="Region" for="RegionID"/>
        <p:autoComplete id="RegionID" value="#{RegionUI.Region.municipality}" completeMethod="#{RegionUI.completeRegion}"
                            var="rv" itemLabel="#{rv.RegionDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="RegionConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="Region" style="white-space: nowrap">#{rv.RegionDescription}</p:column>
       </p:autoComplete>         
       
*/

}
