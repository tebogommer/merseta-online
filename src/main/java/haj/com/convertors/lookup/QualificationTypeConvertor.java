package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.QualificationType;
import haj.com.service.lookup.QualificationTypeService;

// TODO: Auto-generated Javadoc
/**
 * The Class QualificationTypeConvertor.
 */
@FacesConverter(value = "QualificationTypeConvertor")
public class QualificationTypeConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a QualificationType.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return QualificationType
	 * @see    QualificationType
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new QualificationTypeService()
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
	 * Convert QualificationType key to String object.
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
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((QualificationType)arg2).getId();
	}

/*
       <p:selectOneMenu id="QualificationTypeId" value="#{xxxUI.QualificationType.xxxType}" converter="QualificationTypeConvertor" style="width:95%">
         <f:selectItems value="#{QualificationTypeUI.QualificationTypeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="QualificationType" for="QualificationTypeID"/>
        <p:autoComplete id="QualificationTypeID" value="#{QualificationTypeUI.QualificationType.municipality}" completeMethod="#{QualificationTypeUI.completeQualificationType}"
                            var="rv" itemLabel="#{rv.QualificationTypeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="QualificationTypeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="QualificationType" style="white-space: nowrap">#{rv.QualificationTypeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
