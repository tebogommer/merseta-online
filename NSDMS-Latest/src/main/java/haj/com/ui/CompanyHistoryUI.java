package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.CompanyHistory;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyHistoryService;

@ManagedBean(name = "companyhistoryUI")
@ViewScoped
public class CompanyHistoryUI extends AbstractUI {

	private CompanyHistoryService service = new CompanyHistoryService();
	private List<CompanyHistory> companyhistoryList = null;
	private List<CompanyHistory> companyhistoryfilteredList = null;
	private CompanyHistory companyhistory = null;
	private LazyDataModel<CompanyHistory> dataModel; 

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
	 * Initialize method to get all CompanyHistory and prepare a for a create of a new CompanyHistory
 	 * @author TechFinium 
 	 * @see    CompanyHistory
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		companyhistoryInfo();
	}

	/**
	 * Get all CompanyHistory for data table
 	 * @author TechFinium 
 	 * @see    CompanyHistory
 	 */
	public void companyhistoryInfo() {
	 
			
			 dataModel = new LazyDataModel<CompanyHistory>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<CompanyHistory> retorno = new  ArrayList<CompanyHistory>();
			   
			   @Override 
			   public List<CompanyHistory> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allCompanyHistory(CompanyHistory.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(CompanyHistory.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(CompanyHistory obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public CompanyHistory getRowData(String rowKey) {
			        for(CompanyHistory obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert CompanyHistory into database
 	 * @author TechFinium 
 	 * @see    CompanyHistory
 	 */
	public void companyhistoryInsert() {
		try {
				 service.create(this.companyhistory);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 companyhistoryInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update CompanyHistory in database
 	 * @author TechFinium 
 	 * @see    CompanyHistory
 	 */
    public void companyhistoryUpdate() {
		try {
				 service.update(this.companyhistory);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 companyhistoryInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete CompanyHistory from database
 	 * @author TechFinium 
 	 * @see    CompanyHistory
 	 */
	public void companyhistoryDelete() {
		try {
			 service.delete(this.companyhistory);
			  prepareNew();
			 companyhistoryInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of CompanyHistory 
 	 * @author TechFinium 
 	 * @see    CompanyHistory
 	 */
	public void prepareNew() {
		companyhistory = new CompanyHistory();
	}

/*
    public List<SelectItem> getCompanyHistoryDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	companyhistoryInfo();
    	for (CompanyHistory ug : companyhistoryList) {
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
    public List<CompanyHistory> complete(String desc) {
		List<CompanyHistory> l = null;
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
    
    public List<CompanyHistory> getCompanyHistoryList() {
		return companyhistoryList;
	}
	public void setCompanyHistoryList(List<CompanyHistory> companyhistoryList) {
		this.companyhistoryList = companyhistoryList;
	}
	public CompanyHistory getCompanyhistory() {
		return companyhistory;
	}
	public void setCompanyhistory(CompanyHistory companyhistory) {
		this.companyhistory = companyhistory;
	}

    public List<CompanyHistory> getCompanyHistoryfilteredList() {
		return companyhistoryfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param companyhistoryfilteredList the new companyhistoryfilteredList list
 	 * @see    CompanyHistory
 	 */	
	public void setCompanyHistoryfilteredList(List<CompanyHistory> companyhistoryfilteredList) {
		this.companyhistoryfilteredList = companyhistoryfilteredList;
	}

	
	public LazyDataModel<CompanyHistory> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<CompanyHistory> dataModel) {
		this.dataModel = dataModel;
	}
	
}
