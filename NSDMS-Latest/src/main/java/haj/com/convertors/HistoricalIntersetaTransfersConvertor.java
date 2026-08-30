package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.HistoricalIntersetaTransfers;
import haj.com.service.HistoricalIntersetaTransfersService;

@FacesConverter(value = "HistoricalIntersetaTransfersConvertor")
public class HistoricalIntersetaTransfersConvertor implements Converter {
	 protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a HistoricalIntersetaTransfers
 	 * @author TechFinium 
 	 * @see    HistoricalIntersetaTransfers
 	 * @return HistoricalIntersetaTransfers
 	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new HistoricalIntersetaTransfersService()
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
	 * Convert HistoricalIntersetaTransfers key to String object
 	 * @author TechFinium 
 	 * @see    String
 	 * @return String
 	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) return "";
		if (arg2 instanceof String) return arg2.toString();
		return ""+((HistoricalIntersetaTransfers)arg2).getId();
	}

/*
       <p:selectOneMenu id="HistoricalIntersetaTransfersId" value="#{xxxUI.HistoricalIntersetaTransfers.xxxType}" converter="HistoricalIntersetaTransfersConvertor" style="width:95%">
         <f:selectItems value="#{HistoricalIntersetaTransfersUI.HistoricalIntersetaTransfersList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="HistoricalIntersetaTransfers" for="HistoricalIntersetaTransfersID"/>
        <p:autoComplete id="HistoricalIntersetaTransfersID" value="#{HistoricalIntersetaTransfersUI.HistoricalIntersetaTransfers.municipality}" completeMethod="#{HistoricalIntersetaTransfersUI.completeHistoricalIntersetaTransfers}"
                            var="rv" itemLabel="#{rv.HistoricalIntersetaTransfersDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="HistoricalIntersetaTransfersConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="HistoricalIntersetaTransfers" style="white-space: nowrap">#{rv.HistoricalIntersetaTransfersDescription}</p:column>
       </p:autoComplete>         
       
*/

}
