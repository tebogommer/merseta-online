package  haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.GrantOfoSelection;
import haj.com.service.lookup.GrantOfoSelectionService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "grantofoselectionUI")
@ViewScoped
public class GrantOfoSelectionUI extends AbstractUI {

	private GrantOfoSelectionService service = new GrantOfoSelectionService();
	private List<GrantOfoSelection> grantofoselectionList = null;
	private List<GrantOfoSelection> grantofoselectionfilteredList = null;
	private GrantOfoSelection grantofoselection = null;
	private LazyDataModel<GrantOfoSelection> dataModel; 

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
	 * Initialize method to get all GrantOfoSelection and prepare a for a create of a new GrantOfoSelection
 	 * @author TechFinium 
 	 * @see    GrantOfoSelection
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		grantofoselectionInfo();
	}

	/**
	 * Get all GrantOfoSelection for data table
 	 * @author TechFinium 
 	 * @see    GrantOfoSelection
 	 */
	public void grantofoselectionInfo() {
	 
			
			 dataModel = new LazyDataModel<GrantOfoSelection>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<GrantOfoSelection> retorno = new  ArrayList<GrantOfoSelection>();
			   
			   @Override 
			   public List<GrantOfoSelection> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allGrantOfoSelection(GrantOfoSelection.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(GrantOfoSelection.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(GrantOfoSelection obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public GrantOfoSelection getRowData(String rowKey) {
			        for(GrantOfoSelection obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert GrantOfoSelection into database
 	 * @author TechFinium 
 	 * @see    GrantOfoSelection
 	 */
	public void grantofoselectionInsert() {
		try {
				 service.create(this.grantofoselection);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 grantofoselectionInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update GrantOfoSelection in database
 	 * @author TechFinium 
 	 * @see    GrantOfoSelection
 	 */
    public void grantofoselectionUpdate() {
		try {
				 service.update(this.grantofoselection);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 grantofoselectionInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete GrantOfoSelection from database
 	 * @author TechFinium 
 	 * @see    GrantOfoSelection
 	 */
	public void grantofoselectionDelete() {
		try {
			 service.delete(this.grantofoselection);
			  prepareNew();
			 grantofoselectionInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of GrantOfoSelection 
 	 * @author TechFinium 
 	 * @see    GrantOfoSelection
 	 */
	public void prepareNew() {
		grantofoselection = new GrantOfoSelection();
	}

/*
    public List<SelectItem> getGrantOfoSelectionDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	grantofoselectionInfo();
    	for (GrantOfoSelection ug : grantofoselectionList) {
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
    public List<GrantOfoSelection> complete(String desc) {
		List<GrantOfoSelection> l = null;
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
    
    public List<GrantOfoSelection> getGrantOfoSelectionList() {
		return grantofoselectionList;
	}
	public void setGrantOfoSelectionList(List<GrantOfoSelection> grantofoselectionList) {
		this.grantofoselectionList = grantofoselectionList;
	}
	public GrantOfoSelection getGrantofoselection() {
		return grantofoselection;
	}
	public void setGrantofoselection(GrantOfoSelection grantofoselection) {
		this.grantofoselection = grantofoselection;
	}

    public List<GrantOfoSelection> getGrantOfoSelectionfilteredList() {
		return grantofoselectionfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param grantofoselectionfilteredList the new grantofoselectionfilteredList list
 	 * @see    GrantOfoSelection
 	 */	
	public void setGrantOfoSelectionfilteredList(List<GrantOfoSelection> grantofoselectionfilteredList) {
		this.grantofoselectionfilteredList = grantofoselectionfilteredList;
	}

	
	public LazyDataModel<GrantOfoSelection> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<GrantOfoSelection> dataModel) {
		this.dataModel = dataModel;
	}
	
}
