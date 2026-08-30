package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.LearnershipDataUpload;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LearnershipDataUploadService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "learnershipdatauploadUI")
@ViewScoped
public class LearnershipDataUploadUI extends AbstractUI {

	private LearnershipDataUploadService service = new LearnershipDataUploadService();
	private List<LearnershipDataUpload> learnershipdatauploadList = null;
	private List<LearnershipDataUpload> learnershipdatauploadfilteredList = null;
	private LearnershipDataUpload learnershipdataupload = null;
	private LazyDataModel<LearnershipDataUpload> dataModel; 
	
	private CSVUtil csvUtil = new CSVUtil();
	private List<String> csvTypeSelectionList = new ArrayList<>();
	private String csvTypeSelection;
	
	private boolean displayDownload = false;

    @PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Initialize method to get all LearnershipDataUpload and prepare a for a create of a new LearnershipDataUpload
 	 * @author TechFinium 
 	 * @see    LearnershipDataUpload
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		learnershipdatauploadInfo();
		countAllEntries();
	}

	/**
	 * Get all LearnershipDataUpload for data table
 	 * @author TechFinium 
 	 * @see    LearnershipDataUpload
 	 */
	public void learnershipdatauploadInfo() {
	 
			
			 dataModel = new LazyDataModel<LearnershipDataUpload>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<LearnershipDataUpload> retorno = new  ArrayList<LearnershipDataUpload>();
			   
			   @Override 
			   public List<LearnershipDataUpload> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allLearnershipDataUpload(LearnershipDataUpload.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(LearnershipDataUpload.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(LearnershipDataUpload obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public LearnershipDataUpload getRowData(String rowKey) {
			        for(LearnershipDataUpload obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert LearnershipDataUpload into database
 	 * @author TechFinium 
 	 * @see    LearnershipDataUpload
 	 */
	public void learnershipdatauploadInsert() {
		try {
				 service.create(this.learnershipdataupload);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 learnershipdatauploadInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update LearnershipDataUpload in database
 	 * @author TechFinium 
 	 * @see    LearnershipDataUpload
 	 */
    public void learnershipdatauploadUpdate() {
		try {
				 service.update(this.learnershipdataupload);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 learnershipdatauploadInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete LearnershipDataUpload from database
 	 * @author TechFinium 
 	 * @see    LearnershipDataUpload
 	 */
	public void learnershipdatauploadDelete() {
		try {
			 service.delete(this.learnershipdataupload);
			  prepareNew();
			 learnershipdatauploadInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of LearnershipDataUpload 
 	 * @author TechFinium 
 	 * @see    LearnershipDataUpload
 	 */
	public void prepareNew() {
		learnershipdataupload = new LearnershipDataUpload();
	}

/*
    public List<SelectItem> getLearnershipDataUploadDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	learnershipdatauploadInfo();
    	for (LearnershipDataUpload ug : learnershipdatauploadList) {
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
    public List<LearnershipDataUpload> complete(String desc) {
		List<LearnershipDataUpload> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		}
		catch (Exception e) {
		addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
    
    public List<LearnershipDataUpload> getLearnershipDataUploadList() {
		return learnershipdatauploadList;
	}
	public void setLearnershipDataUploadList(List<LearnershipDataUpload> learnershipdatauploadList) {
		this.learnershipdatauploadList = learnershipdatauploadList;
	}
	public LearnershipDataUpload getLearnershipdataupload() {
		return learnershipdataupload;
	}
	public void setLearnershipdataupload(LearnershipDataUpload learnershipdataupload) {
		this.learnershipdataupload = learnershipdataupload;
	}

    public List<LearnershipDataUpload> getLearnershipDataUploadfilteredList() {
		return learnershipdatauploadfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param learnershipdatauploadfilteredList the new learnershipdatauploadfilteredList list
 	 * @see    LearnershipDataUpload
 	 */	
	public void setLearnershipDataUploadfilteredList(List<LearnershipDataUpload> learnershipdatauploadfilteredList) {
		this.learnershipdatauploadfilteredList = learnershipdatauploadfilteredList;
	}

	
	public LazyDataModel<LearnershipDataUpload> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LearnershipDataUpload> dataModel) {
		this.dataModel = dataModel;
	}
	
	public void prepTypeSelection() {
		try {
			csvTypeSelectionList = new ArrayList<>();
			csvTypeSelectionList.add(",");
			csvTypeSelectionList.add(";");
			csvTypeSelectionList.add("|");
			csvTypeSelectionList.add("-");
			csvTypeSelection = ",";
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		try {
			if (csvTypeSelection == null || csvTypeSelection.isEmpty()) {
				csvTypeSelection = ",";
			}
			logger.info("Starting file upload");
			List<LearnershipDataUpload> csvDataList = csvUtil.getObjects(LearnershipDataUpload.class,event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			learnershipdatauploadInfo();
			countAllEntries();
		} catch (ConstraintViolationException e) {
			addErrorMessage("ConstraintViolationException");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		} finally {
			csvUtil = new CSVUtil();
		}
	}

	public void clearUploadedEntries() {
		try {
			service.deleteUploadedEntries();
			addInfoMessage("data cleared");
			learnershipdatauploadInfo();
			countAllEntries();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void syncLearnershipData(){
		try {
			service.syncLearnershipInformation();
			addInfoMessage("Action Complete");
			learnershipdatauploadInfo();
			countAllEntries();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void countAllEntries(){
		try {
			displayDownload = false;
	        int entriesCount = service.countAllResults();
	        if (entriesCount < 65000) {
	            displayDownload = true;
	        }
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public List<String> getCsvTypeSelectionList() {
		return csvTypeSelectionList;
	}

	public void setCsvTypeSelectionList(List<String> csvTypeSelectionList) {
		this.csvTypeSelectionList = csvTypeSelectionList;
	}

	public String getCsvTypeSelection() {
		return csvTypeSelection;
	}

	public void setCsvTypeSelection(String csvTypeSelection) {
		this.csvTypeSelection = csvTypeSelection;
	}

	public boolean isDisplayDownload() {
		return displayDownload;
	}

	public void setDisplayDownload(boolean displayDownload) {
		this.displayDownload = displayDownload;
	}
	
}
