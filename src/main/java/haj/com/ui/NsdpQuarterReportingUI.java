package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.NsdpQuarterReporting;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.NsdpQuarterReportingService;

@ManagedBean(name = "nsdpquarterreportingUI")
@ViewScoped
public class NsdpQuarterReportingUI extends AbstractUI {

	private NsdpQuarterReportingService service = new NsdpQuarterReportingService();
	private List<NsdpQuarterReporting> nsdpquarterreportingList = null;
	private List<NsdpQuarterReporting> nsdpquarterreportingfilteredList = null;
	private NsdpQuarterReporting nsdpquarterreporting = null;
	private LazyDataModel<NsdpQuarterReporting> dataModel; 

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
	 * Initialize method to get all NsdpQuarterReporting and prepare a for a create of a new NsdpQuarterReporting
 	 * @author TechFinium 
 	 * @see    NsdpQuarterReporting
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		nsdpquarterreportingInfo();
	}

	/**
	 * Get all NsdpQuarterReporting for data table
 	 * @author TechFinium 
 	 * @see    NsdpQuarterReporting
 	 */
	public void nsdpquarterreportingInfo() {
//			dataModel = new NsdpQuarterReportingDatamodel();
	}

	/**
	 * Insert NsdpQuarterReporting into database
 	 * @author TechFinium 
 	 * @see    NsdpQuarterReporting
 	 */
	public void nsdpquarterreportingInsert() {
		try {
				 service.create(this.nsdpquarterreporting);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 nsdpquarterreportingInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update NsdpQuarterReporting in database
 	 * @author TechFinium 
 	 * @see    NsdpQuarterReporting
 	 */
    public void nsdpquarterreportingUpdate() {
		try {
				 service.update(this.nsdpquarterreporting);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 nsdpquarterreportingInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete NsdpQuarterReporting from database
 	 * @author TechFinium 
 	 * @see    NsdpQuarterReporting
 	 */
	public void nsdpquarterreportingDelete() {
		try {
			 service.delete(this.nsdpquarterreporting);
			  prepareNew();
			 nsdpquarterreportingInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of NsdpQuarterReporting 
 	 * @author TechFinium 
 	 * @see    NsdpQuarterReporting
 	 */
	public void prepareNew() {
		nsdpquarterreporting = new NsdpQuarterReporting();
	}

/*
    public List<SelectItem> getNsdpQuarterReportingDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	nsdpquarterreportingInfo();
    	for (NsdpQuarterReporting ug : nsdpquarterreportingList) {
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
    public List<NsdpQuarterReporting> complete(String desc) {
		List<NsdpQuarterReporting> l = null;
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
    
    public List<NsdpQuarterReporting> getNsdpQuarterReportingList() {
		return nsdpquarterreportingList;
	}
	public void setNsdpQuarterReportingList(List<NsdpQuarterReporting> nsdpquarterreportingList) {
		this.nsdpquarterreportingList = nsdpquarterreportingList;
	}
	public NsdpQuarterReporting getNsdpquarterreporting() {
		return nsdpquarterreporting;
	}
	public void setNsdpquarterreporting(NsdpQuarterReporting nsdpquarterreporting) {
		this.nsdpquarterreporting = nsdpquarterreporting;
	}

    public List<NsdpQuarterReporting> getNsdpQuarterReportingfilteredList() {
		return nsdpquarterreportingfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param nsdpquarterreportingfilteredList the new nsdpquarterreportingfilteredList list
 	 * @see    NsdpQuarterReporting
 	 */	
	public void setNsdpQuarterReportingfilteredList(List<NsdpQuarterReporting> nsdpquarterreportingfilteredList) {
		this.nsdpquarterreportingfilteredList = nsdpquarterreportingfilteredList;
	}

	
	public LazyDataModel<NsdpQuarterReporting> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<NsdpQuarterReporting> dataModel) {
		this.dataModel = dataModel;
	}
	
}
