package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.datatakeon.TS2;
import haj.com.service.TS2Service;

// TODO: Auto-generated Javadoc
/**
 * The Class TS2Convertor.
 */
@FacesConverter(value = "TS2Convertor")
public class TS2Convertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a TS2.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return TS2
	 * @see    TS2
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new TS2Service()
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
	 * Convert TS2 key to String object.
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
		return ""+((TS2)arg2).getId();
	}

/*
       <p:selectOneMenu id="TS2Id" value="#{xxxUI.TS2.xxxType}" converter="TS2Convertor" style="width:95%">
         <f:selectItems value="#{TS2UI.TS2List}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="TS2" for="TS2ID"/>
        <p:autoComplete id="TS2ID" value="#{TS2UI.TS2.municipality}" completeMethod="#{TS2UI.completeTS2}"
                            var="rv" itemLabel="#{rv.TS2Description}" itemValue="#{rv}" 
                            forceSelection="true" converter="TS2Convertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="TS2" style="white-space: nowrap">#{rv.TS2Description}</p:column>
       </p:autoComplete>         
       
*/

}
