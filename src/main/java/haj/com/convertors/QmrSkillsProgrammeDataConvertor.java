package haj.com.convertors;

import haj.com.entity.QmrSkillsProgrammeData;
import haj.com.service.QmrSkillsProgrammeDataService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "QmrSkillsProgrammeDataConvertor")
public class QmrSkillsProgrammeDataConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a QmrSkillsProgrammeData
 	 * @author TechFinium 
 	 * @see    QmrSkillsProgrammeData
 	 * @return QmrSkillsProgrammeData
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new QmrSkillsProgrammeDataService()
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
	 * Convert QmrSkillsProgrammeData key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((QmrSkillsProgrammeData)arg2).getId();
	}

/*
       <p:selectOneMenu id="QmrSkillsProgrammeDataId" value="#{xxxUI.QmrSkillsProgrammeData.xxxType}" converter="QmrSkillsProgrammeDataConvertor" style="width:95%">
         <f:selectItems value="#{QmrSkillsProgrammeDataUI.QmrSkillsProgrammeDataList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="QmrSkillsProgrammeData" for="QmrSkillsProgrammeDataID"/>
        <p:autoComplete id="QmrSkillsProgrammeDataID" value="#{QmrSkillsProgrammeDataUI.QmrSkillsProgrammeData.municipality}" completeMethod="#{QmrSkillsProgrammeDataUI.completeQmrSkillsProgrammeData}"
                            var="rv" itemLabel="#{rv.QmrSkillsProgrammeDataDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="QmrSkillsProgrammeDataConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="QmrSkillsProgrammeData" style="white-space: nowrap">#{rv.QmrSkillsProgrammeDataDescription}</p:column>
       </p:autoComplete>         
       
*/

}
