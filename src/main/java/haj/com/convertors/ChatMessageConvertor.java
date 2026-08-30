package haj.com.convertors;

import haj.com.entity.ChatMessage;
import haj.com.service.ChatMessageService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "ChatMessageConvertor")
public class ChatMessageConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a ChatMessage
 	 * @author TechFinium 
 	 * @see    ChatMessage
 	 * @return ChatMessage
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new ChatMessageService()
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
	 * Convert ChatMessage key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((ChatMessage)arg2).getId();
	}

/*
       <p:selectOneMenu id="ChatMessageId" value="#{xxxUI.ChatMessage.xxxType}" converter="ChatMessageConvertor" style="width:95%">
         <f:selectItems value="#{ChatMessageUI.ChatMessageList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="ChatMessage" for="ChatMessageID"/>
        <p:autoComplete id="ChatMessageID" value="#{ChatMessageUI.ChatMessage.municipality}" completeMethod="#{ChatMessageUI.completeChatMessage}"
                            var="rv" itemLabel="#{rv.ChatMessageDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="ChatMessageConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="ChatMessage" style="white-space: nowrap">#{rv.ChatMessageDescription}</p:column>
       </p:autoComplete>         
       
*/

}
