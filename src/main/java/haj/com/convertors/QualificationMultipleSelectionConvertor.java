package haj.com.convertors;

import haj.com.entity.QualificationMultipleSelection;
import haj.com.service.QualificationMultipleSelectionService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "QualificationMultipleSelectionConvertor")
public class QualificationMultipleSelectionConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a QualificationMultipleSelection
 	 * @author TechFinium 
 	 * @see    QualificationMultipleSelection
 	 * @return QualificationMultipleSelection
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new QualificationMultipleSelectionService()
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
	 * Convert QualificationMultipleSelection key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((QualificationMultipleSelection)arg2).getId();
	}

/*
       <p:selectOneMenu id="QualificationMultipleSelectionId" value="#{xxxUI.QualificationMultipleSelection.xxxType}" converter="QualificationMultipleSelectionConvertor" style="width:95%">
         <f:selectItems value="#{QualificationMultipleSelectionUI.QualificationMultipleSelectionList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="QualificationMultipleSelection" for="QualificationMultipleSelectionID"/>
        <p:autoComplete id="QualificationMultipleSelectionID" value="#{QualificationMultipleSelectionUI.QualificationMultipleSelection.municipality}" completeMethod="#{QualificationMultipleSelectionUI.completeQualificationMultipleSelection}"
                            var="rv" itemLabel="#{rv.QualificationMultipleSelectionDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="QualificationMultipleSelectionConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="QualificationMultipleSelection" style="white-space: nowrap">#{rv.QualificationMultipleSelectionDescription}</p:column>
       </p:autoComplete>         
       
*/

}
