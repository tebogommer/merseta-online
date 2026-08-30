package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.ProviderAccreditAssessor;
import haj.com.service.lookup.ProviderAccreditAssessorService;

// TODO: Auto-generated Javadoc
/**
 * The Class ProviderAccreditAssessorConvertor.
 */
@FacesConverter(value = "ProviderAccreditAssessorConvertor")
public class ProviderAccreditAssessorConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a ProviderAccreditAssessor.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return ProviderAccreditAssessor
	 * @see    ProviderAccreditAssessor
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new ProviderAccreditAssessorService()
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
	 * Convert ProviderAccreditAssessor key to String object.
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
		return ""+((ProviderAccreditAssessor)arg2).getId();
	}

/*
       <p:selectOneMenu id="ProviderAccreditAssessorId" value="#{xxxUI.ProviderAccreditAssessor.xxxType}" converter="ProviderAccreditAssessorConvertor" style="width:95%">
         <f:selectItems value="#{ProviderAccreditAssessorUI.ProviderAccreditAssessorList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="ProviderAccreditAssessor" for="ProviderAccreditAssessorID"/>
        <p:autoComplete id="ProviderAccreditAssessorID" value="#{ProviderAccreditAssessorUI.ProviderAccreditAssessor.municipality}" completeMethod="#{ProviderAccreditAssessorUI.completeProviderAccreditAssessor}"
                            var="rv" itemLabel="#{rv.ProviderAccreditAssessorDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="ProviderAccreditAssessorConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="ProviderAccreditAssessor" style="white-space: nowrap">#{rv.ProviderAccreditAssessorDescription}</p:column>
       </p:autoComplete>         
       
*/

}
