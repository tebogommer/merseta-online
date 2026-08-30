package  haj.com.ui;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.LegacyReporting;
import haj.com.service.LegacyReportingService;
import haj.com.entity.datamodel.LegacyReportingDatamodel;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "legacyreportingUI")
@ViewScoped
public class LegacyReportingUI extends AbstractUI {

	private LegacyReportingService service = new LegacyReportingService();
	private List<LegacyReporting> legacyreportingList = null;
	private List<LegacyReporting> legacyreportingfilteredList = null;
	private LegacyReporting legacyreporting = null;
	private LazyDataModel<LegacyReporting> dataModel; 

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
	 * Initialize method to get all LegacyReporting and prepare a for a create of a new LegacyReporting
 	 * @author TechFinium 
 	 * @see    LegacyReporting
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		legacyreportingInfo();
	}

	/**
	 * Get all LegacyReporting for data table
 	 * @author TechFinium 
 	 * @see    LegacyReporting
 	 */
	public void legacyreportingInfo() {
			dataModel = new LegacyReportingDatamodel();
	}

	/**
	 * Insert LegacyReporting into database
 	 * @author TechFinium 
 	 * @see    LegacyReporting
 	 */
	public void legacyreportingInsert() {
		try {
				 service.create(this.legacyreporting);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 legacyreportingInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update LegacyReporting in database
 	 * @author TechFinium 
 	 * @see    LegacyReporting
 	 */
    public void legacyreportingUpdate() {
		try {
				 service.update(this.legacyreporting);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 legacyreportingInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete LegacyReporting from database
 	 * @author TechFinium 
 	 * @see    LegacyReporting
 	 */
	public void legacyreportingDelete() {
		try {
			 service.delete(this.legacyreporting);
			  prepareNew();
			 legacyreportingInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of LegacyReporting 
 	 * @author TechFinium 
 	 * @see    LegacyReporting
 	 */
	public void prepareNew() {
		legacyreporting = new LegacyReporting();
	}

/*
    public List<SelectItem> getLegacyReportingDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	legacyreportingInfo();
    	for (LegacyReporting ug : legacyreportingList) {
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
    public List<LegacyReporting> complete(String desc) {
		List<LegacyReporting> l = null;
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
    
    public List<LegacyReporting> getLegacyReportingList() {
		return legacyreportingList;
	}
	public void setLegacyReportingList(List<LegacyReporting> legacyreportingList) {
		this.legacyreportingList = legacyreportingList;
	}
	public LegacyReporting getLegacyreporting() {
		return legacyreporting;
	}
	public void setLegacyreporting(LegacyReporting legacyreporting) {
		this.legacyreporting = legacyreporting;
	}

    public List<LegacyReporting> getLegacyReportingfilteredList() {
		return legacyreportingfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param legacyreportingfilteredList the new legacyreportingfilteredList list
 	 * @see    LegacyReporting
 	 */	
	public void setLegacyReportingfilteredList(List<LegacyReporting> legacyreportingfilteredList) {
		this.legacyreportingfilteredList = legacyreportingfilteredList;
	}

	
	public LazyDataModel<LegacyReporting> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyReporting> dataModel) {
		this.dataModel = dataModel;
	}
	
}
