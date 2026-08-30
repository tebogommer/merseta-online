package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.Blank;
import haj.com.service.BlankService;

// TODO: Auto-generated Javadoc
/**
 * The Class BlankConvertor.
 */
@FacesConverter(value = "BlankConvertor")
public class BlankConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a Blank.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return Blank
	 * @see    Blank
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new BlankService()
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
	 * Convert Blank key to String object.
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
		return ""+((Blank)arg2).getId();
	}

/*
       <p:selectOneMenu id="BlankId" value="#{xxxUI.Blank.xxxType}" converter="BlankConvertor" style="width:95%">
         <f:selectItems value="#{BlankUI.BlankList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="Blank" for="BlankID"/>
        <p:autoComplete id="BlankID" value="#{BlankUI.Blank.municipality}" completeMethod="#{BlankUI.completeBlank}"
                            var="rv" itemLabel="#{rv.BlankDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="BlankConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="Blank" style="white-space: nowrap">#{rv.BlankDescription}</p:column>
       </p:autoComplete>         
       
*/

}
