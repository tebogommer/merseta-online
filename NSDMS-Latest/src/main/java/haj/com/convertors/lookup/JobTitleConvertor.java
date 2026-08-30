package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.JobTitle;
import haj.com.service.lookup.JobTitleService;

@FacesConverter(value = "JobTitleConvertor")
public class JobTitleConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a JobTitle
 	 * @author TechFinium 
 	 * @see    JobTitle
 	 * @return JobTitle
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new JobTitleService()
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
	 * Convert JobTitle key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((JobTitle)arg2).getId();
	}

/*
       <p:selectOneMenu id="JobTitleId" value="#{xxxUI.JobTitle.xxxType}" converter="JobTitleConvertor" style="width:95%">
         <f:selectItems value="#{JobTitleUI.JobTitleList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="JobTitle" for="JobTitleID"/>
        <p:autoComplete id="JobTitleID" value="#{JobTitleUI.JobTitle.municipality}" completeMethod="#{JobTitleUI.completeJobTitle}"
                            var="rv" itemLabel="#{rv.JobTitleDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="JobTitleConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="JobTitle" style="white-space: nowrap">#{rv.JobTitleDescription}</p:column>
       </p:autoComplete>         
       
*/

}
