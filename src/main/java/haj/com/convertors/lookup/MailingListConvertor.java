package haj.com.convertors.lookup;

import haj.com.entity.lookup.MailingList;
import haj.com.service.lookup.MailingListService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "MailingListConvertor")
public class MailingListConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a MailingList
 	 * @author TechFinium 
 	 * @see    MailingList
 	 * @return MailingList
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new MailingListService()
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
	 * Convert MailingList key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((MailingList)arg2).getId();
	}

/*
       <p:selectOneMenu id="MailingListId" value="#{xxxUI.MailingList.xxxType}" converter="MailingListConvertor" style="width:95%">
         <f:selectItems value="#{MailingListUI.MailingListList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="MailingList" for="MailingListID"/>
        <p:autoComplete id="MailingListID" value="#{MailingListUI.MailingList.municipality}" completeMethod="#{MailingListUI.completeMailingList}"
                            var="rv" itemLabel="#{rv.MailingListDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="MailingListConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="MailingList" style="white-space: nowrap">#{rv.MailingListDescription}</p:column>
       </p:autoComplete>         
       
*/

}
