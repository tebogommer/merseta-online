package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.QdfCompany;
import haj.com.service.QdfCompanyService;

@FacesConverter(value = "QdfCompanyConvertor")
public class QdfCompanyConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a QdfCompany
 	 * @author TechFinium 
 	 * @see    QdfCompany
 	 * @return QdfCompany
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new QdfCompanyService()
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
	 * Convert QdfCompany key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((QdfCompany)arg2).getId();
	}

/*
       <p:selectOneMenu id="QdfCompanyId" value="#{xxxUI.QdfCompany.xxxType}" converter="QdfCompanyConvertor" style="width:95%">
         <f:selectItems value="#{QdfCompanyUI.QdfCompanyList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="QdfCompany" for="QdfCompanyID"/>
        <p:autoComplete id="QdfCompanyID" value="#{QdfCompanyUI.QdfCompany.municipality}" completeMethod="#{QdfCompanyUI.completeQdfCompany}"
                            var="rv" itemLabel="#{rv.QdfCompanyDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="QdfCompanyConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="QdfCompany" style="white-space: nowrap">#{rv.QdfCompanyDescription}</p:column>
       </p:autoComplete>         
       
*/

}
