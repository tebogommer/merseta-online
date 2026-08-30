package haj.com.convertors;

import haj.com.entity.ActiveContractDetail;
import haj.com.service.ActiveContractDetailService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "ActiveContractDetailConvertor")
public class ActiveContractDetailConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a ActiveContractDetail
 	 * @author TechFinium 
 	 * @see    ActiveContractDetail
 	 * @return ActiveContractDetail
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new ActiveContractDetailService()
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
	 * Convert ActiveContractDetail key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((ActiveContractDetail)arg2).getId();
	}

/*
       <p:selectOneMenu id="ActiveContractDetailId" value="#{xxxUI.ActiveContractDetail.xxxType}" converter="ActiveContractDetailConvertor" style="width:95%">
         <f:selectItems value="#{ActiveContractDetailUI.ActiveContractDetailList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="ActiveContractDetail" for="ActiveContractDetailID"/>
        <p:autoComplete id="ActiveContractDetailID" value="#{ActiveContractDetailUI.ActiveContractDetail.municipality}" completeMethod="#{ActiveContractDetailUI.completeActiveContractDetail}"
                            var="rv" itemLabel="#{rv.ActiveContractDetailDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="ActiveContractDetailConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="ActiveContractDetail" style="white-space: nowrap">#{rv.ActiveContractDetailDescription}</p:column>
       </p:autoComplete>         
       
*/

}
