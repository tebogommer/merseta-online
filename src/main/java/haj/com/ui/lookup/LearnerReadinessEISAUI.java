package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.LearnerReadinessEISA;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LearnerReadinessEISAService;

// TODO: Auto-generated Javadoc
/**
 * The Class LearnerReadinessEISAUI.
 */
@ManagedBean(name = "learnerreadinesseisaUI")
@ViewScoped
public class LearnerReadinessEISAUI extends AbstractUI {

	/** The service. */
	private LearnerReadinessEISAService service = new LearnerReadinessEISAService();
	
	/** The learnerreadinesseisa list. */
	private List<LearnerReadinessEISA> learnerreadinesseisaList = null;
	
	/** The learnerreadinesseisafiltered list. */
	private List<LearnerReadinessEISA> learnerreadinesseisafilteredList = null;
	
	/** The learnerreadinesseisa. */
	private LearnerReadinessEISA learnerreadinesseisa = null;
	
	/** The data model. */
	private LazyDataModel<LearnerReadinessEISA> dataModel; 

    /**
     * Inits the.
     */
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
	 * Initialize method to get all LearnerReadinessEISA and prepare a for a create of a new LearnerReadinessEISA.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    LearnerReadinessEISA
	 */
	private void runInit() throws Exception {
		prepareNew();
		learnerreadinesseisaInfo();
	}

	/**
	 * Get all LearnerReadinessEISA for data table.
	 *
	 * @author TechFinium
	 * @see    LearnerReadinessEISA
	 */
	public void learnerreadinesseisaInfo() {
	 
			
			 dataModel = new LazyDataModel<LearnerReadinessEISA>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<LearnerReadinessEISA> retorno = new  ArrayList<LearnerReadinessEISA>();
			   
			   @Override 
			   public List<LearnerReadinessEISA> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allLearnerReadinessEISA(LearnerReadinessEISA.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(LearnerReadinessEISA.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(LearnerReadinessEISA obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public LearnerReadinessEISA getRowData(String rowKey) {
			        for(LearnerReadinessEISA obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert LearnerReadinessEISA into database.
	 *
	 * @author TechFinium
	 * @see    LearnerReadinessEISA
	 */
	public void learnerreadinesseisaInsert() {
		try {
				 service.create(this.learnerreadinesseisa);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 learnerreadinesseisaInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update LearnerReadinessEISA in database.
	 *
	 * @author TechFinium
	 * @see    LearnerReadinessEISA
	 */
    public void learnerreadinesseisaUpdate() {
		try {
				 service.update(this.learnerreadinesseisa);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 learnerreadinesseisaInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete LearnerReadinessEISA from database.
	 *
	 * @author TechFinium
	 * @see    LearnerReadinessEISA
	 */
	public void learnerreadinesseisaDelete() {
		try {
			 service.delete(this.learnerreadinesseisa);
			  prepareNew();
			 learnerreadinesseisaInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted "));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of LearnerReadinessEISA .
	 *
	 * @author TechFinium
	 * @see    LearnerReadinessEISA
	 */
	public void prepareNew() {
		learnerreadinesseisa = new LearnerReadinessEISA();
	}

/*
    public List<SelectItem> getLearnerReadinessEISADD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	learnerreadinesseisaInfo();
    	for (LearnerReadinessEISA ug : learnerreadinesseisaList) {
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
    public List<LearnerReadinessEISA> complete(String desc) {
		List<LearnerReadinessEISA> l = null;
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
    
    /**
     * Gets the learner readiness EISA list.
     *
     * @return the learner readiness EISA list
     */
    public List<LearnerReadinessEISA> getLearnerReadinessEISAList() {
		return learnerreadinesseisaList;
	}
	
	/**
	 * Sets the learner readiness EISA list.
	 *
	 * @param learnerreadinesseisaList the new learner readiness EISA list
	 */
	public void setLearnerReadinessEISAList(List<LearnerReadinessEISA> learnerreadinesseisaList) {
		this.learnerreadinesseisaList = learnerreadinesseisaList;
	}
	
	/**
	 * Gets the learnerreadinesseisa.
	 *
	 * @return the learnerreadinesseisa
	 */
	public LearnerReadinessEISA getLearnerreadinesseisa() {
		return learnerreadinesseisa;
	}
	
	/**
	 * Sets the learnerreadinesseisa.
	 *
	 * @param learnerreadinesseisa the new learnerreadinesseisa
	 */
	public void setLearnerreadinesseisa(LearnerReadinessEISA learnerreadinesseisa) {
		this.learnerreadinesseisa = learnerreadinesseisa;
	}

    /**
     * Gets the learner readiness EIS afiltered list.
     *
     * @return the learner readiness EIS afiltered list
     */
    public List<LearnerReadinessEISA> getLearnerReadinessEISAfilteredList() {
		return learnerreadinesseisafilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param learnerreadinesseisafilteredList the new learnerreadinesseisafilteredList list
	 * @see    LearnerReadinessEISA
	 */	
	public void setLearnerReadinessEISAfilteredList(List<LearnerReadinessEISA> learnerreadinesseisafilteredList) {
		this.learnerreadinesseisafilteredList = learnerreadinesseisafilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<LearnerReadinessEISA> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<LearnerReadinessEISA> dataModel) {
		this.dataModel = dataModel;
	}
	
}
