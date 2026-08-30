package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.PopiActStatus;
import haj.com.service.lookup.PopiActStatusService;

// TODO: Auto-generated Javadoc
/**
 * The Class PopiActStatusConvertor.
 */
@FacesConverter(value = "PopiActStatusConvertor")
public class PopiActStatusConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a PopiActStatus.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return PopiActStatus
	 * @see    PopiActStatus
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new PopiActStatusService()
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
	 * Convert PopiActStatus key to String object.
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
		return ""+((PopiActStatus)arg2).getId();
	}

/*
       <p:selectOneMenu id="PopiActStatusId" value="#{xxxUI.PopiActStatus.xxxType}" converter="PopiActStatusConvertor" style="width:95%">
         <f:selectItems value="#{PopiActStatusUI.PopiActStatusList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="PopiActStatus" for="PopiActStatusID"/>
        <p:autoComplete id="PopiActStatusID" value="#{PopiActStatusUI.PopiActStatus.municipality}" completeMethod="#{PopiActStatusUI.completePopiActStatus}"
                            var="rv" itemLabel="#{rv.PopiActStatusDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="PopiActStatusConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="PopiActStatus" style="white-space: nowrap">#{rv.PopiActStatusDescription}</p:column>
       </p:autoComplete>         
       
*/

}
