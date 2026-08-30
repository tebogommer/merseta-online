package haj.com.convertors.lookup;

import haj.com.entity.lookup.QualificationToolKit;
import haj.com.service.lookup.QualificationToolKitService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "QualificationToolKitConvertor")
public class QualificationToolKitConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a QualificationToolKit
 	 * @author TechFinium 
 	 * @see    QualificationToolKit
 	 * @return QualificationToolKit
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new QualificationToolKitService()
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
	 * Convert QualificationToolKit key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((QualificationToolKit)arg2).getId();
	}

/*
       <p:selectOneMenu id="QualificationToolKitId" value="#{xxxUI.QualificationToolKit.xxxType}" converter="QualificationToolKitConvertor" style="width:95%">
         <f:selectItems value="#{QualificationToolKitUI.QualificationToolKitList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="QualificationToolKit" for="QualificationToolKitID"/>
        <p:autoComplete id="QualificationToolKitID" value="#{QualificationToolKitUI.QualificationToolKit.municipality}" completeMethod="#{QualificationToolKitUI.completeQualificationToolKit}"
                            var="rv" itemLabel="#{rv.QualificationToolKitDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="QualificationToolKitConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="QualificationToolKit" style="white-space: nowrap">#{rv.QualificationToolKitDescription}</p:column>
       </p:autoComplete>         
       
*/

}
