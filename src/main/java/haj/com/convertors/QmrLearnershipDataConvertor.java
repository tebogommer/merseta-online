package haj.com.convertors;

import haj.com.entity.QmrLearnershipData;
import haj.com.service.QmrLearnershipDataService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "QmrLearnershipDataConvertor")
public class QmrLearnershipDataConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a QmrLearnershipData
 	 * @author TechFinium 
 	 * @see    QmrLearnershipData
 	 * @return QmrLearnershipData
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new QmrLearnershipDataService()
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
	 * Convert QmrLearnershipData key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((QmrLearnershipData)arg2).getId();
	}

/*
       <p:selectOneMenu id="QmrLearnershipDataId" value="#{xxxUI.QmrLearnershipData.xxxType}" converter="QmrLearnershipDataConvertor" style="width:95%">
         <f:selectItems value="#{QmrLearnershipDataUI.QmrLearnershipDataList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="QmrLearnershipData" for="QmrLearnershipDataID"/>
        <p:autoComplete id="QmrLearnershipDataID" value="#{QmrLearnershipDataUI.QmrLearnershipData.municipality}" completeMethod="#{QmrLearnershipDataUI.completeQmrLearnershipData}"
                            var="rv" itemLabel="#{rv.QmrLearnershipDataDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="QmrLearnershipDataConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="QmrLearnershipData" style="white-space: nowrap">#{rv.QmrLearnershipDataDescription}</p:column>
       </p:autoComplete>         
       
*/

}
