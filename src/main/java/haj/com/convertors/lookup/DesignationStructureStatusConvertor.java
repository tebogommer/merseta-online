package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.DesignationStructureStatus;
import haj.com.service.lookup.DesignationStructureStatusService;

// TODO: Auto-generated Javadoc
/**
 * The Class DesignationStructureStatusConvertor.
 */
@FacesConverter(value = "DesignationStructureStatusConvertor")
public class DesignationStructureStatusConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a DesignationStructureStatus.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return DesignationStructureStatus
	 * @see    DesignationStructureStatus
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new DesignationStructureStatusService()
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
	 * Convert DesignationStructureStatus key to String object.
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
		return ""+((DesignationStructureStatus)arg2).getId();
	}

/*
       <p:selectOneMenu id="DesignationStructureStatusId" value="#{xxxUI.DesignationStructureStatus.xxxType}" converter="DesignationStructureStatusConvertor" style="width:95%">
         <f:selectItems value="#{DesignationStructureStatusUI.DesignationStructureStatusList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="DesignationStructureStatus" for="DesignationStructureStatusID"/>
        <p:autoComplete id="DesignationStructureStatusID" value="#{DesignationStructureStatusUI.DesignationStructureStatus.municipality}" completeMethod="#{DesignationStructureStatusUI.completeDesignationStructureStatus}"
                            var="rv" itemLabel="#{rv.DesignationStructureStatusDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="DesignationStructureStatusConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="DesignationStructureStatus" style="white-space: nowrap">#{rv.DesignationStructureStatusDescription}</p:column>
       </p:autoComplete>         
       
*/

}
