package haj.com.convertors;

import haj.com.entity.GpGrantBatchList;
import haj.com.service.GpGrantBatchListService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter(value = "GpGrantBatchListConvertor")
public class GpGrantBatchListConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a GpGrantBatchList
 	 * @author TechFinium 
 	 * @see    GpGrantBatchList
 	 * @return GpGrantBatchList
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new GpGrantBatchListService()
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
	 * Convert GpGrantBatchList key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((GpGrantBatchList)arg2).getId();
	}

/*
       <p:selectOneMenu id="GpGrantBatchListId" value="#{xxxUI.GpGrantBatchList.xxxType}" converter="GpGrantBatchListConvertor" style="width:95%">
         <f:selectItems value="#{GpGrantBatchListUI.GpGrantBatchListList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="GpGrantBatchList" for="GpGrantBatchListID"/>
        <p:autoComplete id="GpGrantBatchListID" value="#{GpGrantBatchListUI.GpGrantBatchList.municipality}" completeMethod="#{GpGrantBatchListUI.completeGpGrantBatchList}"
                            var="rv" itemLabel="#{rv.GpGrantBatchListDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="GpGrantBatchListConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="GpGrantBatchList" style="white-space: nowrap">#{rv.GpGrantBatchListDescription}</p:column>
       </p:autoComplete>         
       
*/

}
