package  haj.com.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.microsoft.schemas.dynamics.gp._2006._01.GLTransaction;

import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.gpservices.GPPrepTransactionsService;
import haj.com.sars.DHETService;
import haj.com.sars.SarsFiles;
import haj.com.sars.SarsLoadLevies;
import haj.com.service.SarsFilesService;
import haj.com.service.SarsLeviesPaidService;
import haj.com.utils.GenericUtility;
import za.co.merseta.nsdms.externalsystems.greatplains.dynamicgp.adapter.CreateGLTransactionAdapter;
import za.co.merseta.nsdms.framework.logging.NSDMSLogger;

// TODO: Auto-generated Javadoc
/**
 * The Class SarsFilesUI.
 */
@ManagedBean(name = "sarsfilesUI")
@ViewScoped
public class SarsFilesUI extends AbstractUI {
	private static final NSDMSLogger logger = NSDMSLogger.getLogger(SarsFilesUI.class);
	/** The service. */
	private SarsFilesService service = new SarsFilesService();
	private SarsLeviesPaidService sarsLeviesPaidService = new SarsLeviesPaidService();
	/** The sarsfiles list. */
	private List<SarsFiles> sarsfilesList = null;
	
	/** The sarsfilesfiltered list. */
	private List<SarsFiles> sarsfilesfilteredList = null;
	
	/** The sarsfiles. */
	private SarsFiles sarsfiles = null;
	
	/** The data model. */
	private LazyDataModel<SarsFiles> dataModel; 
    
    /** The sars load levies. */
    private SarsLoadLevies sarsLoadLevies = new SarsLoadLevies();
	
	/** The for month. */
	private Date  forMonth;
	
	/** The max date. */
	private Date maxDate;
	
	private Date fiscalYear;
	
	private Date postDate;
	
	private Date latestFileOnDHET;
	private GPPrepTransactionsService gpPrepTransactionsService = new GPPrepTransactionsService();
    /**
     * Inits the.
     */
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
	 * Initialize method to get all SarsFiles and prepare a for a create of a new SarsFiles.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    SarsFiles
	 */
	private void runInit() throws Exception {
	    forMonth = null;
		maxDate = GenericUtility.getLasttDayOfMonth(new Date());
		try {
			this.latestFileOnDHET = null;
			this.latestFileOnDHET = DHETService.findLatestMonth();
		} catch (Exception e) {
			logger.fatal(e);
			this.latestFileOnDHET = null;
		}
		prepareNew();
		sarsfilesInfo();
		sarsLeviesPaidService.sarsLeviesPaidWithNoLevyFileLink();
	}

	/**
	 * Get all SarsFiles for data table.
	 *
	 * @author TechFinium
	 * @see    SarsFiles
	 */
	public void sarsfilesInfo() {
	 
			
			 dataModel = new LazyDataModel<SarsFiles>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<SarsFiles> retorno = new  ArrayList<SarsFiles>();
			   
			   @Override 
			   public List<SarsFiles> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				   try {
					if (sortField==null) {
						sortField = "forMonth";
						sortOrder  = sortOrder.DESCENDING;
					}
					retorno = service.allSarsFiles(SarsFiles.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(SarsFiles.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
					e.printStackTrace();
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(SarsFiles obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public SarsFiles getRowData(String rowKey) {
			        for(SarsFiles obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert SarsFiles into database.
	 *
	 * @author TechFinium
	 * @see    SarsFiles
	 */
	public void sarsfilesInsert() {
		try {
				 service.create(this.sarsfiles);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 sarsfilesInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update SarsFiles in database.
	 *
	 * @author TechFinium
	 * @see    SarsFiles
	 */
    public void sarsfilesUpdate() {
		try {
				 service.update(this.sarsfiles);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 sarsfilesInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete SarsFiles from database.
	 *
	 * @author TechFinium
	 * @see    SarsFiles
	 */
	public void sarsfilesDelete() {
		try {
			 service.delete(this.sarsfiles);
			  prepareNew();
			 sarsfilesInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of SarsFiles .
	 *
	 * @author TechFinium
	 * @see    SarsFiles
	 */
	public void prepareNew() {
		sarsfiles = new SarsFiles();
	}

/*
    public List<SelectItem> getSarsFilesDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	sarsfilesInfo();
    	for (SarsFiles ug : sarsfilesList) {
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
    public List<SarsFiles> complete(String desc) {
		List<SarsFiles> l = null;
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
    
    /**
     * Gets the sars files list.
     *
     * @return the sars files list
     */
    public List<SarsFiles> getSarsFilesList() {
		return sarsfilesList;
	}
	
	/**
	 * Sets the sars files list.
	 *
	 * @param sarsfilesList the new sars files list
	 */
	public void setSarsFilesList(List<SarsFiles> sarsfilesList) {
		this.sarsfilesList = sarsfilesList;
	}
	
	/**
	 * Gets the sarsfiles.
	 *
	 * @return the sarsfiles
	 */
	public SarsFiles getSarsfiles() {
		return sarsfiles;
	}
	
	/**
	 * Sets the sarsfiles.
	 *
	 * @param sarsfiles the new sarsfiles
	 */
	public void setSarsfiles(SarsFiles sarsfiles) {
		this.sarsfiles = sarsfiles;
	}

    /**
     * Gets the sars filesfiltered list.
     *
     * @return the sars filesfiltered list
     */
    public List<SarsFiles> getSarsFilesfilteredList() {
		return sarsfilesfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param sarsfilesfilteredList the new sarsfilesfilteredList list
	 * @see    SarsFiles
	 */	
	public void setSarsFilesfilteredList(List<SarsFiles> sarsfilesfilteredList) {
		this.sarsfilesfilteredList = sarsfilesfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<SarsFiles> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<SarsFiles> dataModel) {
		this.dataModel = dataModel;
	}
	
	/**
	 * Show employers.
	 */
	public void showEmployers() {
		try {
			getSessionUI().setSarsFiles(this.sarsfiles);
			super.ajaxRedirect("/admin/sarsemployerdetail.jsf");
		
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Gets the for month.
	 *
	 * @return the for month
	 */
	public Date getForMonth() {
		return forMonth;
	}

	/**
	 * Sets the for month.
	 *
	 * @param forMonth the new for month
	 */
	public void setForMonth(Date forMonth) {
		this.forMonth = forMonth;
	}

	/**
	 * Gets the max date.
	 *
	 * @return the max date
	 */
	public Date getMaxDate() {
		return maxDate;
	}

	/**
	 * Sets the max date.
	 *
	 * @param maxDate the new max date
	 */
	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}
	

	/**
	 * Handle file upload.
	 *
	 * @param event the event
	 */
	public void handleFileUpload(FileUploadEvent event) {
		try {
			forMonth = GenericUtility.getFirstDayOfMonth(forMonth);
			sarsLoadLevies.saveFile(event.getFile().getInputstream(), event.getFile().getFileName(),this.forMonth);
			runInit();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepForPostGPData() {
		try {
			this.postDate =	sarsLeviesPaidService.findpostingDateBySarsFile(sarsfiles);
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void postGPData() {
		logger.info("postGPData() - {}", "START");
		try {
			logger.debug("Retrieving GL Transaction list");
			List<GLTransaction> glTransactionList = gpPrepTransactionsService.createGLTransaction(this.sarsfiles.getId().intValue(),postDate);
			logger.debug("Processing GP Transaction List");
			for (GLTransaction glTransaction : glTransactionList) {
				CreateGLTransactionAdapter adapter = new CreateGLTransactionAdapter();
				adapter.createGLTransaction(glTransaction);
			}
			sarsfilesInfo();
			
		} catch (Exception e) {
			logger.error("Failed to processing GL Transaction List", e);
			addErrorMessage(e.getMessage(), e);
		}
		logger.info("postGPData() - {}", "END");
	}

	public Date getFiscalYear() {
		return fiscalYear;
	}

	public void setFiscalYear(Date fiscalYear) {
		this.fiscalYear = fiscalYear;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public Date getLatestFileOnDHET() {
		return latestFileOnDHET;
	}

	public void setLatestFileOnDHET(Date latestFileOnDHET) {
		this.latestFileOnDHET = latestFileOnDHET;
	}
}
