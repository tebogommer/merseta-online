package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.LearnerInduction;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.LearnerInductionService;

@ManagedBean(name = "learnerinductionUI")
@ViewScoped
public class LearnerInductionUI extends AbstractUI {

	private LearnerInductionService service = new LearnerInductionService();
	private List<LearnerInduction> learnerinductionList = null;
	private List<LearnerInduction> learnerinductionfilteredList = null;
	private LearnerInduction learnerinduction = null;
	private LazyDataModel<LearnerInduction> dataModel; 

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
	 * Initialize method to get all LearnerInduction and prepare a for a create of a new LearnerInduction
 	 * @author TechFinium 
 	 * @see    LearnerInduction
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		learnerinductionInfo();
	}

	/**
	 * Get all LearnerInduction for data table
 	 * @author TechFinium 
 	 * @see    LearnerInduction
 	 */
	public void learnerinductionInfo() {
	 
			
			 dataModel = new LazyDataModel<LearnerInduction>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<LearnerInduction> retorno = new  ArrayList<LearnerInduction>();
			   
			   @Override 
			   public List<LearnerInduction> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allLearnerInduction(LearnerInduction.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(LearnerInduction.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(LearnerInduction obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public LearnerInduction getRowData(String rowKey) {
			        for(LearnerInduction obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert LearnerInduction into database
 	 * @author TechFinium 
 	 * @see    LearnerInduction
 	 */
	public void learnerinductionInsert() {
		try {
				 service.create(this.learnerinduction);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 learnerinductionInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update LearnerInduction in database
 	 * @author TechFinium 
 	 * @see    LearnerInduction
 	 */
    public void learnerinductionUpdate() {
		try {
				 service.update(this.learnerinduction);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 learnerinductionInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete LearnerInduction from database
 	 * @author TechFinium 
 	 * @see    LearnerInduction
 	 */
	public void learnerinductionDelete() {
		try {
			 service.delete(this.learnerinduction);
			  prepareNew();
			 learnerinductionInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of LearnerInduction 
 	 * @author TechFinium 
 	 * @see    LearnerInduction
 	 */
	public void prepareNew() {
		learnerinduction = new LearnerInduction();
	}

/*
    public List<SelectItem> getLearnerInductionDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	learnerinductionInfo();
    	for (LearnerInduction ug : learnerinductionList) {
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
    public List<LearnerInduction> complete(String desc) {
		List<LearnerInduction> l = null;
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
    
    public List<LearnerInduction> getLearnerInductionList() {
		return learnerinductionList;
	}
	public void setLearnerInductionList(List<LearnerInduction> learnerinductionList) {
		this.learnerinductionList = learnerinductionList;
	}
	public LearnerInduction getLearnerinduction() {
		return learnerinduction;
	}
	public void setLearnerinduction(LearnerInduction learnerinduction) {
		this.learnerinduction = learnerinduction;
	}

    public List<LearnerInduction> getLearnerInductionfilteredList() {
		return learnerinductionfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param learnerinductionfilteredList the new learnerinductionfilteredList list
 	 * @see    LearnerInduction
 	 */	
	public void setLearnerInductionfilteredList(List<LearnerInduction> learnerinductionfilteredList) {
		this.learnerinductionfilteredList = learnerinductionfilteredList;
	}

	
	public LazyDataModel<LearnerInduction> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LearnerInduction> dataModel) {
		this.dataModel = dataModel;
	}
	
}
