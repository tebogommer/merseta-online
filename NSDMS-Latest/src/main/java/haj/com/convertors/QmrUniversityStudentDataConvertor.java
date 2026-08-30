package haj.com.convertors;

import haj.com.entity.QmrUniversityStudentData;
import haj.com.service.QmrUniversityStudentDataService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "QmrUniversityStudentDataConvertor")
public class QmrUniversityStudentDataConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a QmrUniversityStudentData
 	 * @author TechFinium 
 	 * @see    QmrUniversityStudentData
 	 * @return QmrUniversityStudentData
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new QmrUniversityStudentDataService()
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
	 * Convert QmrUniversityStudentData key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((QmrUniversityStudentData)arg2).getId();
	}

/*
       <p:selectOneMenu id="QmrUniversityStudentDataId" value="#{xxxUI.QmrUniversityStudentData.xxxType}" converter="QmrUniversityStudentDataConvertor" style="width:95%">
         <f:selectItems value="#{QmrUniversityStudentDataUI.QmrUniversityStudentDataList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="QmrUniversityStudentData" for="QmrUniversityStudentDataID"/>
        <p:autoComplete id="QmrUniversityStudentDataID" value="#{QmrUniversityStudentDataUI.QmrUniversityStudentData.municipality}" completeMethod="#{QmrUniversityStudentDataUI.completeQmrUniversityStudentData}"
                            var="rv" itemLabel="#{rv.QmrUniversityStudentDataDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="QmrUniversityStudentDataConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="QmrUniversityStudentData" style="white-space: nowrap">#{rv.QmrUniversityStudentDataDescription}</p:column>
       </p:autoComplete>         
       
*/

}
