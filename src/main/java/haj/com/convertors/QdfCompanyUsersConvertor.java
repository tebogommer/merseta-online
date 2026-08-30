package haj.com.convertors;

import haj.com.entity.QdfCompanyUsers;
import haj.com.service.QdfCompanyUsersService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "QdfCompanyUsersConvertor")
public class QdfCompanyUsersConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a QdfCompanyUsers
 	 * @author TechFinium 
 	 * @see    QdfCompanyUsers
 	 * @return QdfCompanyUsers
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new QdfCompanyUsersService()
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
	 * Convert QdfCompanyUsers key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((QdfCompanyUsers)arg2).getId();
	}

/*
       <p:selectOneMenu id="QdfCompanyUsersId" value="#{xxxUI.QdfCompanyUsers.xxxType}" converter="QdfCompanyUsersConvertor" style="width:95%">
         <f:selectItems value="#{QdfCompanyUsersUI.QdfCompanyUsersList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="QdfCompanyUsers" for="QdfCompanyUsersID"/>
        <p:autoComplete id="QdfCompanyUsersID" value="#{QdfCompanyUsersUI.QdfCompanyUsers.municipality}" completeMethod="#{QdfCompanyUsersUI.completeQdfCompanyUsers}"
                            var="rv" itemLabel="#{rv.QdfCompanyUsersDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="QdfCompanyUsersConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="QdfCompanyUsers" style="white-space: nowrap">#{rv.QdfCompanyUsersDescription}</p:column>
       </p:autoComplete>         
       
*/

}
