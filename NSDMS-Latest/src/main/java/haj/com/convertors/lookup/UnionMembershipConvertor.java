package haj.com.convertors.lookup;

import haj.com.entity.lookup.UnionMembership;
import haj.com.service.lookup.UnionMembershipService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "UnionMembershipConvertor")
public class UnionMembershipConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a UnionMembership
 	 * @author TechFinium 
 	 * @see    UnionMembership
 	 * @return UnionMembership
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new UnionMembershipService()
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
	 * Convert UnionMembership key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((UnionMembership)arg2).getId();
	}

/*
       <p:selectOneMenu id="UnionMembershipId" value="#{xxxUI.UnionMembership.xxxType}" converter="UnionMembershipConvertor" style="width:95%">
         <f:selectItems value="#{UnionMembershipUI.UnionMembershipList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="UnionMembership" for="UnionMembershipID"/>
        <p:autoComplete id="UnionMembershipID" value="#{UnionMembershipUI.UnionMembership.municipality}" completeMethod="#{UnionMembershipUI.completeUnionMembership}"
                            var="rv" itemLabel="#{rv.UnionMembershipDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="UnionMembershipConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="UnionMembership" style="white-space: nowrap">#{rv.UnionMembershipDescription}</p:column>
       </p:autoComplete>         
       
*/

}
