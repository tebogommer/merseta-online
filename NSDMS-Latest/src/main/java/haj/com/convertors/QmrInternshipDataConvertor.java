package haj.com.convertors;

import haj.com.entity.QmrInternshipData;
import haj.com.service.QmrInternshipDataService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "QmrInternshipDataConvertor")
public class QmrInternshipDataConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a QmrInternshipData
 	 * @author TechFinium 
 	 * @see    QmrInternshipData
 	 * @return QmrInternshipData
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new QmrInternshipDataService()
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
	 * Convert QmrInternshipData key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((QmrInternshipData)arg2).getId();
	}

/*
       <p:selectOneMenu id="QmrInternshipDataId" value="#{xxxUI.QmrInternshipData.xxxType}" converter="QmrInternshipDataConvertor" style="width:95%">
         <f:selectItems value="#{QmrInternshipDataUI.QmrInternshipDataList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="QmrInternshipData" for="QmrInternshipDataID"/>
        <p:autoComplete id="QmrInternshipDataID" value="#{QmrInternshipDataUI.QmrInternshipData.municipality}" completeMethod="#{QmrInternshipDataUI.completeQmrInternshipData}"
                            var="rv" itemLabel="#{rv.QmrInternshipDataDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="QmrInternshipDataConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="QmrInternshipData" style="white-space: nowrap">#{rv.QmrInternshipDataDescription}</p:column>
       </p:autoComplete>         
       
*/

}
