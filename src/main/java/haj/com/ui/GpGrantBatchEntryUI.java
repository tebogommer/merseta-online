package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.GpGrantBatchEntry;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.GpGrantBatchEntryService;

@ManagedBean(name = "gpgrantbatchentryUI")
@ViewScoped
public class GpGrantBatchEntryUI extends AbstractUI {

	private GpGrantBatchEntryService service = new GpGrantBatchEntryService();
	private List<GpGrantBatchEntry> gpgrantbatchentryList = null;
	private List<GpGrantBatchEntry> gpgrantbatchentryfilteredList = null;
	private GpGrantBatchEntry gpgrantbatchentry = null;
	private LazyDataModel<GpGrantBatchEntry> dataModel; 

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
	 * Initialize method to get all GpGrantBatchEntry and prepare a for a create of a new GpGrantBatchEntry
 	 * @author TechFinium 
 	 * @see    GpGrantBatchEntry
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		gpgrantbatchentryInfo();
	}

	/**
	 * Get all GpGrantBatchEntry for data table
 	 * @author TechFinium 
 	 * @see    GpGrantBatchEntry
 	 */
	public void gpgrantbatchentryInfo() {
//			dataModel = new GpGrantBatchEntryDatamodel();
	}

	/**
	 * Insert GpGrantBatchEntry into database
 	 * @author TechFinium 
 	 * @see    GpGrantBatchEntry
 	 */
	public void gpgrantbatchentryInsert() {
		try {
				 service.create(this.gpgrantbatchentry);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 gpgrantbatchentryInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update GpGrantBatchEntry in database
 	 * @author TechFinium 
 	 * @see    GpGrantBatchEntry
 	 */
    public void gpgrantbatchentryUpdate() {
		try {
				 service.update(this.gpgrantbatchentry);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 gpgrantbatchentryInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete GpGrantBatchEntry from database
 	 * @author TechFinium 
 	 * @see    GpGrantBatchEntry
 	 */
	public void gpgrantbatchentryDelete() {
		try {
			 service.delete(this.gpgrantbatchentry);
			  prepareNew();
			 gpgrantbatchentryInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of GpGrantBatchEntry 
 	 * @author TechFinium 
 	 * @see    GpGrantBatchEntry
 	 */
	public void prepareNew() {
		gpgrantbatchentry = new GpGrantBatchEntry();
	}

/*
    public List<SelectItem> getGpGrantBatchEntryDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	gpgrantbatchentryInfo();
    	for (GpGrantBatchEntry ug : gpgrantbatchentryList) {
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
    public List<GpGrantBatchEntry> complete(String desc) {
		List<GpGrantBatchEntry> l = null;
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
    
    public List<GpGrantBatchEntry> getGpGrantBatchEntryList() {
		return gpgrantbatchentryList;
	}
	public void setGpGrantBatchEntryList(List<GpGrantBatchEntry> gpgrantbatchentryList) {
		this.gpgrantbatchentryList = gpgrantbatchentryList;
	}
	public GpGrantBatchEntry getGpgrantbatchentry() {
		return gpgrantbatchentry;
	}
	public void setGpgrantbatchentry(GpGrantBatchEntry gpgrantbatchentry) {
		this.gpgrantbatchentry = gpgrantbatchentry;
	}

    public List<GpGrantBatchEntry> getGpGrantBatchEntryfilteredList() {
		return gpgrantbatchentryfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param gpgrantbatchentryfilteredList the new gpgrantbatchentryfilteredList list
 	 * @see    GpGrantBatchEntry
 	 */	
	public void setGpGrantBatchEntryfilteredList(List<GpGrantBatchEntry> gpgrantbatchentryfilteredList) {
		this.gpgrantbatchentryfilteredList = gpgrantbatchentryfilteredList;
	}

	
	public LazyDataModel<GpGrantBatchEntry> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<GpGrantBatchEntry> dataModel) {
		this.dataModel = dataModel;
	}
	
}
