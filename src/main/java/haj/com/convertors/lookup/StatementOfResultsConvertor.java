package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.StatementOfResults;
import haj.com.service.lookup.StatementOfResultsService;

// TODO: Auto-generated Javadoc
/**
 * The Class StatementOfResultsConvertor.
 */
@FacesConverter(value = "StatementOfResultsConvertor")
public class StatementOfResultsConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a StatementOfResults.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return StatementOfResults
	 * @see    StatementOfResults
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new StatementOfResultsService()
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
	 * Convert StatementOfResults key to String object.
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
		return ""+((StatementOfResults)arg2).getId();
	}

/*
       <p:selectOneMenu id="StatementOfResultsId" value="#{xxxUI.StatementOfResults.xxxType}" converter="StatementOfResultsConvertor" style="width:95%">
         <f:selectItems value="#{StatementOfResultsUI.StatementOfResultsList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="StatementOfResults" for="StatementOfResultsID"/>
        <p:autoComplete id="StatementOfResultsID" value="#{StatementOfResultsUI.StatementOfResults.municipality}" completeMethod="#{StatementOfResultsUI.completeStatementOfResults}"
                            var="rv" itemLabel="#{rv.StatementOfResultsDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="StatementOfResultsConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="StatementOfResults" style="white-space: nowrap">#{rv.StatementOfResultsDescription}</p:column>
       </p:autoComplete>         
       
*/

}
