package haj.com.convertors.lookup;

import haj.com.entity.lookup.QualificationTasks;
import haj.com.service.lookup.QualificationTasksService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "QualificationTasksConvertor")
public class QualificationTasksConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a QualificationTasks
 	 * @author TechFinium 
 	 * @see    QualificationTasks
 	 * @return QualificationTasks
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new QualificationTasksService()
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
	 * Convert QualificationTasks key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((QualificationTasks)arg2).getId();
	}

/*
       <p:selectOneMenu id="QualificationTasksId" value="#{xxxUI.QualificationTasks.xxxType}" converter="QualificationTasksConvertor" style="width:95%">
         <f:selectItems value="#{QualificationTasksUI.QualificationTasksList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="QualificationTasks" for="QualificationTasksID"/>
        <p:autoComplete id="QualificationTasksID" value="#{QualificationTasksUI.QualificationTasks.municipality}" completeMethod="#{QualificationTasksUI.completeQualificationTasks}"
                            var="rv" itemLabel="#{rv.QualificationTasksDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="QualificationTasksConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="QualificationTasks" style="white-space: nowrap">#{rv.QualificationTasksDescription}</p:column>
       </p:autoComplete>         
       
*/

}
