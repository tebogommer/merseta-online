package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.WorkplaceLinked;
import haj.com.service.lookup.WorkplaceLinkedService;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkplaceLinkedConvertor.
 */
@FacesConverter(value = "WorkplaceLinkedConvertor")
public class WorkplaceLinkedConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a WorkplaceLinked.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return WorkplaceLinked
	 * @see    WorkplaceLinked
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new WorkplaceLinkedService()
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
	 * Convert WorkplaceLinked key to String object.
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
		return ""+((WorkplaceLinked)arg2).getId();
	}

/*
       <p:selectOneMenu id="WorkplaceLinkedId" value="#{xxxUI.WorkplaceLinked.xxxType}" converter="WorkplaceLinkedConvertor" style="width:95%">
         <f:selectItems value="#{WorkplaceLinkedUI.WorkplaceLinkedList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="WorkplaceLinked" for="WorkplaceLinkedID"/>
        <p:autoComplete id="WorkplaceLinkedID" value="#{WorkplaceLinkedUI.WorkplaceLinked.municipality}" completeMethod="#{WorkplaceLinkedUI.completeWorkplaceLinked}"
                            var="rv" itemLabel="#{rv.WorkplaceLinkedDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="WorkplaceLinkedConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="WorkplaceLinked" style="white-space: nowrap">#{rv.WorkplaceLinkedDescription}</p:column>
       </p:autoComplete>         
       
*/

}
