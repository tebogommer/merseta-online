package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.PartOf;
import haj.com.service.lookup.PartOfService;

// TODO: Auto-generated Javadoc
/**
 * The Class PartOfConvertor.
 */
@FacesConverter(value = "PartOfConvertor")
public class PartOfConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a PartOf.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return PartOf
	 * @see    PartOf
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new PartOfService()
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
	 * Convert PartOf key to String object.
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
		return ""+((PartOf)arg2).getId();
	}

/*
       <p:selectOneMenu id="PartOfId" value="#{xxxUI.PartOf.xxxType}" converter="PartOfConvertor" style="width:95%">
         <f:selectItems value="#{PartOfUI.PartOfList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="PartOf" for="PartOfID"/>
        <p:autoComplete id="PartOfID" value="#{PartOfUI.PartOf.municipality}" completeMethod="#{PartOfUI.completePartOf}"
                            var="rv" itemLabel="#{rv.PartOfDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="PartOfConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="PartOf" style="white-space: nowrap">#{rv.PartOfDescription}</p:column>
       </p:autoComplete>         
       
*/

}
