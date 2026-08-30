package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.Subfield;
import haj.com.service.lookup.SubfieldService;

// TODO: Auto-generated Javadoc
/**
 * The Class SubfieldConvertor.
 */
@FacesConverter(value = "SubfieldConvertor")
public class SubfieldConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a Subfield.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return Subfield
	 * @see    Subfield
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SubfieldService()
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
	 * Convert Subfield key to String object.
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
		return ""+((Subfield)arg2).getId();
	}

/*
       <p:selectOneMenu id="SubfieldId" value="#{xxxUI.Subfield.xxxType}" converter="SubfieldConvertor" style="width:95%">
         <f:selectItems value="#{SubfieldUI.SubfieldList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="Subfield" for="SubfieldID"/>
        <p:autoComplete id="SubfieldID" value="#{SubfieldUI.Subfield.municipality}" completeMethod="#{SubfieldUI.completeSubfield}"
                            var="rv" itemLabel="#{rv.SubfieldDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SubfieldConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="Subfield" style="white-space: nowrap">#{rv.SubfieldDescription}</p:column>
       </p:autoComplete>         
       
*/

}
