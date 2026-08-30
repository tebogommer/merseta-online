package haj.com.convertors;

import haj.com.entity.QmrBursaryData;
import haj.com.service.QmrBursaryDataService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "QmrBursaryDataConvertor")
public class QmrBursaryDataConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a QmrBursaryData
 	 * @author TechFinium 
 	 * @see    QmrBursaryData
 	 * @return QmrBursaryData
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new QmrBursaryDataService()
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
	 * Convert QmrBursaryData key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((QmrBursaryData)arg2).getId();
	}

/*
       <p:selectOneMenu id="QmrBursaryDataId" value="#{xxxUI.QmrBursaryData.xxxType}" converter="QmrBursaryDataConvertor" style="width:95%">
         <f:selectItems value="#{QmrBursaryDataUI.QmrBursaryDataList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="QmrBursaryData" for="QmrBursaryDataID"/>
        <p:autoComplete id="QmrBursaryDataID" value="#{QmrBursaryDataUI.QmrBursaryData.municipality}" completeMethod="#{QmrBursaryDataUI.completeQmrBursaryData}"
                            var="rv" itemLabel="#{rv.QmrBursaryDataDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="QmrBursaryDataConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="QmrBursaryData" style="white-space: nowrap">#{rv.QmrBursaryDataDescription}</p:column>
       </p:autoComplete>         
       
*/

}
