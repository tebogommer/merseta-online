package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.AppraisalCategoryCode;
import haj.com.service.lookup.AppraisalCategoryCodeService;

// TODO: Auto-generated Javadoc
/**
 * The Class AppraisalCategoryCodeConvertor.
 */
@FacesConverter(value = "AppraisalCategoryCodeConvertor")
public class AppraisalCategoryCodeConvertor implements Converter {
	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a AppraisalCategoryCode.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return AppraisalCategoryCode
	 * @see    AppraisalCategoryCode
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new AppraisalCategoryCodeService()
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
	 * Convert AppraisalCategoryCode key to String object.
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
		return ""+((AppraisalCategoryCode)arg2).getId();
	}

/*
       <p:selectOneMenu id="AppraisalCategoryCodeId" value="#{xxxUI.AppraisalCategoryCode.xxxType}" converter="AppraisalCategoryCodeConvertor" style="width:95%">
         <f:selectItems value="#{AppraisalCategoryCodeUI.AppraisalCategoryCodeList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="AppraisalCategoryCode" for="AppraisalCategoryCodeID"/>
        <p:autoComplete id="AppraisalCategoryCodeID" value="#{AppraisalCategoryCodeUI.AppraisalCategoryCode.municipality}" completeMethod="#{AppraisalCategoryCodeUI.completeAppraisalCategoryCode}"
                            var="rv" itemLabel="#{rv.AppraisalCategoryCodeDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="AppraisalCategoryCodeConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="AppraisalCategoryCode" style="white-space: nowrap">#{rv.AppraisalCategoryCodeDescription}</p:column>
       </p:autoComplete>         
       
*/

}
