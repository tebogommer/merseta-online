package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.NqfDesigStatus;
import haj.com.service.lookup.NqfDesigStatusService;

// TODO: Auto-generated Javadoc
/**
 * The Class NqfDesigStatusConvertor.
 */
@FacesConverter(value = "NqfDesigStatusConvertor")
public class NqfDesigStatusConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a NqfDesigStatus.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return NqfDesigStatus
	 * @see    NqfDesigStatus
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new NqfDesigStatusService()
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
	 * Convert NqfDesigStatus key to String object.
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
		return ""+((NqfDesigStatus)arg2).getId();
	}

/*
       <p:selectOneMenu id="NqfDesigStatusId" value="#{xxxUI.NqfDesigStatus.xxxType}" converter="NqfDesigStatusConvertor" style="width:95%">
         <f:selectItems value="#{NqfDesigStatusUI.NqfDesigStatusList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="NqfDesigStatus" for="NqfDesigStatusID"/>
        <p:autoComplete id="NqfDesigStatusID" value="#{NqfDesigStatusUI.NqfDesigStatus.municipality}" completeMethod="#{NqfDesigStatusUI.completeNqfDesigStatus}"
                            var="rv" itemLabel="#{rv.NqfDesigStatusDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="NqfDesigStatusConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="NqfDesigStatus" style="white-space: nowrap">#{rv.NqfDesigStatusDescription}</p:column>
       </p:autoComplete>         
       
*/

}
