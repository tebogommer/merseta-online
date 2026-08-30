package haj.com.convertors;

import haj.com.entity.QmrAETProgrammeData;
import haj.com.service.QmrAETProgrammeDataService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "QmrAETProgrammeDataConvertor")
public class QmrAETProgrammeDataConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a QmrAETProgrammeData
 	 * @author TechFinium 
 	 * @see    QmrAETProgrammeData
 	 * @return QmrAETProgrammeData
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new QmrAETProgrammeDataService()
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
	 * Convert QmrAETProgrammeData key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((QmrAETProgrammeData)arg2).getId();
	}

/*
       <p:selectOneMenu id="QmrAETProgrammeDataId" value="#{xxxUI.QmrAETProgrammeData.xxxType}" converter="QmrAETProgrammeDataConvertor" style="width:95%">
         <f:selectItems value="#{QmrAETProgrammeDataUI.QmrAETProgrammeDataList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="QmrAETProgrammeData" for="QmrAETProgrammeDataID"/>
        <p:autoComplete id="QmrAETProgrammeDataID" value="#{QmrAETProgrammeDataUI.QmrAETProgrammeData.municipality}" completeMethod="#{QmrAETProgrammeDataUI.completeQmrAETProgrammeData}"
                            var="rv" itemLabel="#{rv.QmrAETProgrammeDataDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="QmrAETProgrammeDataConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="QmrAETProgrammeData" style="white-space: nowrap">#{rv.QmrAETProgrammeDataDescription}</p:column>
       </p:autoComplete>         
       
*/

}
