package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.WspSkillsGap;
import haj.com.service.WspSkillsGapService;

@FacesConverter(value = "WspSkillsGapConvertor")
public class WspSkillsGapConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a WspSkillsGap
 	 * @author TechFinium 
 	 * @see    WspSkillsGap
 	 * @return WspSkillsGap
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new WspSkillsGapService()
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
	 * Convert WspSkillsGap key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((WspSkillsGap)arg2).getId();
	}

/*
       <p:selectOneMenu id="WspSkillsGapId" value="#{xxxUI.WspSkillsGap.xxxType}" converter="WspSkillsGapConvertor" style="width:95%">
         <f:selectItems value="#{WspSkillsGapUI.WspSkillsGapList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="WspSkillsGap" for="WspSkillsGapID"/>
        <p:autoComplete id="WspSkillsGapID" value="#{WspSkillsGapUI.WspSkillsGap.municipality}" completeMethod="#{WspSkillsGapUI.completeWspSkillsGap}"
                            var="rv" itemLabel="#{rv.WspSkillsGapDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="WspSkillsGapConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="WspSkillsGap" style="white-space: nowrap">#{rv.WspSkillsGapDescription}</p:column>
       </p:autoComplete>         
       
*/

}
