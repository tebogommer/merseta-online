package  haj.com.ui;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.AllocationChangesReasons;
import haj.com.service.AllocationChangesReasonsService;
import haj.com.entity.datamodel.AllocationChangesReasonsDatamodel;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "allocationchangesreasonsUI")
@ViewScoped
public class AllocationChangesReasonsUI extends AbstractUI {

	private AllocationChangesReasonsService service = new AllocationChangesReasonsService();
	private List<AllocationChangesReasons> allocationchangesreasonsList = null;
	private List<AllocationChangesReasons> allocationchangesreasonsfilteredList = null;
	private AllocationChangesReasons allocationchangesreasons = null;
	private LazyDataModel<AllocationChangesReasons> dataModel; 

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
	 * Initialize method to get all AllocationChangesReasons and prepare a for a create of a new AllocationChangesReasons
 	 * @author TechFinium 
 	 * @see    AllocationChangesReasons
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		allocationchangesreasonsInfo();
	}

	/**
	 * Get all AllocationChangesReasons for data table
 	 * @author TechFinium 
 	 * @see    AllocationChangesReasons
 	 */
	public void allocationchangesreasonsInfo() {
			dataModel = new AllocationChangesReasonsDatamodel();
	}

	/**
	 * Insert AllocationChangesReasons into database
 	 * @author TechFinium 
 	 * @see    AllocationChangesReasons
 	 */
	public void allocationchangesreasonsInsert() {
		try {
				 service.create(this.allocationchangesreasons);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 allocationchangesreasonsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update AllocationChangesReasons in database
 	 * @author TechFinium 
 	 * @see    AllocationChangesReasons
 	 */
    public void allocationchangesreasonsUpdate() {
		try {
				 service.update(this.allocationchangesreasons);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 allocationchangesreasonsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete AllocationChangesReasons from database
 	 * @author TechFinium 
 	 * @see    AllocationChangesReasons
 	 */
	public void allocationchangesreasonsDelete() {
		try {
			 service.delete(this.allocationchangesreasons);
			  prepareNew();
			 allocationchangesreasonsInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of AllocationChangesReasons 
 	 * @author TechFinium 
 	 * @see    AllocationChangesReasons
 	 */
	public void prepareNew() {
		allocationchangesreasons = new AllocationChangesReasons();
	}

/*
    public List<SelectItem> getAllocationChangesReasonsDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	allocationchangesreasonsInfo();
    	for (AllocationChangesReasons ug : allocationchangesreasonsList) {
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
    public List<AllocationChangesReasons> complete(String desc) {
		List<AllocationChangesReasons> l = null;
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
    
    public List<AllocationChangesReasons> getAllocationChangesReasonsList() {
		return allocationchangesreasonsList;
	}
	public void setAllocationChangesReasonsList(List<AllocationChangesReasons> allocationchangesreasonsList) {
		this.allocationchangesreasonsList = allocationchangesreasonsList;
	}
	public AllocationChangesReasons getAllocationchangesreasons() {
		return allocationchangesreasons;
	}
	public void setAllocationchangesreasons(AllocationChangesReasons allocationchangesreasons) {
		this.allocationchangesreasons = allocationchangesreasons;
	}

    public List<AllocationChangesReasons> getAllocationChangesReasonsfilteredList() {
		return allocationchangesreasonsfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param allocationchangesreasonsfilteredList the new allocationchangesreasonsfilteredList list
 	 * @see    AllocationChangesReasons
 	 */	
	public void setAllocationChangesReasonsfilteredList(List<AllocationChangesReasons> allocationchangesreasonsfilteredList) {
		this.allocationchangesreasonsfilteredList = allocationchangesreasonsfilteredList;
	}

	
	public LazyDataModel<AllocationChangesReasons> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<AllocationChangesReasons> dataModel) {
		this.dataModel = dataModel;
	}
	
}
