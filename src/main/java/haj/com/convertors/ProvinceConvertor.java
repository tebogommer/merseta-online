package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.Province;
import haj.com.service.ProvinceService;

// TODO: Auto-generated Javadoc
/**
 * The Class ProvinceConvertor.
 */
@FacesConverter(value = "ProvinceConvertor")
public class ProvinceConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a Province.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return Province
	 * @see    Province
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new ProvinceService().findByKey(Long.valueOf(value));
			} catch (NumberFormatException e) {
				logger.fatal(e);
			} catch (Exception e) {
				logger.fatal(e);
				e.printStackTrace();
			}

		}
	    return null;
	}


	/**
	 * Convert Province key to String object.
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
		return ""+((Province)arg2).getId();
	}

/*
       <p:selectOneMenu id="ProvinceId" value="#{xxxUI.Province.xxxType}" converter="ProvinceConvertor" style="width:95%">
         <f:selectItems value="#{ProvinceUI.ProvinceList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="Province" for="ProvinceID"/>
        <p:autoComplete id="ProvinceID" value="#{ProvinceUI.Province.municipality}" completeMethod="#{ProvinceUI.completeProvince}"
                            var="rv" itemLabel="#{rv.ProvinceDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="ProvinceConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="Province" style="white-space: nowrap">#{rv.ProvinceDescription}</p:column>
       </p:autoComplete>         
       
*/

}
