package haj.com.convertors.lookup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.lookup.YoutubeVideo;
import haj.com.service.lookup.YoutubeVideoService;

@FacesConverter(value = "YoutubeVideoConvertor")
public class YoutubeVideoConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a YoutubeVideo
 	 * @author TechFinium 
 	 * @see    YoutubeVideo
 	 * @return YoutubeVideo
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new YoutubeVideoService()
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
	 * Convert YoutubeVideo key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((YoutubeVideo)arg2).getId();
	}

/*
       <p:selectOneMenu id="YoutubeVideoId" value="#{xxxUI.YoutubeVideo.xxxType}" converter="YoutubeVideoConvertor" style="width:95%">
         <f:selectItems value="#{YoutubeVideoUI.YoutubeVideoList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="YoutubeVideo" for="YoutubeVideoID"/>
        <p:autoComplete id="YoutubeVideoID" value="#{YoutubeVideoUI.YoutubeVideo.municipality}" completeMethod="#{YoutubeVideoUI.completeYoutubeVideo}"
                            var="rv" itemLabel="#{rv.YoutubeVideoDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="YoutubeVideoConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="YoutubeVideo" style="white-space: nowrap">#{rv.YoutubeVideoDescription}</p:column>
       </p:autoComplete>         
       
*/

}
