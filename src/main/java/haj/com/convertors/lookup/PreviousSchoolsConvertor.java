package haj.com.convertors.lookup;

import haj.com.entity.lookup.PreviousSchools;
import haj.com.service.lookup.PreviousSchoolsService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "PreviousSchoolsConvertor")
public class PreviousSchoolsConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a PreviousSchools
 	 * @author TechFinium 
 	 * @see    PreviousSchools
 	 * @return PreviousSchools
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new PreviousSchoolsService()
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
	 * Convert PreviousSchools key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((PreviousSchools)arg2).getId();
	}

/*
       <p:selectOneMenu id="PreviousSchoolsId" value="#{xxxUI.PreviousSchools.xxxType}" converter="PreviousSchoolsConvertor" style="width:95%">
         <f:selectItems value="#{PreviousSchoolsUI.PreviousSchoolsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="PreviousSchools" for="PreviousSchoolsID"/>
        <p:autoComplete id="PreviousSchoolsID" value="#{PreviousSchoolsUI.PreviousSchools.municipality}" completeMethod="#{PreviousSchoolsUI.completePreviousSchools}"
                            var="rv" itemLabel="#{rv.PreviousSchoolsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="PreviousSchoolsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="PreviousSchools" style="white-space: nowrap">#{rv.PreviousSchoolsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
