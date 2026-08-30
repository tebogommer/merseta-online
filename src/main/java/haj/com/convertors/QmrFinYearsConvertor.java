package haj.com.convertors;

import haj.com.entity.QmrFinYears;
import haj.com.service.QmrFinYearsService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "QmrFinYearsConvertor")
public class QmrFinYearsConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a QmrFinYears
 	 * @author TechFinium 
 	 * @see    QmrFinYears
 	 * @return QmrFinYears
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new QmrFinYearsService()
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
	 * Convert QmrFinYears key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((QmrFinYears)arg2).getId();
	}

/*
       <p:selectOneMenu id="QmrFinYearsId" value="#{xxxUI.QmrFinYears.xxxType}" converter="QmrFinYearsConvertor" style="width:95%">
         <f:selectItems value="#{QmrFinYearsUI.QmrFinYearsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="QmrFinYears" for="QmrFinYearsID"/>
        <p:autoComplete id="QmrFinYearsID" value="#{QmrFinYearsUI.QmrFinYears.municipality}" completeMethod="#{QmrFinYearsUI.completeQmrFinYears}"
                            var="rv" itemLabel="#{rv.QmrFinYearsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="QmrFinYearsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="QmrFinYears" style="white-space: nowrap">#{rv.QmrFinYearsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
