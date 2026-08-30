package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.HostingCompanyProcess;
import haj.com.service.HostingCompanyProcessService;

// TODO: Auto-generated Javadoc
/**
 * The Class HostingCompanyProcessConvertor.
 */
@FacesConverter(value = "HostingCompanyProcessConvertor")
public class HostingCompanyProcessConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a HostingCompanyProcess.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return HostingCompanyProcess
	 * @see    HostingCompanyProcess
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new HostingCompanyProcessService()
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
	 * Convert HostingCompanyProcess key to String object.
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
		return ""+((HostingCompanyProcess)arg2).getId();
	}

/*
       <p:selectOneMenu id="HostingCompanyProcessId" value="#{xxxUI.HostingCompanyProcess.xxxType}" converter="HostingCompanyProcessConvertor" style="width:95%">
         <f:selectItems value="#{HostingCompanyProcessUI.HostingCompanyProcessList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="HostingCompanyProcess" for="HostingCompanyProcessID"/>
        <p:autoComplete id="HostingCompanyProcessID" value="#{HostingCompanyProcessUI.HostingCompanyProcess.municipality}" completeMethod="#{HostingCompanyProcessUI.completeHostingCompanyProcess}"
                            var="rv" itemLabel="#{rv.HostingCompanyProcessDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="HostingCompanyProcessConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="HostingCompanyProcess" style="white-space: nowrap">#{rv.HostingCompanyProcessDescription}</p:column>
       </p:autoComplete>         
       
*/

}
