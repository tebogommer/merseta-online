package haj.com.convertors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.sars.SarsEmployerDetail;
import haj.com.service.SarsEmployerDetailService;

// TODO: Auto-generated Javadoc
/**
 * The Class SarsEmployerDetailConvertor.
 */
@FacesConverter(value = "SarsEmployerDetailConvertor")
public class SarsEmployerDetailConvertor implements Converter {
	 
 	/** The logger. */
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Used by JSF to get a SarsEmployerDetail.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param value the value
	 * @return SarsEmployerDetail
	 * @see    SarsEmployerDetail
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		} else {

			try {
				return new SarsEmployerDetailService()
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
	 * Convert SarsEmployerDetail key to String object.
	 *
	 * @author TechFinium
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param arg2 the arg 2
	 * @return String
	 * @see    String
	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return ""+((SarsEmployerDetail)arg2).getId();
	}

/*
       <p:selectOneMenu id="SarsEmployerDetailId" value="#{xxxUI.SarsEmployerDetail.xxxType}" converter="SarsEmployerDetailConvertor" style="width:95%">
         <f:selectItems value="#{SarsEmployerDetailUI.SarsEmployerDetailList}" var="rv" itemLabel="#{rv.a}" itemValue="#{rv}"/>
       </p:selectOneMenu>
       
        <h:outputLabel value="SarsEmployerDetail" for="SarsEmployerDetailID"/>
        <p:autoComplete id="SarsEmployerDetailID" value="#{SarsEmployerDetailUI.SarsEmployerDetail.municipality}" completeMethod="#{SarsEmployerDetailUI.completeSarsEmployerDetail}"
                            var="rv" itemLabel="#{rv.SarsEmployerDetailDescription}" itemValue="#{rv}" 
                            forceSelection="true" converter="SarsEmployerDetailConvertor" dropdown="true" minQueryLength="3" maxResults="10" >
                 <p:column headerText="SarsEmployerDetail" style="white-space: nowrap">#{rv.SarsEmployerDetailDescription}</p:column>
       </p:autoComplete>         
       
*/

}
