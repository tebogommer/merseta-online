package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.QualificationEntryRequirement;
import haj.com.service.lookup.QualificationEntryRequirementService;

// TODO: Auto-generated Javadoc
/**
 * The Class QualificationEntryRequirementConvertor.
 */
@FacesConverter(value = "QualificationEntryRequirementConvertor")
public class QualificationEntryRequirementConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a QualificationEntryRequirement.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return QualificationEntryRequirement
	 * @see    QualificationEntryRequirement
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new QualificationEntryRequirementService()
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
	 * Convert QualificationEntryRequirement key to String object.
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
		return ""+((QualificationEntryRequirement)arg2).getId();
	}

/*
       <p:selectOneMenu id="QualificationEntryRequirementId" value="#{xxxUI.QualificationEntryRequirement.xxxType}" converter="QualificationEntryRequirementConvertor" style="width:95%">
         <f:selectItems value="#{QualificationEntryRequirementUI.QualificationEntryRequirementList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="QualificationEntryRequirement" for="QualificationEntryRequirementID"/>
        <p:autoComplete id="QualificationEntryRequirementID" value="#{QualificationEntryRequirementUI.QualificationEntryRequirement.municipality}" completeMethod="#{QualificationEntryRequirementUI.completeQualificationEntryRequirement}"
                            var="rv" itemLabel="#{rv.QualificationEntryRequirementDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="QualificationEntryRequirementConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="QualificationEntryRequirement" style="white-space: nowrap">#{rv.QualificationEntryRequirementDescription}</p:column>
       </p:autoComplete>         
       
*/

}
