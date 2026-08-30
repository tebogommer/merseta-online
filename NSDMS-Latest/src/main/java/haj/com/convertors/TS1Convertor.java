package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.datatakeon.TS1;
import haj.com.service.TS1Service;

// TODO: Auto-generated Javadoc
/**
 * The Class TS1Convertor.
 */
@FacesConverter(value = "TS1Convertor")
public class TS1Convertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a TS1.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return TS1
	 * @see    TS1
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new TS1Service()
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
	 * Convert TS1 key to String object.
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
		return ""+((TS1)arg2).getId();
	}

/*
       <p:selectOneMenu id="TS1Id" value="#{xxxUI.TS1.xxxType}" converter="TS1Convertor" style="width:95%">
         <f:selectItems value="#{TS1UI.TS1List}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="TS1" for="TS1ID"/>
        <p:autoComplete id="TS1ID" value="#{TS1UI.TS1.municipality}" completeMethod="#{TS1UI.completeTS1}"
                            var="rv" itemLabel="#{rv.TS1Description}" itemValue="#{rv}" 
                            forceSelection="true" converter="TS1Convertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="TS1" style="white-space: nowrap">#{rv.TS1Description}</p:column>
       </p:autoComplete>         
       
*/

}
