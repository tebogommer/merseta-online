package haj.com.convertors;

import haj.com.entity.QmrArtisanData;
import haj.com.service.QmrArtisanDataService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "QmrArtisanDataConvertor")
public class QmrArtisanDataConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a QmrArtisanData
 	 * @author TechFinium 
 	 * @see    QmrArtisanData
 	 * @return QmrArtisanData
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new QmrArtisanDataService()
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
	 * Convert QmrArtisanData key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((QmrArtisanData)arg2).getId();
	}

/*
       <p:selectOneMenu id="QmrArtisanDataId" value="#{xxxUI.QmrArtisanData.xxxType}" converter="QmrArtisanDataConvertor" style="width:95%">
         <f:selectItems value="#{QmrArtisanDataUI.QmrArtisanDataList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="QmrArtisanData" for="QmrArtisanDataID"/>
        <p:autoComplete id="QmrArtisanDataID" value="#{QmrArtisanDataUI.QmrArtisanData.municipality}" completeMethod="#{QmrArtisanDataUI.completeQmrArtisanData}"
                            var="rv" itemLabel="#{rv.QmrArtisanDataDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="QmrArtisanDataConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="QmrArtisanData" style="white-space: nowrap">#{rv.QmrArtisanDataDescription}</p:column>
       </p:autoComplete>         
       
*/

}
