package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.SkillsGapTrackLookUp;
import haj.com.service.lookup.SkillsGapTrackLookUpService;

@FacesConverter(value = "SkillsGapTrackLookUpConvertor")
public class SkillsGapTrackLookUpConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SkillsGapTrackLookUp
 	 * @author TechFinium 
 	 * @see    SkillsGapTrackLookUp
 	 * @return SkillsGapTrackLookUp
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SkillsGapTrackLookUpService()
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
	 * Convert SkillsGapTrackLookUp key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((SkillsGapTrackLookUp)arg2).getId();
	}

/*
       <p:selectOneMenu id="SkillsGapTrackLookUpId" value="#{xxxUI.SkillsGapTrackLookUp.xxxType}" converter="SkillsGapTrackLookUpConvertor" style="width:95%">
         <f:selectItems value="#{SkillsGapTrackLookUpUI.SkillsGapTrackLookUpList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SkillsGapTrackLookUp" for="SkillsGapTrackLookUpID"/>
        <p:autoComplete id="SkillsGapTrackLookUpID" value="#{SkillsGapTrackLookUpUI.SkillsGapTrackLookUp.municipality}" completeMethod="#{SkillsGapTrackLookUpUI.completeSkillsGapTrackLookUp}"
                            var="rv" itemLabel="#{rv.SkillsGapTrackLookUpDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SkillsGapTrackLookUpConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SkillsGapTrackLookUp" style="white-space: nowrap">#{rv.SkillsGapTrackLookUpDescription}</p:column>
       </p:autoComplete>         
       
*/

}
