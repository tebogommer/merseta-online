package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.report.bean.ByChamberReportBean;
import haj.com.sars.SarsEmployerDetail;
import haj.com.service.ReportService;
import haj.com.service.SarsEmployerDetailService;

// TODO: Auto-generated Javadoc
/**
 * The Class SarsEmployerDetailUI.
 */
@ManagedBean(name = "sarsemployerdetailUI")
@ViewScoped
public class SarsEmployerDetailUI extends AbstractUI {

	/** The service. */
	private SarsEmployerDetailService service = new SarsEmployerDetailService();
	
	/** The sarsemployerdetail list. */
	private List<SarsEmployerDetail> sarsemployerdetailList = null;
	
	/** The sarsemployerdetailfiltered list. */
	private List<SarsEmployerDetail> sarsemployerdetailfilteredList = null;
	
	/** The sarsemployerdetail. */
	private SarsEmployerDetail sarsemployerdetail = null;
	
	/** The data model. */
	private LazyDataModel<SarsEmployerDetail> dataModel; 
	
	private SarsEmployerDetail sarsemployerdetailUpdate = null;
	private String errorList;
	
	

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
	 * Initialize method to get all SarsEmployerDetail and prepare a for a create of a new SarsEmployerDetail.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    SarsEmployerDetail
	 */
	private void runInit() throws Exception {
		prepareNew();
		sarsemployerdetailInfo();
		
	}

	/**
	 * Get all SarsEmployerDetail for data table.
	 *
	 * @author TechFinium
	 * @see    SarsEmployerDetail
	 */
	public void sarsemployerdetailInfo() {
	 
			
			 dataModel = new LazyDataModel<SarsEmployerDetail>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<SarsEmployerDetail> retorno = new  ArrayList<SarsEmployerDetail>();
			   
			   @Override 
			   public List<SarsEmployerDetail> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					if (getSessionUI().getSarsFiles() != null) {
						retorno = service.allSarsEmployerDetail(SarsEmployerDetail.class,first,pageSize,sortField,sortOrder,filters,getSessionUI().getSarsFiles());
						dataModel.setRowCount(service.count(SarsEmployerDetail.class,filters,getSessionUI().getSarsFiles()));
					}
				
					
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(SarsEmployerDetail obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public SarsEmployerDetail getRowData(String rowKey) {
			        for(SarsEmployerDetail obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert SarsEmployerDetail into database.
	 *
	 * @author TechFinium
	 * @see    SarsEmployerDetail
	 */
	public void sarsemployerdetailInsert() {
		try {
				 service.create(this.sarsemployerdetail);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 sarsemployerdetailInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update SarsEmployerDetail in database.
	 *
	 * @author TechFinium
	 * @see    SarsEmployerDetail
	 */
    public void sarsemployerdetailUpdate() {
		try {
				 service.update(this.sarsemployerdetail);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 sarsemployerdetailInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete SarsEmployerDetail from database.
	 *
	 * @author TechFinium
	 * @see    SarsEmployerDetail
	 */
	public void sarsemployerdetailDelete() {
		try {
			 service.delete(this.sarsemployerdetail);
			  prepareNew();
			 sarsemployerdetailInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of SarsEmployerDetail .
	 *
	 * @author TechFinium
	 * @see    SarsEmployerDetail
	 */
	public void prepareNew() {
		sarsemployerdetail = new SarsEmployerDetail();
	}
	
//	getSessionUI().getSarsFiles()
	
	public void runChecks(){
		try {
			errorList = "";
			errorList = service.testValidiationsForCompanyInsert(getSessionUI().getSarsFiles().getId());
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void runEmployerImport(){
		try {
			service.populateNewFromSARS(getSessionUI().getSarsFiles().getId());
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void updateEntry(){
		try {
			service.updateAndCreateAuditUpdate(sarsemployerdetailUpdate, getSessionUI().getActiveUser().getId());
			sarsemployerdetailUpdate = null;
			sarsemployerdetailInfo();
			runClientSideExecute("PF('dlgEmployerDetailUpdate').hide()");
			addInfoMessage("Update Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

/*
    public List<SelectItem> getSarsEmployerDetailDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	sarsemployerdetailInfo();
    	for (SarsEmployerDetail ug : sarsemployerdetailList) {
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
    public List<SarsEmployerDetail> complete(String desc) {
		List<SarsEmployerDetail> l = null;
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
     * Gets the sars employer detail list.
     *
     * @return the sars employer detail list
     */
    public List<SarsEmployerDetail> getSarsEmployerDetailList() {
		return sarsemployerdetailList;
	}
	
	/**
	 * Sets the sars employer detail list.
	 *
	 * @param sarsemployerdetailList the new sars employer detail list
	 */
	public void setSarsEmployerDetailList(List<SarsEmployerDetail> sarsemployerdetailList) {
		this.sarsemployerdetailList = sarsemployerdetailList;
	}
	
	/**
	 * Gets the sarsemployerdetail.
	 *
	 * @return the sarsemployerdetail
	 */
	public SarsEmployerDetail getSarsemployerdetail() {
		return sarsemployerdetail;
	}
	
	/**
	 * Sets the sarsemployerdetail.
	 *
	 * @param sarsemployerdetail the new sarsemployerdetail
	 */
	public void setSarsemployerdetail(SarsEmployerDetail sarsemployerdetail) {
		this.sarsemployerdetail = sarsemployerdetail;
	}

    /**
     * Gets the sars employer detailfiltered list.
     *
     * @return the sars employer detailfiltered list
     */
    public List<SarsEmployerDetail> getSarsEmployerDetailfilteredList() {
		return sarsemployerdetailfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param sarsemployerdetailfilteredList the new sarsemployerdetailfilteredList list
	 * @see    SarsEmployerDetail
	 */	
	public void setSarsEmployerDetailfilteredList(List<SarsEmployerDetail> sarsemployerdetailfilteredList) {
		this.sarsemployerdetailfilteredList = sarsemployerdetailfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<SarsEmployerDetail> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<SarsEmployerDetail> dataModel) {
		this.dataModel = dataModel;
	}

	public String getErrorList() {
		return errorList;
	}

	public void setErrorList(String errorList) {
		this.errorList = errorList;
	}

	public SarsEmployerDetail getSarsemployerdetailUpdate() {
		return sarsemployerdetailUpdate;
	}

	public void setSarsemployerdetailUpdate(SarsEmployerDetail sarsemployerdetailUpdate) {
		this.sarsemployerdetailUpdate = sarsemployerdetailUpdate;
	}

	
}
