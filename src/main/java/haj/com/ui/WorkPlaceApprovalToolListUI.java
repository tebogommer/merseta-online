package  haj.com.ui;

import javax.annotation.PostConstruct;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.WorkPlaceApprovalToolList;
import haj.com.service.WorkPlaceApprovalToolListService;
import org.primefaces.model.LazyDataModel;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "workplaceapprovaltoollistUI")
@ViewScoped
public class WorkPlaceApprovalToolListUI extends AbstractUI {

	private WorkPlaceApprovalToolListService service = new WorkPlaceApprovalToolListService();
	private List<WorkPlaceApprovalToolList> workplaceapprovaltoollistList = null;
	private List<WorkPlaceApprovalToolList> workplaceapprovaltoollistfilteredList = null;
	private WorkPlaceApprovalToolList workplaceapprovaltoollist = null;
	private LazyDataModel<WorkPlaceApprovalToolList> dataModel; 

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
	 * Initialize method to get all WorkPlaceApprovalToolList and prepare a for a create of a new WorkPlaceApprovalToolList
 	 * @author TechFinium 
 	 * @see    WorkPlaceApprovalToolList
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		workplaceapprovaltoollistInfo();
	}

	/**
	 * Get all WorkPlaceApprovalToolList for data table
 	 * @author TechFinium 
 	 * @see    WorkPlaceApprovalToolList
 	 */
	public void workplaceapprovaltoollistInfo() {
			//dataModel = new WorkPlaceApprovalToolListDatamodel();
	}

	/**
	 * Insert WorkPlaceApprovalToolList into database
 	 * @author TechFinium 
 	 * @see    WorkPlaceApprovalToolList
 	 */
	public void workplaceapprovaltoollistInsert() {
		try {
				 service.create(this.workplaceapprovaltoollist);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 workplaceapprovaltoollistInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update WorkPlaceApprovalToolList in database
 	 * @author TechFinium 
 	 * @see    WorkPlaceApprovalToolList
 	 */
    public void workplaceapprovaltoollistUpdate() {
		try {
				 service.update(this.workplaceapprovaltoollist);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 workplaceapprovaltoollistInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete WorkPlaceApprovalToolList from database
 	 * @author TechFinium 
 	 * @see    WorkPlaceApprovalToolList
 	 */
	public void workplaceapprovaltoollistDelete() {
		try {
			 service.delete(this.workplaceapprovaltoollist);
			  prepareNew();
			 workplaceapprovaltoollistInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of WorkPlaceApprovalToolList 
 	 * @author TechFinium 
 	 * @see    WorkPlaceApprovalToolList
 	 */
	public void prepareNew() {
		workplaceapprovaltoollist = new WorkPlaceApprovalToolList();
	}

/*
    public List<SelectItem> getWorkPlaceApprovalToolListDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	workplaceapprovaltoollistInfo();
    	for (WorkPlaceApprovalToolList ug : workplaceapprovaltoollistList) {
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
    public List<WorkPlaceApprovalToolList> complete(String desc) {
		List<WorkPlaceApprovalToolList> l = null;
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
    
    public List<WorkPlaceApprovalToolList> getWorkPlaceApprovalToolListList() {
		return workplaceapprovaltoollistList;
	}
	public void setWorkPlaceApprovalToolListList(List<WorkPlaceApprovalToolList> workplaceapprovaltoollistList) {
		this.workplaceapprovaltoollistList = workplaceapprovaltoollistList;
	}
	public WorkPlaceApprovalToolList getWorkplaceapprovaltoollist() {
		return workplaceapprovaltoollist;
	}
	public void setWorkplaceapprovaltoollist(WorkPlaceApprovalToolList workplaceapprovaltoollist) {
		this.workplaceapprovaltoollist = workplaceapprovaltoollist;
	}

    public List<WorkPlaceApprovalToolList> getWorkPlaceApprovalToolListfilteredList() {
		return workplaceapprovaltoollistfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param workplaceapprovaltoollistfilteredList the new workplaceapprovaltoollistfilteredList list
 	 * @see    WorkPlaceApprovalToolList
 	 */	
	public void setWorkPlaceApprovalToolListfilteredList(List<WorkPlaceApprovalToolList> workplaceapprovaltoollistfilteredList) {
		this.workplaceapprovaltoollistfilteredList = workplaceapprovaltoollistfilteredList;
	}

	
	public LazyDataModel<WorkPlaceApprovalToolList> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<WorkPlaceApprovalToolList> dataModel) {
		this.dataModel = dataModel;
	}
	
}
