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
import haj.com.entity.lookup.AppraisalCategoryCode;
import haj.com.entity.lookup.AppraisalChecklist;
import haj.com.entity.lookup.Appraisals;
import haj.com.entity.lookup.ToolList;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.AppraisalCategoryCodeService;
import haj.com.service.lookup.AppraisalChecklistService;
import haj.com.service.lookup.AppraisalsService;

@ManagedBean(name = "appraisalsUI")
@ViewScoped
public class AppraisalsUI extends AbstractUI {

	private AppraisalsService service = new AppraisalsService();
	private AppraisalChecklistService appraisalChecklistService = new AppraisalChecklistService();
	private List<Appraisals> appraisalsList = null;
	private List<Appraisals> appraisalsfilteredList = null;
	private Appraisals appraisals = null;
	private LazyDataModel<Appraisals> dataModel; 
	//private List<AppraisalCategories> selectedCategories;
	private AppraisalCategories appraisalCategory;
	
	private List<AppraisalCategoryCode> selectedAppraisalCategoryCode;
	private AppraisalCategoryCode appraisalCategoryCode;
	private AppraisalChecklist appraisalChecklist;
	private List<AppraisalChecklist> appraisalChecklists;

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
	 * Initialize method to get all Appraisals and prepare a for a create of a new Appraisals
 	 * @author TechFinium 
 	 * @see    Appraisals
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		appraisalsInfo();
	}

	/**
	 * Get all Appraisals for data table
 	 * @author TechFinium 
 	 * @see    Appraisals
 	 */
	public void appraisalsInfo() {
			 dataModel = new LazyDataModel<Appraisals>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<Appraisals> retorno = new  ArrayList<Appraisals>();
			   
			   @Override 
			   public List<Appraisals> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allAppraisals(Appraisals.class,first,pageSize,sortField,sortOrder,filters);
					for (Appraisals appraisals : retorno) {
						appraisals.setAppraisalChecklist(service.allAppraisalChecklist(appraisals));
					}
					dataModel.setRowCount(service.count(Appraisals.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(Appraisals obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public Appraisals getRowData(String rowKey) {
			        for(Appraisals obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
	}

	public void appraisalsInsert()
	{
		try {
			if(selectedAppraisalCategoryCode.size() == 0)
			{
				addErrorMessage("Please add category codes");				
			}
			else
			{
				 service.createList(this.appraisals,this.appraisalCategory, selectedAppraisalCategoryCode);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 appraisalsInfo();
			}
		 } catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
	 	   super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Update Appraisals in database
 	 * @author TechFinium 
 	 * @see    Appraisals
 	 */
    public void appraisalsUpdate() {
		try {
				 service.update(this.appraisals);			
				 
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 appraisalsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete Appraisals from database
 	 * @author TechFinium 
 	 * @see    Appraisals
 	 */
	public void appraisalsDelete() {
		try {
			 service.delete(this.appraisals);
			  prepareNew();
			 appraisalsInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void appraisalsListDelete() {
		try {
			 appraisalChecklistService.delete(appraisalChecklist);
			 addInfoMessage("Removed from trhe list");
			 prepareNew();
			 appraisalsInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of Appraisals 
 	 * @author TechFinium 
 	 * @see    Appraisals
 	 */
	public void prepareNew() {
		appraisals = new Appraisals();
		appraisalChecklist = new AppraisalChecklist();
		selectedAppraisalCategoryCode= new ArrayList<>();
		//selectedCategories = new ArrayList<>();
	}
	
	
	public void prepCategoryList() {
		
		try {
			this.appraisalChecklists = (List<AppraisalChecklist>) appraisalChecklistService.findAppraisalCategoryCodeByAppraisal(this.appraisals);
			for(AppraisalChecklist  appraisalChecklis : appraisalChecklists)
			{
				this.selectedAppraisalCategoryCode.add(appraisalChecklis.getAppraisalCategoryCode());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//this.selectedCategories = toollist.getToolListTool().stream().map(x -> x.getTool()).collect(Collectors.toList());
		//this.selectedAppraisalCategoryCode = appraisals.getAppraisalCategoryCode().stream().map(x -> x).collect(Collectors.toList());
		//this.selectedAppraisalCategoryCode = this.appraisals.getAppraisalCategoryCode();
		//addErrorMessage(this.selectedAppraisalCategoryCode.size() + " ");
	}
	
	public void addToList() {
		boolean isAvailable = false;
		if(selectedAppraisalCategoryCode.size() > 0)
		{
			for(AppraisalCategoryCode appraisalCategoryCodein: selectedAppraisalCategoryCode)
			{
				if(appraisalCategoryCodein.getAppraisalcode().matches(appraisalCategoryCode.getAppraisalcode()))
				{
					isAvailable = true;
					break;
				}
			}
		}
		
		if(isAvailable)
		{
			addErrorMessage("Category code already exists");
		}
		else
		{
			if(appraisalCategoryCode != null)
			{
				this.selectedAppraisalCategoryCode.add(appraisalCategoryCode);
			}
			else
			{
				addErrorMessage("Please add codes");	
			}
		}
		//this.selectedCategories.add(appraisalCategory);
		this.appraisalCategory = null;
	}

	public void removeFromList() {
		this.selectedAppraisalCategoryCode.remove(appraisalCategoryCode);
		//this.selectedCategories.remove(appraisalCategory);
		this.appraisalCategory = null;
	}

/*
    public List<SelectItem> getAppraisalsDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	AppraisalsInfo();
    	for (Appraisals ug : AppraisalsList) {
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
    public List<Appraisals> complete(String desc) {
		List<Appraisals> l = null;
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
    
    public List<Appraisals> getAppraisalsList() {
		return appraisalsList;
	}
	public void setAppraisalsList(List<Appraisals> appraisalsList) {
		this.appraisalsList = appraisalsList;
	}


    public List<Appraisals> getAppraisalsfilteredList() {
		return appraisalsfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param AppraisalsfilteredList the new AppraisalsfilteredList list
 	 * @see    Appraisals
 	 */	
	public void setAppraisalsfilteredList(List<Appraisals> appraisalsfilteredList) {
		this.appraisalsfilteredList = appraisalsfilteredList;
	}

	
	public LazyDataModel<Appraisals> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<Appraisals> dataModel) {
		this.dataModel = dataModel;
	}

	public Appraisals getAppraisals() {
		return appraisals;
	}

	public void setAppraisals(Appraisals Appraisals) {
		this.appraisals = Appraisals;
	}

	/*public List<AppraisalCategories> getSelectedCategories() {
		return selectedCategories;
	}

	public void setSelectedCategories(List<AppraisalCategories> selectedCategories) {
		this.selectedCategories = selectedCategories;
	}*/

	public AppraisalCategories getAppraisalCategory() {
		return appraisalCategory;
	}

	public void setAppraisalCategory(AppraisalCategories appraisalCategory) {
		this.appraisalCategory = appraisalCategory;
	}

	public AppraisalCategoryCode getAppraisalCategoryCode() {
		return appraisalCategoryCode;
	}

	public void setAppraisalCategoryCode(AppraisalCategoryCode appraisalCategoryCode) {
		this.appraisalCategoryCode = appraisalCategoryCode;
	}

	public List<AppraisalCategoryCode> getSelectedAppraisalCategoryCode() {
		return selectedAppraisalCategoryCode;
	}

	public void setSelectedAppraisalCategoryCode(List<AppraisalCategoryCode> selectedAppraisalCategoryCode) {
		this.selectedAppraisalCategoryCode = selectedAppraisalCategoryCode;
	}

	public AppraisalChecklist getAppraisalChecklist() {
		return appraisalChecklist;
	}

	public void setAppraisalChecklist(AppraisalChecklist appraisalChecklist) {
		this.appraisalChecklist = appraisalChecklist;
	}
	
}
