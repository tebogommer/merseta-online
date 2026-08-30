package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.CompanyLearnersTransfer;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyLearnersTransferService;

@ManagedBean(name = "companylearnerstransferUI")
@ViewScoped
public class CompanyLearnersTransferUI extends AbstractUI {

	private CompanyLearnersTransferService service = new CompanyLearnersTransferService();
	private List<CompanyLearnersTransfer> companylearnerstransferList = null;
	private List<CompanyLearnersTransfer> companylearnerstransferfilteredList = null;
	private CompanyLearnersTransfer companylearnerstransfer = null;
	private LazyDataModel<CompanyLearnersTransfer> dataModel; 

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
	 * Initialize method to get all CompanyLearnersTransfer and prepare a for a create of a new CompanyLearnersTransfer
 	 * @author TechFinium 
 	 * @see    CompanyLearnersTransfer
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		companylearnerstransferInfo();
	}

	/**
	 * Get all CompanyLearnersTransfer for data table
 	 * @author TechFinium 
 	 * @see    CompanyLearnersTransfer
 	 */
	public void companylearnerstransferInfo() {
	 
			
			 dataModel = new LazyDataModel<CompanyLearnersTransfer>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<CompanyLearnersTransfer> retorno = new  ArrayList<CompanyLearnersTransfer>();
			   
			   @Override 
			   public List<CompanyLearnersTransfer> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allCompanyLearnersTransfer(CompanyLearnersTransfer.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(CompanyLearnersTransfer.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(CompanyLearnersTransfer obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public CompanyLearnersTransfer getRowData(String rowKey) {
			        for(CompanyLearnersTransfer obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert CompanyLearnersTransfer into database
 	 * @author TechFinium 
 	 * @see    CompanyLearnersTransfer
 	 */
	public void companylearnerstransferInsert() {
		try {
				 service.create(this.companylearnerstransfer);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 companylearnerstransferInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update CompanyLearnersTransfer in database
 	 * @author TechFinium 
 	 * @see    CompanyLearnersTransfer
 	 */
    public void companylearnerstransferUpdate() {
		try {
				 service.update(this.companylearnerstransfer);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 companylearnerstransferInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete CompanyLearnersTransfer from database
 	 * @author TechFinium 
 	 * @see    CompanyLearnersTransfer
 	 */
	public void companylearnerstransferDelete() {
		try {
			 service.delete(this.companylearnerstransfer);
			  prepareNew();
			 companylearnerstransferInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of CompanyLearnersTransfer 
 	 * @author TechFinium 
 	 * @see    CompanyLearnersTransfer
 	 */
	public void prepareNew() {
		companylearnerstransfer = new CompanyLearnersTransfer();
	}

/*
    public List<SelectItem> getCompanyLearnersTransferDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	companylearnerstransferInfo();
    	for (CompanyLearnersTransfer ug : companylearnerstransferList) {
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
    public List<CompanyLearnersTransfer> complete(String desc) {
		List<CompanyLearnersTransfer> l = null;
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
    
    public List<CompanyLearnersTransfer> getCompanyLearnersTransferList() {
		return companylearnerstransferList;
	}
	public void setCompanyLearnersTransferList(List<CompanyLearnersTransfer> companylearnerstransferList) {
		this.companylearnerstransferList = companylearnerstransferList;
	}
	public CompanyLearnersTransfer getCompanylearnerstransfer() {
		return companylearnerstransfer;
	}
	public void setCompanylearnerstransfer(CompanyLearnersTransfer companylearnerstransfer) {
		this.companylearnerstransfer = companylearnerstransfer;
	}

    public List<CompanyLearnersTransfer> getCompanyLearnersTransferfilteredList() {
		return companylearnerstransferfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param companylearnerstransferfilteredList the new companylearnerstransferfilteredList list
 	 * @see    CompanyLearnersTransfer
 	 */	
	public void setCompanyLearnersTransferfilteredList(List<CompanyLearnersTransfer> companylearnerstransferfilteredList) {
		this.companylearnerstransferfilteredList = companylearnerstransferfilteredList;
	}

	
	public LazyDataModel<CompanyLearnersTransfer> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<CompanyLearnersTransfer> dataModel) {
		this.dataModel = dataModel;
	}
	
}
