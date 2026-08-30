package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.AppraisalCategoryCode;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.AppraisalCategoryCodeService;

@ManagedBean(name = "appraisalCategoryCodeUI")
@ViewScoped
public class AppraisalCategoryCodeUI extends AbstractUI {

	private AppraisalCategoryCodeService service = new AppraisalCategoryCodeService();
	private List<AppraisalCategoryCode> appraisalCategoryCodeList = null;
	private List<AppraisalCategoryCode> appraisalCategoryCodefilteredList = null;
	private AppraisalCategoryCode appraisalCategoryCode = null;
	private LazyDataModel<AppraisalCategoryCode> dataModel; 

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
	 * Initialize method to get all AppraisalCategoryCode and prepare a for a create of a new AppraisalCategoryCode
 	 * @author TechFinium 
 	 * @see    AppraisalCategoryCode
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		appraisalCategoryCodeInfo();
	}

	/**
	 * Get all AppraisalCategoryCode for data table
 	 * @author TechFinium 
 	 * @see    AppraisalCategoryCode
 	 */
	public void appraisalCategoryCodeInfo() {
	 
			
			 dataModel = new LazyDataModel<AppraisalCategoryCode>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<AppraisalCategoryCode> retorno = new  ArrayList<AppraisalCategoryCode>();
			   
			   @Override 
			   public List<AppraisalCategoryCode> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allAppraisalCategoryCode(AppraisalCategoryCode.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(AppraisalCategoryCode.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(AppraisalCategoryCode obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public AppraisalCategoryCode getRowData(String rowKey) {
			        for(AppraisalCategoryCode obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	public void appraisalCategoryCodeInsert()
	{
		try {
			 service.create(this.appraisalCategoryCode);
			 prepareNew();
			 addInfoMessage(super.getEntryLanguage("update.successful"));
			 appraisalCategoryCodeInfo();
		 } catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
	 	   super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Update AppraisalCategoryCode in database
 	 * @author TechFinium 
 	 * @see    AppraisalCategoryCode
 	 */
    public void appraisalCategoryCodeUpdate() {
		try {
				 service.update(this.appraisalCategoryCode);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 appraisalCategoryCodeInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete AppraisalCategoryCode from database
 	 * @author TechFinium 
 	 * @see    AppraisalCategoryCode
 	 */
	public void appraisalCategoryCodeDelete() {
		try {
			 service.delete(this.appraisalCategoryCode);
			  prepareNew();
			 appraisalCategoryCodeInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of AppraisalCategoryCode 
 	 * @author TechFinium 
 	 * @see    AppraisalCategoryCode
 	 */
	public void prepareNew() {
		appraisalCategoryCode = new AppraisalCategoryCode();
	}

/*
    public List<SelectItem> getAppraisalCategoryCodeDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	AppraisalCategoryCodeInfo();
    	for (AppraisalCategoryCode ug : AppraisalCategoryCodeList) {
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
    public List<AppraisalCategoryCode> complete(String desc) {
		List<AppraisalCategoryCode> l = null;
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
    
    public List<AppraisalCategoryCode> getAppraisalCategoryCodeList() {
		return appraisalCategoryCodeList;
	}
	public void setAppraisalCategoryCodeList(List<AppraisalCategoryCode> appraisalCategoryCodeList) {
		this.appraisalCategoryCodeList = appraisalCategoryCodeList;
	}


    public List<AppraisalCategoryCode> getAppraisalCategoryCodefilteredList() {
		return appraisalCategoryCodefilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param AppraisalCategoryCodefilteredList the new AppraisalCategoryCodefilteredList list
 	 * @see    AppraisalCategoryCode
 	 */	
	public void setAppraisalCategoryCodefilteredList(List<AppraisalCategoryCode> appraisalCategoryCodefilteredList) {
		this.appraisalCategoryCodefilteredList = appraisalCategoryCodefilteredList;
	}

	
	public LazyDataModel<AppraisalCategoryCode> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<AppraisalCategoryCode> dataModel) {
		this.dataModel = dataModel;
	}

	public AppraisalCategoryCode getAppraisalCategoryCode() {
		return appraisalCategoryCode;
	}

	public void setAppraisalCategoryCode(AppraisalCategoryCode appraisalCategoryCode) {
		this.appraisalCategoryCode = appraisalCategoryCode;
	}
	
}
