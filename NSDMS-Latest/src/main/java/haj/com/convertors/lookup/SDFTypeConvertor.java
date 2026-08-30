package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.SDFType;
import haj.com.service.lookup.SDFTypeService;

// TODO: Auto-generated Javadoc
/**
 * The Class SDFTypeConvertor.
 */
@FacesConverter(value = "SDFTypeConvertor")
public class SDFTypeConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SDFType.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return SDFType
	 * @see    SDFType
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SDFTypeService()
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
	 * Convert SDFType key to String object.
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
		return ""+((SDFType)arg2).getId();
	}

/*
       <p:selectOneMenu id="SDFTypeId" value="#{xxxUI.SDFType.xxxType}" converter="SDFTypeConvertor" style="width:95%">
         <f:selectItems value="#{SDFTypeUI.SDFTypeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SDFType" for="SDFTypeID"/>
        <p:autoComplete id="SDFTypeID" value="#{SDFTypeUI.SDFType.municipality}" completeMethod="#{SDFTypeUI.completeSDFType}"
                            var="rv" itemLabel="#{rv.SDFTypeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SDFTypeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SDFType" style="white-space: nowrap">#{rv.SDFTypeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
