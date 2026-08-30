package haj.com.convertors.lookup;

import haj.com.entity.lookup.PostCodeLink;
import haj.com.service.lookup.PostCodeLinkService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "PostCodeLinkConvertor")
public class PostCodeLinkConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a PostCodeLink
 	 * @author TechFinium 
 	 * @see    PostCodeLink
 	 * @return PostCodeLink
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new PostCodeLinkService()
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
	 * Convert PostCodeLink key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((PostCodeLink)arg2).getId();
	}

/*
       <p:selectOneMenu id="PostCodeLinkId" value="#{xxxUI.PostCodeLink.xxxType}" converter="PostCodeLinkConvertor" style="width:95%">
         <f:selectItems value="#{PostCodeLinkUI.PostCodeLinkList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="PostCodeLink" for="PostCodeLinkID"/>
        <p:autoComplete id="PostCodeLinkID" value="#{PostCodeLinkUI.PostCodeLink.municipality}" completeMethod="#{PostCodeLinkUI.completePostCodeLink}"
                            var="rv" itemLabel="#{rv.PostCodeLinkDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="PostCodeLinkConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="PostCodeLink" style="white-space: nowrap">#{rv.PostCodeLinkDescription}</p:column>
       </p:autoComplete>         
       
*/

}
