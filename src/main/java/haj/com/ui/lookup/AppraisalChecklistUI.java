package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.AppraisalCategories;
import haj.com.entity.lookup.AppraisalChecklist;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.AppraisalChecklistService;

@ManagedBean(name = "appraisalChecklistUI")
@ViewScoped
public class AppraisalChecklistUI extends AbstractUI {

	private AppraisalChecklistService service = new AppraisalChecklistService();
	private List<AppraisalChecklist> appraisalChecklistList = null;
	private List<AppraisalChecklist> appraisalChecklistfilteredList = null;
	private AppraisalChecklist appraisalChecklist = null;
	private LazyDataModel<AppraisalChecklist> dataModel; 
	private List<AppraisalCategories> selectedCategories;
	private AppraisalCategories appraisalCategory;

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
	 * Initialize method to get all AppraisalChecklist and prepare a for a create of a new AppraisalChecklist
 	 * @author TechFinium 
 	 * @see    AppraisalChecklist
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		appraisalChecklistInfo();
	}

	/**
	 * Get all AppraisalChecklist for data table
 	 * @author TechFinium 
 	 * @see    AppraisalChecklist
 	 */
	public void appraisalChecklistInfo() {
	 
			
			 dataModel = new LazyDataModel<AppraisalChecklist>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<AppraisalChecklist> retorno = new  ArrayList<AppraisalChecklist>();
			   
			   @Override 
			   public List<AppraisalChecklist> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allAppraisalChecklist(AppraisalChecklist.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(AppraisalChecklist.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(AppraisalChecklist obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public AppraisalChecklist getRowData(String rowKey) {
			        for(AppraisalChecklist obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	public void appraisalChecklistInsert()
	{
		try {
			 service.create(this.appraisalChecklist);
			 prepareNew();
			 addInfoMessage(super.getEntryLanguage("update.successful"));
			 appraisalChecklistInfo();
		 } catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
	 	   super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Update AppraisalChecklist in database
 	 * @author TechFinium 
 	 * @see    AppraisalChecklist
 	 */
    public void appraisalChecklistUpdate() {
		try {
				 service.update(this.appraisalChecklist);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 appraisalChecklistInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete AppraisalChecklist from database
 	 * @author TechFinium 
 	 * @see    AppraisalChecklist
 	 */
	public void appraisalChecklistDelete() {
		try {
			 service.delete(this.appraisalChecklist);
			  prepareNew();
			 appraisalChecklistInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of AppraisalChecklist 
 	 * @author TechFinium 
 	 * @see    AppraisalChecklist
 	 */
	public void prepareNew() {
		appraisalChecklist = new AppraisalChecklist();
		selectedCategories = new ArrayList<>();
	}
	
	
	public void prepCategoryList() {
		//this.selectedCategories = toollist.getToolListTool().stream().map(x -> x.getTool()).collect(Collectors.toList());
	}
	
	public void addToList() {
		this.selectedCategories.add(appraisalCategory);
		this.appraisalCategory = null;
	}

	public void removeFromList() {
		this.selectedCategories.remove(appraisalCategory);
		this.appraisalCategory = null;
	}

/*
    public List<SelectItem> getAppraisalChecklistDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	AppraisalChecklistInfo();
    	for (AppraisalChecklist ug : AppraisalChecklistList) {
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
    public List<AppraisalChecklist> complete(String desc) {
		List<AppraisalChecklist> l = null;
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
    
    public List<AppraisalChecklist> getAppraisalChecklistList() {
		return appraisalChecklistList;
	}
	public void setAppraisalChecklistList(List<AppraisalChecklist> appraisalChecklistList) {
		this.appraisalChecklistList = appraisalChecklistList;
	}


    public List<AppraisalChecklist> getAppraisalChecklistfilteredList() {
		return appraisalChecklistfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param AppraisalChecklistfilteredList the new AppraisalChecklistfilteredList list
 	 * @see    AppraisalChecklist
 	 */	
	public void setAppraisalChecklistfilteredList(List<AppraisalChecklist> appraisalChecklistfilteredList) {
		this.appraisalChecklistfilteredList = appraisalChecklistfilteredList;
	}

	
	public LazyDataModel<AppraisalChecklist> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<AppraisalChecklist> dataModel) {
		this.dataModel = dataModel;
	}

	public AppraisalChecklist getAppraisalChecklist() {
		return appraisalChecklist;
	}

	public void setAppraisalChecklist(AppraisalChecklist appraisalChecklist) {
		this.appraisalChecklist = appraisalChecklist;
	}

	public List<AppraisalCategories> getSelectedCategories() {
		return selectedCategories;
	}

	public void setSelectedCategories(List<AppraisalCategories> selectedCategories) {
		this.selectedCategories = selectedCategories;
	}

	public AppraisalCategories getAppraisalCategory() {
		return appraisalCategory;
	}

	public void setAppraisalCategory(AppraisalCategories appraisalCategory) {
		this.appraisalCategory = appraisalCategory;
	}
	
}
