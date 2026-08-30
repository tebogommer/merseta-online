package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.AppraisalChecklist;
import haj.com.service.lookup.AppraisalChecklistService;

// TODO: Auto-generated Javadoc
/**
 * The Class AppraisalChecklistConvertor.
 */
@FacesConverter(value = "AppraisalChecklistConvertor")
public class AppraisalChecklistConvertor implements Converter {
	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a AppraisalChecklist.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return AppraisalChecklist
	 * @see    AppraisalChecklist
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new AppraisalChecklistService()
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
	 * Convert AppraisalChecklist key to String object.
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
		return ""+((AppraisalChecklist)arg2).getId();
	}

/*
       <p:selectOneMenu id="AppraisalChecklistId" value="#{xxxUI.AppraisalChecklist.xxxType}" converter="AppraisalChecklistConvertor" style="width:95%">
         <f:selectItems value="#{AppraisalChecklistUI.AppraisalChecklistList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="AppraisalChecklist" for="AppraisalChecklistID"/>
        <p:autoComplete id="AppraisalChecklistID" value="#{AppraisalChecklistUI.AppraisalChecklist.municipality}" completeMethod="#{AppraisalChecklistUI.completeAppraisalChecklist}"
                            var="rv" itemLabel="#{rv.AppraisalChecklistDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="AppraisalChecklistConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="AppraisalChecklist" style="white-space: nowrap">#{rv.AppraisalChecklistDescription}</p:column>
       </p:autoComplete>         
       
*/

}
