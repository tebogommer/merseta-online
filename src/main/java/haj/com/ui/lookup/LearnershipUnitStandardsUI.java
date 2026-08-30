package  haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.LearnershipUnitStandards;
import haj.com.service.lookup.LearnershipUnitStandardsService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "learnershipunitstandardsUI")
@ViewScoped
public class LearnershipUnitStandardsUI extends AbstractUI {

	private LearnershipUnitStandardsService service = new LearnershipUnitStandardsService();
	private List<LearnershipUnitStandards> learnershipunitstandardsList = null;
	private List<LearnershipUnitStandards> learnershipunitstandardsfilteredList = null;
	private LearnershipUnitStandards learnershipunitstandards = null;
	private LazyDataModel<LearnershipUnitStandards> dataModel; 

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
	 * Initialize method to get all LearnershipUnitStandards and prepare a for a create of a new LearnershipUnitStandards
 	 * @author TechFinium 
 	 * @see    LearnershipUnitStandards
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		learnershipunitstandardsInfo();
	}

	/**
	 * Get all LearnershipUnitStandards for data table
 	 * @author TechFinium 
 	 * @see    LearnershipUnitStandards
 	 */
	public void learnershipunitstandardsInfo() {
	 
			
			 dataModel = new LazyDataModel<LearnershipUnitStandards>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<LearnershipUnitStandards> retorno = new  ArrayList<LearnershipUnitStandards>();
			   
			   @Override 
			   public List<LearnershipUnitStandards> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allLearnershipUnitStandards(LearnershipUnitStandards.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(LearnershipUnitStandards.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(LearnershipUnitStandards obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public LearnershipUnitStandards getRowData(String rowKey) {
			        for(LearnershipUnitStandards obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert LearnershipUnitStandards into database
 	 * @author TechFinium 
 	 * @see    LearnershipUnitStandards
 	 */
	public void learnershipunitstandardsInsert() {
		try {
				 service.create(this.learnershipunitstandards);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 learnershipunitstandardsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update LearnershipUnitStandards in database
 	 * @author TechFinium 
 	 * @see    LearnershipUnitStandards
 	 */
    public void learnershipunitstandardsUpdate() {
		try {
				 service.update(this.learnershipunitstandards);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 learnershipunitstandardsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete LearnershipUnitStandards from database
 	 * @author TechFinium 
 	 * @see    LearnershipUnitStandards
 	 */
	public void learnershipunitstandardsDelete() {
		try {
			 service.delete(this.learnershipunitstandards);
			  prepareNew();
			 learnershipunitstandardsInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of LearnershipUnitStandards 
 	 * @author TechFinium 
 	 * @see    LearnershipUnitStandards
 	 */
	public void prepareNew() {
		learnershipunitstandards = new LearnershipUnitStandards();
	}

/*
    public List<SelectItem> getLearnershipUnitStandardsDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	learnershipunitstandardsInfo();
    	for (LearnershipUnitStandards ug : learnershipunitstandardsList) {
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
    public List<LearnershipUnitStandards> complete(String desc) {
		List<LearnershipUnitStandards> l = null;
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
    
    public List<LearnershipUnitStandards> getLearnershipUnitStandardsList() {
		return learnershipunitstandardsList;
	}
	public void setLearnershipUnitStandardsList(List<LearnershipUnitStandards> learnershipunitstandardsList) {
		this.learnershipunitstandardsList = learnershipunitstandardsList;
	}
	public LearnershipUnitStandards getLearnershipunitstandards() {
		return learnershipunitstandards;
	}
	public void setLearnershipunitstandards(LearnershipUnitStandards learnershipunitstandards) {
		this.learnershipunitstandards = learnershipunitstandards;
	}

    public List<LearnershipUnitStandards> getLearnershipUnitStandardsfilteredList() {
		return learnershipunitstandardsfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param learnershipunitstandardsfilteredList the new learnershipunitstandardsfilteredList list
 	 * @see    LearnershipUnitStandards
 	 */	
	public void setLearnershipUnitStandardsfilteredList(List<LearnershipUnitStandards> learnershipunitstandardsfilteredList) {
		this.learnershipunitstandardsfilteredList = learnershipunitstandardsfilteredList;
	}

	
	public LazyDataModel<LearnershipUnitStandards> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LearnershipUnitStandards> dataModel) {
		this.dataModel = dataModel;
	}
	
}
