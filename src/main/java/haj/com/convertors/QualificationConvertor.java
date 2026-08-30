package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.Qualification;
import haj.com.service.lookup.QualificationService;

// TODO: Auto-generated Javadoc
/**
 * The Class QualificationConvertor.
 */
@FacesConverter(value = "QualificationConvertor")
public class QualificationConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a Qualification.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return Qualification
	 * @see    Qualification
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new QualificationService()
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
	 * Convert Qualification key to String object.
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
		if(arg2!=null && ((Qualification)arg2).getId() !=null){
			return ""+((Qualification)arg2).getId();
		}else {
			return "";
		}		
	}

/*
       <p:selectOneMenu id="QualificationId" value="#{xxxUI.Qualification.xxxType}" converter="QualificationConvertor" style="width:95%">
         <f:selectItems value="#{QualificationUI.QualificationList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="Qualification" for="QualificationID"/>
        <p:autoComplete id="QualificationID" value="#{QualificationUI.Qualification.municipality}" completeMethod="#{QualificationUI.completeQualification}"
                            var="rv" itemLabel="#{rv.QualificationDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="QualificationConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="Qualification" style="white-space: nowrap">#{rv.QualificationDescription}</p:column>
       </p:autoComplete>         
       
*/

}
