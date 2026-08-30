package  haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.AllocationList;
import haj.com.service.lookup.AllocationListService;
import haj.com.entity.datamodel.lookup.AllocationListDatamodel;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "allocationlistUI")
@ViewScoped
public class AllocationListUI extends AbstractUI {

	private AllocationListService service = new AllocationListService();
	private List<AllocationList> allocationlistList = null;
	private List<AllocationList> allocationlistfilteredList = null;
	private AllocationList allocationlist = null;
	private LazyDataModel<AllocationList> dataModel; 

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
	 * Initialize method to get all AllocationList and prepare a for a create of a new AllocationList
 	 * @author TechFinium 
 	 * @see    AllocationList
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		allocationlistInfo();
	}

	/**
	 * Get all AllocationList for data table
 	 * @author TechFinium 
 	 * @see    AllocationList
 	 */
	public void allocationlistInfo() {
		dataModel = new AllocationListDatamodel();
	
	}

	/**
	 * Insert AllocationList into database
 	 * @author TechFinium 
 	 * @see    AllocationList
 	 */
	public void allocationlistInsert() {
		try {
				 service.create(this.allocationlist);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 allocationlistInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update AllocationList in database
 	 * @author TechFinium 
 	 * @see    AllocationList
 	 */
    public void allocationlistUpdate() {
		try {
				 service.update(this.allocationlist);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 allocationlistInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete AllocationList from database
 	 * @author TechFinium 
 	 * @see    AllocationList
 	 */
	public void allocationlistDelete() {
		try {
			 service.delete(this.allocationlist);
			  prepareNew();
			 allocationlistInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of AllocationList 
 	 * @author TechFinium 
 	 * @see    AllocationList
 	 */
	public void prepareNew() {
		allocationlist = new AllocationList();
	}

/*
    public List<SelectItem> getAllocationListDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	allocationlistInfo();
    	for (AllocationList ug : allocationlistList) {
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
    public List<AllocationList> complete(String desc) {
		List<AllocationList> l = null;
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
    
    public List<AllocationList> getAllocationListList() {
		return allocationlistList;
	}
	public void setAllocationListList(List<AllocationList> allocationlistList) {
		this.allocationlistList = allocationlistList;
	}
	public AllocationList getAllocationlist() {
		return allocationlist;
	}
	public void setAllocationlist(AllocationList allocationlist) {
		this.allocationlist = allocationlist;
	}

    public List<AllocationList> getAllocationListfilteredList() {
		return allocationlistfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param allocationlistfilteredList the new allocationlistfilteredList list
 	 * @see    AllocationList
 	 */	
	public void setAllocationListfilteredList(List<AllocationList> allocationlistfilteredList) {
		this.allocationlistfilteredList = allocationlistfilteredList;
	}

	
	public LazyDataModel<AllocationList> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<AllocationList> dataModel) {
		this.dataModel = dataModel;
	}
	
}
