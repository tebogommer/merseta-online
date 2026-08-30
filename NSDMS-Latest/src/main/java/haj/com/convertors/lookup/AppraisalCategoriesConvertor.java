package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.AppraisalCategories;
import haj.com.service.lookup.AppraisalCategoriesService;

// TODO: Auto-generated Javadoc
/**
 * The Class AppraisalCategoriesConvertor.
 */
@FacesConverter(value = "AppraisalCategoriesConvertor")
public class AppraisalCategoriesConvertor implements Converter {
	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a AppraisalCategories.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return AppraisalCategories
	 * @see    AppraisalCategories
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new AppraisalCategoriesService()
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
	 * Convert AppraisalCategories key to String object.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param arg2 the arg 2
	 * @return String
	 * @see    String
	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {		
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((AppraisalCategories)arg2).getId();
	}

/*
       <p:selectOneMenu id="AppraisalCategoriesId" value="#{xxxUI.AppraisalCategories.xxxType}" converter="AppraisalCategoriesConvertor" style="width:95%">
         <f:selectItems value="#{AppraisalCategoriesUI.AppraisalCategoriesList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="AppraisalCategories" for="AppraisalCategoriesID"/>
        <p:autoComplete id="AppraisalCategoriesID" value="#{AppraisalCategoriesUI.AppraisalCategories.municipality}" completeMethod="#{AppraisalCategoriesUI.completeAppraisalCategories}"
                            var="rv" itemLabel="#{rv.AppraisalCategoriesDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="AppraisalCategoriesConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="AppraisalCategories" style="white-space: nowrap">#{rv.AppraisalCategoriesDescription}</p:column>
       </p:autoComplete>         
       
*/

}
