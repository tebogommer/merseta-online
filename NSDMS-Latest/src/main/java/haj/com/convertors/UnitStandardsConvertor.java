package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.UnitStandards;
import haj.com.service.UnitStandardsService;

// TODO: Auto-generated Javadoc
/**
 * The Class UnitStandardsConvertor.
 */
@FacesConverter(value = "UnitStandardsConvertor")
public class UnitStandardsConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a UnitStandards.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return UnitStandards
	 * @see    UnitStandards
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new UnitStandardsService()
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
	 * Convert UnitStandards key to String object.
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
		return ""+((UnitStandards)arg2).getId();
	}

/*
       <p:selectOneMenu id="UnitStandardsId" value="#{xxxUI.UnitStandards.xxxType}" converter="UnitStandardsConvertor" style="width:95%">
         <f:selectItems value="#{UnitStandardsUI.UnitStandardsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="UnitStandards" for="UnitStandardsID"/>
        <p:autoComplete id="UnitStandardsID" value="#{UnitStandardsUI.UnitStandards.municipality}" completeMethod="#{UnitStandardsUI.completeUnitStandards}"
                            var="rv" itemLabel="#{rv.UnitStandardsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="UnitStandardsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="UnitStandards" style="white-space: nowrap">#{rv.UnitStandardsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
