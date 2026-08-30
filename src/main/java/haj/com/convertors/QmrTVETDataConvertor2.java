package haj.com.convertors;

import haj.com.entity.QmrTVETData;
import haj.com.service.QmrTVETDataService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "QmrTVETDataConvertor")
public class QmrTVETDataConvertor2 implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a QmrTVETData
 	 * @author TechFinium 
 	 * @see    QmrTVETData
 	 * @return QmrTVETData
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new QmrTVETDataService()
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
	 * Convert QmrTVETData key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((QmrTVETData)arg2).getId();
	}

/*
       <p:selectOneMenu id="QmrTVETDataId" value="#{xxxUI.QmrTVETData.xxxType}" converter="QmrTVETDataConvertor" style="width:95%">
         <f:selectItems value="#{QmrTVETDataUI.QmrTVETDataList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="QmrTVETData" for="QmrTVETDataID"/>
        <p:autoComplete id="QmrTVETDataID" value="#{QmrTVETDataUI.QmrTVETData.municipality}" completeMethod="#{QmrTVETDataUI.completeQmrTVETData}"
                            var="rv" itemLabel="#{rv.QmrTVETDataDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="QmrTVETDataConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="QmrTVETData" style="white-space: nowrap">#{rv.QmrTVETDataDescription}</p:column>
       </p:autoComplete>         
       
*/

}
