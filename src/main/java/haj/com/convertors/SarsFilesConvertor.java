package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.sars.SarsFiles;
import haj.com.service.SarsFilesService;

// TODO: Auto-generated Javadoc
/**
 * The Class SarsFilesConvertor.
 */
@FacesConverter(value = "SarsFilesConvertor")
public class SarsFilesConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SarsFiles.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return SarsFiles
	 * @see    SarsFiles
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SarsFilesService()
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
	 * Convert SarsFiles key to String object.
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
		return ""+((SarsFiles)arg2).getId();
	}

/*
       <p:selectOneMenu id="SarsFilesId" value="#{xxxUI.SarsFiles.xxxType}" converter="SarsFilesConvertor" style="width:95%">
         <f:selectItems value="#{SarsFilesUI.SarsFilesList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SarsFiles" for="SarsFilesID"/>
        <p:autoComplete id="SarsFilesID" value="#{SarsFilesUI.SarsFiles.municipality}" completeMethod="#{SarsFilesUI.completeSarsFiles}"
                            var="rv" itemLabel="#{rv.SarsFilesDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SarsFilesConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SarsFiles" style="white-space: nowrap">#{rv.SarsFilesDescription}</p:column>
       </p:autoComplete>         
       
*/

}
