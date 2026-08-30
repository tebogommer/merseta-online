package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.WspChecklist;
import haj.com.service.WspChecklistService;

// TODO: Auto-generated Javadoc
/**
 * The Class WspChecklistConvertor.
 */
@FacesConverter(value = "WspChecklistConvertor")
public class WspChecklistConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a WspChecklist.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return WspChecklist
	 * @see    WspChecklist
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new WspChecklistService()
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
	 * Convert WspChecklist key to String object.
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
		return ""+((WspChecklist)arg2).getId();
	}

/*
       <p:selectOneMenu id="WspChecklistId" value="#{xxxUI.WspChecklist.xxxType}" converter="WspChecklistConvertor" style="width:95%">
         <f:selectItems value="#{WspChecklistUI.WspChecklistList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="WspChecklist" for="WspChecklistID"/>
        <p:autoComplete id="WspChecklistID" value="#{WspChecklistUI.WspChecklist.municipality}" completeMethod="#{WspChecklistUI.completeWspChecklist}"
                            var="rv" itemLabel="#{rv.WspChecklistDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="WspChecklistConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="WspChecklist" style="white-space: nowrap">#{rv.WspChecklistDescription}</p:column>
       </p:autoComplete>         
       
*/

}
