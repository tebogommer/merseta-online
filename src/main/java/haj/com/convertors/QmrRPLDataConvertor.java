package haj.com.convertors;

import haj.com.entity.QmrRPLData;
import haj.com.service.QmrRPLDataService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "QmrRPLDataConvertor")
public class QmrRPLDataConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a QmrRPLData
 	 * @author TechFinium 
 	 * @see    QmrRPLData
 	 * @return QmrRPLData
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new QmrRPLDataService()
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
	 * Convert QmrRPLData key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((QmrRPLData)arg2).getId();
	}

/*
       <p:selectOneMenu id="QmrRPLDataId" value="#{xxxUI.QmrRPLData.xxxType}" converter="QmrRPLDataConvertor" style="width:95%">
         <f:selectItems value="#{QmrRPLDataUI.QmrRPLDataList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="QmrRPLData" for="QmrRPLDataID"/>
        <p:autoComplete id="QmrRPLDataID" value="#{QmrRPLDataUI.QmrRPLData.municipality}" completeMethod="#{QmrRPLDataUI.completeQmrRPLData}"
                            var="rv" itemLabel="#{rv.QmrRPLDataDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="QmrRPLDataConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="QmrRPLData" style="white-space: nowrap">#{rv.QmrRPLDataDescription}</p:column>
       </p:autoComplete>         
       
*/

}
