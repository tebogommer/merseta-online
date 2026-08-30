package haj.com.convertors;

import haj.com.entity.QmrLecturerDevelopmentData;
import haj.com.service.QmrLecturerDevelopmentDataService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "QmrLecturerDevelopmentDataConvertor")
public class QmrLecturerDevelopmentDataConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a QmrLecturerDevelopmentData
 	 * @author TechFinium 
 	 * @see    QmrLecturerDevelopmentData
 	 * @return QmrLecturerDevelopmentData
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new QmrLecturerDevelopmentDataService()
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
	 * Convert QmrLecturerDevelopmentData key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((QmrLecturerDevelopmentData)arg2).getId();
	}

/*
       <p:selectOneMenu id="QmrLecturerDevelopmentDataId" value="#{xxxUI.QmrLecturerDevelopmentData.xxxType}" converter="QmrLecturerDevelopmentDataConvertor" style="width:95%">
         <f:selectItems value="#{QmrLecturerDevelopmentDataUI.QmrLecturerDevelopmentDataList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="QmrLecturerDevelopmentData" for="QmrLecturerDevelopmentDataID"/>
        <p:autoComplete id="QmrLecturerDevelopmentDataID" value="#{QmrLecturerDevelopmentDataUI.QmrLecturerDevelopmentData.municipality}" completeMethod="#{QmrLecturerDevelopmentDataUI.completeQmrLecturerDevelopmentData}"
                            var="rv" itemLabel="#{rv.QmrLecturerDevelopmentDataDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="QmrLecturerDevelopmentDataConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="QmrLecturerDevelopmentData" style="white-space: nowrap">#{rv.QmrLecturerDevelopmentDataDescription}</p:column>
       </p:autoComplete>         
       
*/

}
