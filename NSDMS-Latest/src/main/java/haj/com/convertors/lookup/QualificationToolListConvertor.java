package haj.com.convertors.lookup;

import haj.com.entity.lookup.QualificationToolList;
import haj.com.service.lookup.QualificationToolListService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "QualificationToolListConvertor")
public class QualificationToolListConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a QualificationToolList
 	 * @author TechFinium 
 	 * @see    QualificationToolList
 	 * @return QualificationToolList
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new QualificationToolListService()
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
	 * Convert QualificationToolList key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((QualificationToolList)arg2).getId();
	}

/*
       <p:selectOneMenu id="QualificationToolListId" value="#{xxxUI.QualificationToolList.xxxType}" converter="QualificationToolListConvertor" style="width:95%">
         <f:selectItems value="#{QualificationToolListUI.QualificationToolListList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="QualificationToolList" for="QualificationToolListID"/>
        <p:autoComplete id="QualificationToolListID" value="#{QualificationToolListUI.QualificationToolList.municipality}" completeMethod="#{QualificationToolListUI.completeQualificationToolList}"
                            var="rv" itemLabel="#{rv.QualificationToolListDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="QualificationToolListConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="QualificationToolList" style="white-space: nowrap">#{rv.QualificationToolListDescription}</p:column>
       </p:autoComplete>         
       
*/

}
