package  haj.com.ui;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.LegacyReportingParams;
import haj.com.service.LegacyReportingParamsService;
import haj.com.entity.datamodel.LegacyReportingParamsDatamodel;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "legacyreportingparamsUI")
@ViewScoped
public class LegacyReportingParamsUI extends AbstractUI {

	private LegacyReportingParamsService service = new LegacyReportingParamsService();
	private List<LegacyReportingParams> legacyreportingparamsList = null;
	private List<LegacyReportingParams> legacyreportingparamsfilteredList = null;
	private LegacyReportingParams legacyreportingparams = null;
	private LazyDataModel<LegacyReportingParams> dataModel; 

    @PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Initialize method to get all LegacyReportingParams and prepare a for a create of a new LegacyReportingParams
 	 * @author TechFinium 
 	 * @see    LegacyReportingParams
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		legacyreportingparamsInfo();
	}

	/**
	 * Get all LegacyReportingParams for data table
 	 * @author TechFinium 
 	 * @see    LegacyReportingParams
 	 */
	public void legacyreportingparamsInfo() {
			dataModel = new LegacyReportingParamsDatamodel();
	}

	/**
	 * Insert LegacyReportingParams into database
 	 * @author TechFinium 
 	 * @see    LegacyReportingParams
 	 */
	public void legacyreportingparamsInsert() {
		try {
				 service.create(this.legacyreportingparams);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 legacyreportingparamsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update LegacyReportingParams in database
 	 * @author TechFinium 
 	 * @see    LegacyReportingParams
 	 */
    public void legacyreportingparamsUpdate() {
		try {
				 service.update(this.legacyreportingparams);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 legacyreportingparamsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete LegacyReportingParams from database
 	 * @author TechFinium 
 	 * @see    LegacyReportingParams
 	 */
	public void legacyreportingparamsDelete() {
		try {
			 service.delete(this.legacyreportingparams);
			  prepareNew();
			 legacyreportingparamsInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of LegacyReportingParams 
 	 * @author TechFinium 
 	 * @see    LegacyReportingParams
 	 */
	public void prepareNew() {
		legacyreportingparams = new LegacyReportingParams();
	}

/*
    public List<SelectItem> getLegacyReportingParamsDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	legacyreportingparamsInfo();
    	for (LegacyReportingParams ug : legacyreportingparamsList) {
    		// l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc()));
		}
    	return l;
    }
  */
  
    /**
     * Complete.
     *
     * @param desc the desc
     * @return the list
     */  
    public List<LegacyReportingParams> complete(String desc) {
		List<LegacyReportingParams> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		}
		catch (Exception e) {
		addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
    
    public List<LegacyReportingParams> getLegacyReportingParamsList() {
		return legacyreportingparamsList;
	}
	public void setLegacyReportingParamsList(List<LegacyReportingParams> legacyreportingparamsList) {
		this.legacyreportingparamsList = legacyreportingparamsList;
	}
	public LegacyReportingParams getLegacyreportingparams() {
		return legacyreportingparams;
	}
	public void setLegacyreportingparams(LegacyReportingParams legacyreportingparams) {
		this.legacyreportingparams = legacyreportingparams;
	}

    public List<LegacyReportingParams> getLegacyReportingParamsfilteredList() {
		return legacyreportingparamsfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param legacyreportingparamsfilteredList the new legacyreportingparamsfilteredList list
 	 * @see    LegacyReportingParams
 	 */	
	public void setLegacyReportingParamsfilteredList(List<LegacyReportingParams> legacyreportingparamsfilteredList) {
		this.legacyreportingparamsfilteredList = legacyreportingparamsfilteredList;
	}

	
	public LazyDataModel<LegacyReportingParams> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyReportingParams> dataModel) {
		this.dataModel = dataModel;
	}
	
}
