package haj.com.convertors;

import haj.com.entity.QmrCandidacyProgrammeData;
import haj.com.service.QmrCandidacyProgrammeDataService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "QmrCandidacyProgrammeDataConvertor")
public class QmrCandidacyProgrammeDataConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a QmrCandidacyProgrammeData
 	 * @author TechFinium 
 	 * @see    QmrCandidacyProgrammeData
 	 * @return QmrCandidacyProgrammeData
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new QmrCandidacyProgrammeDataService()
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
	 * Convert QmrCandidacyProgrammeData key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((QmrCandidacyProgrammeData)arg2).getId();
	}

/*
       <p:selectOneMenu id="QmrCandidacyProgrammeDataId" value="#{xxxUI.QmrCandidacyProgrammeData.xxxType}" converter="QmrCandidacyProgrammeDataConvertor" style="width:95%">
         <f:selectItems value="#{QmrCandidacyProgrammeDataUI.QmrCandidacyProgrammeDataList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="QmrCandidacyProgrammeData" for="QmrCandidacyProgrammeDataID"/>
        <p:autoComplete id="QmrCandidacyProgrammeDataID" value="#{QmrCandidacyProgrammeDataUI.QmrCandidacyProgrammeData.municipality}" completeMethod="#{QmrCandidacyProgrammeDataUI.completeQmrCandidacyProgrammeData}"
                            var="rv" itemLabel="#{rv.QmrCandidacyProgrammeDataDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="QmrCandidacyProgrammeDataConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="QmrCandidacyProgrammeData" style="white-space: nowrap">#{rv.QmrCandidacyProgrammeDataDescription}</p:column>
       </p:autoComplete>         
       
*/

}
