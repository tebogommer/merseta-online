package haj.com.convertors;

import haj.com.entity.QmrIndividualUnitStandardData;
import haj.com.service.QmrIndividualUnitStandardDataService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "QmrIndividualUnitStandardDataConvertor")
public class QmrIndividualUnitStandardDataConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a QmrIndividualUnitStandardData
 	 * @author TechFinium 
 	 * @see    QmrIndividualUnitStandardData
 	 * @return QmrIndividualUnitStandardData
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new QmrIndividualUnitStandardDataService()
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
	 * Convert QmrIndividualUnitStandardData key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((QmrIndividualUnitStandardData)arg2).getId();
	}

/*
       <p:selectOneMenu id="QmrIndividualUnitStandardDataId" value="#{xxxUI.QmrIndividualUnitStandardData.xxxType}" converter="QmrIndividualUnitStandardDataConvertor" style="width:95%">
         <f:selectItems value="#{QmrIndividualUnitStandardDataUI.QmrIndividualUnitStandardDataList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="QmrIndividualUnitStandardData" for="QmrIndividualUnitStandardDataID"/>
        <p:autoComplete id="QmrIndividualUnitStandardDataID" value="#{QmrIndividualUnitStandardDataUI.QmrIndividualUnitStandardData.municipality}" completeMethod="#{QmrIndividualUnitStandardDataUI.completeQmrIndividualUnitStandardData}"
                            var="rv" itemLabel="#{rv.QmrIndividualUnitStandardDataDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="QmrIndividualUnitStandardDataConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="QmrIndividualUnitStandardData" style="white-space: nowrap">#{rv.QmrIndividualUnitStandardDataDescription}</p:column>
       </p:autoComplete>         
       
*/

}
