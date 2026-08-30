package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.DocByte;
import haj.com.service.DocByteService;

@FacesConverter(value = "DocByteConvertor")
public class DocByteConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a DocByte
 	 * @author TechFinium 
 	 * @see    DocByte
 	 * @return DocByte
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new DocByteService()
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
	 * Convert DocByte key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((DocByte)arg2).getId();
	}

/*
       <p:selectOneMenu id="DocByteId" value="#{xxxUI.DocByte.xxxType}" converter="DocByteConvertor" style="width:95%">
         <f:selectItems value="#{DocByteUI.DocByteList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="DocByte" for="DocByteID"/>
        <p:autoComplete id="DocByteID" value="#{DocByteUI.DocByte.municipality}" completeMethod="#{DocByteUI.completeDocByte}"
                            var="rv" itemLabel="#{rv.DocByteDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="DocByteConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="DocByte" style="white-space: nowrap">#{rv.DocByteDescription}</p:column>
       </p:autoComplete>         
       
*/

}
