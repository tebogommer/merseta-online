package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.NQFAlignment;
import haj.com.service.lookup.NQFAlignmentService;

// TODO: Auto-generated Javadoc
/**
 * The Class NQFAlignmentConvertor.
 */
@FacesConverter(value = "NQFAlignmentConvertor")
public class NQFAlignmentConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a NQFAlignment.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return NQFAlignment
	 * @see    NQFAlignment
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new NQFAlignmentService()
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
	 * Convert NQFAlignment key to String object.
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
		return ""+((NQFAlignment)arg2).getId();
	}

/*
       <p:selectOneMenu id="NQFAlignmentId" value="#{xxxUI.NQFAlignment.xxxType}" converter="NQFAlignmentConvertor" style="width:95%">
         <f:selectItems value="#{NQFAlignmentUI.NQFAlignmentList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="NQFAlignment" for="NQFAlignmentID"/>
        <p:autoComplete id="NQFAlignmentID" value="#{NQFAlignmentUI.NQFAlignment.municipality}" completeMethod="#{NQFAlignmentUI.completeNQFAlignment}"
                            var="rv" itemLabel="#{rv.NQFAlignmentDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="NQFAlignmentConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="NQFAlignment" style="white-space: nowrap">#{rv.NQFAlignmentDescription}</p:column>
       </p:autoComplete>         
       
*/

}
