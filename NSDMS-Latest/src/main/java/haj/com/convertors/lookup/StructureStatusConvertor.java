package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.StructureStatus;
import haj.com.service.lookup.StructureStatusService;

// TODO: Auto-generated Javadoc
/**
 * The Class StructureStatusConvertor.
 */
@FacesConverter(value = "StructureStatusConvertor")
public class StructureStatusConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a StructureStatus.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return StructureStatus
	 * @see    StructureStatus
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new StructureStatusService()
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
	 * Convert StructureStatus key to String object.
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
		return ""+((StructureStatus)arg2).getId();
	}

/*
       <p:selectOneMenu id="StructureStatusId" value="#{xxxUI.StructureStatus.xxxType}" converter="StructureStatusConvertor" style="width:95%">
         <f:selectItems value="#{StructureStatusUI.StructureStatusList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="StructureStatus" for="StructureStatusID"/>
        <p:autoComplete id="StructureStatusID" value="#{StructureStatusUI.StructureStatus.municipality}" completeMethod="#{StructureStatusUI.completeStructureStatus}"
                            var="rv" itemLabel="#{rv.StructureStatusDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="StructureStatusConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="StructureStatus" style="white-space: nowrap">#{rv.StructureStatusDescription}</p:column>
       </p:autoComplete>         
       
*/

}
