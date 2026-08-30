package haj.com.convertors;

import haj.com.entity.CollectDetail;
import haj.com.service.CollectDetailService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "CollectDetailConvertor")
public class CollectDetailConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a CollectDetail
 	 * @author TechFinium 
 	 * @see    CollectDetail
 	 * @return CollectDetail
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new CollectDetailService()
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
	 * Convert CollectDetail key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((CollectDetail)arg2).getId();
	}

/*
       <p:selectOneMenu id="CollectDetailId" value="#{xxxUI.CollectDetail.xxxType}" converter="CollectDetailConvertor" style="width:95%">
         <f:selectItems value="#{CollectDetailUI.CollectDetailList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="CollectDetail" for="CollectDetailID"/>
        <p:autoComplete id="CollectDetailID" value="#{CollectDetailUI.CollectDetail.municipality}" completeMethod="#{CollectDetailUI.completeCollectDetail}"
                            var="rv" itemLabel="#{rv.CollectDetailDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="CollectDetailConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="CollectDetail" style="white-space: nowrap">#{rv.CollectDetailDescription}</p:column>
       </p:autoComplete>         
       
*/

}
