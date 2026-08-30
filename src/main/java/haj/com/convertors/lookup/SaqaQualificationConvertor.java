package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.SaqaQualification;
import haj.com.service.lookup.SaqaQualificationService;

// TODO: Auto-generated Javadoc
/**
 * The Class SaqaQualificationConvertor.
 */
@FacesConverter(value = "SaqaQualificationConvertor")
public class SaqaQualificationConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SaqaQualification.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return SaqaQualification
	 * @see    SaqaQualification
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SaqaQualificationService()
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
	 * Convert SaqaQualification key to String object.
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
		return ""+((SaqaQualification)arg2).getId();
	}

/*
       <p:selectOneMenu id="SaqaQualificationId" value="#{xxxUI.SaqaQualification.xxxType}" converter="SaqaQualificationConvertor" style="width:95%">
         <f:selectItems value="#{SaqaQualificationUI.SaqaQualificationList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SaqaQualification" for="SaqaQualificationID"/>
        <p:autoComplete id="SaqaQualificationID" value="#{SaqaQualificationUI.SaqaQualification.municipality}" completeMethod="#{SaqaQualificationUI.completeSaqaQualification}"
                            var="rv" itemLabel="#{rv.SaqaQualificationDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SaqaQualificationConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SaqaQualification" style="white-space: nowrap">#{rv.SaqaQualificationDescription}</p:column>
       </p:autoComplete>         
       
*/

}
