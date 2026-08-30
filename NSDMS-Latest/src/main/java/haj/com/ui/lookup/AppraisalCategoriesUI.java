package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.AppraisalCategories;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.AppraisalCategoriesService;

@ManagedBean(name = "appraisalCategoriesUI")
@ViewScoped
public class AppraisalCategoriesUI extends AbstractUI {

	private AppraisalCategoriesService service = new AppraisalCategoriesService();
	private List<AppraisalCategories> AppraisalCategoriesList = null;
	private List<AppraisalCategories> AppraisalCategoriesfilteredList = null;
	private AppraisalCategories AppraisalCategories = null;
	private LazyDataModel<AppraisalCategories> dataModel; 

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
	 * Initialize method to get all AppraisalCategories and prepare a for a create of a new AppraisalCategories
 	 * @author TechFinium 
 	 * @see    AppraisalCategories
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		AppraisalCategoriesInfo();
	}

	/**
	 * Get all AppraisalCategories for data table
 	 * @author TechFinium 
 	 * @see    AppraisalCategories
 	 */
	public void AppraisalCategoriesInfo() {
	 
			
			 dataModel = new LazyDataModel<AppraisalCategories>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<AppraisalCategories> retorno = new  ArrayList<AppraisalCategories>();
			   
			   @Override 
			   public List<AppraisalCategories> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allAppraisalCategories(AppraisalCategories.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(AppraisalCategories.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(AppraisalCategories obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public AppraisalCategories getRowData(String rowKey) {
			        for(AppraisalCategories obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	public void appraisalCategoriesInsert()
	{
		try {
			 service.create(this.AppraisalCategories);
			 prepareNew();
			 addInfoMessage(super.getEntryLanguage("update.successful"));
			 AppraisalCategoriesInfo();
		 } catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
	 	   super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Update AppraisalCategories in database
 	 * @author TechFinium 
 	 * @see    AppraisalCategories
 	 */
    public void appraisalCategoriesUpdate() {
		try {
				 service.update(this.AppraisalCategories);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 AppraisalCategoriesInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete AppraisalCategories from database
 	 * @author TechFinium 
 	 * @see    AppraisalCategories
 	 */
	public void appraisalCategoriesDelete() {
		try {
			 service.delete(this.AppraisalCategories);
			  prepareNew();
			 AppraisalCategoriesInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of AppraisalCategories 
 	 * @author TechFinium 
 	 * @see    AppraisalCategories
 	 */
	public void prepareNew() {
		AppraisalCategories = new AppraisalCategories();
	}

/*
    public List<SelectItem> getAppraisalCategoriesDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	AppraisalCategoriesInfo();
    	for (AppraisalCategories ug : AppraisalCategoriesList) {
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
    public List<AppraisalCategories> complete(String desc) {
		List<AppraisalCategories> l = null;
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
    
    public List<AppraisalCategories> getAppraisalCategoriesList() {
		return AppraisalCategoriesList;
	}
	public void setAppraisalCategoriesList(List<AppraisalCategories> AppraisalCategoriesList) {
		this.AppraisalCategoriesList = AppraisalCategoriesList;
	}


    public List<AppraisalCategories> getAppraisalCategoriesfilteredList() {
		return AppraisalCategoriesfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param AppraisalCategoriesfilteredList the new AppraisalCategoriesfilteredList list
 	 * @see    AppraisalCategories
 	 */	
	public void setAppraisalCategoriesfilteredList(List<AppraisalCategories> AppraisalCategoriesfilteredList) {
		this.AppraisalCategoriesfilteredList = AppraisalCategoriesfilteredList;
	}

	
	public LazyDataModel<AppraisalCategories> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<AppraisalCategories> dataModel) {
		this.dataModel = dataModel;
	}

	public AppraisalCategories getAppraisalCategories() {
		return AppraisalCategories;
	}

	public void setAppraisalCategories(AppraisalCategories AppraisalCategories) {
		this.AppraisalCategories = AppraisalCategories;
	}
	
}
