package  haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.LearnerMentorRatio;
import haj.com.service.lookup.LearnerMentorRatioService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "learnermentorratioUI")
@ViewScoped
public class LearnerMentorRatioUI extends AbstractUI {

	private LearnerMentorRatioService service = new LearnerMentorRatioService();
	private List<LearnerMentorRatio> learnermentorratioList = null;
	private List<LearnerMentorRatio> learnermentorratiofilteredList = null;
	private LearnerMentorRatio learnermentorratio = null;
	private LazyDataModel<LearnerMentorRatio> dataModel; 

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
	 * Initialize method to get all LearnerMentorRatio and prepare a for a create of a new LearnerMentorRatio
 	 * @author TechFinium 
 	 * @see    LearnerMentorRatio
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		learnermentorratioInfo();
	}

	/**
	 * Get all LearnerMentorRatio for data table
 	 * @author TechFinium 
 	 * @see    LearnerMentorRatio
 	 */
	public void learnermentorratioInfo() {
	 
			
			 dataModel = new LazyDataModel<LearnerMentorRatio>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<LearnerMentorRatio> retorno = new  ArrayList<LearnerMentorRatio>();
			   
			   @Override 
			   public List<LearnerMentorRatio> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allLearnerMentorRatio(LearnerMentorRatio.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(LearnerMentorRatio.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(LearnerMentorRatio obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public LearnerMentorRatio getRowData(String rowKey) {
			        for(LearnerMentorRatio obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert LearnerMentorRatio into database
 	 * @author TechFinium 
 	 * @see    LearnerMentorRatio
 	 */
	public void learnermentorratioInsert() {
		try {
				 service.create(this.learnermentorratio);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 learnermentorratioInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update LearnerMentorRatio in database
 	 * @author TechFinium 
 	 * @see    LearnerMentorRatio
 	 */
    public void learnermentorratioUpdate() {
		try {
				 service.update(this.learnermentorratio);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 learnermentorratioInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete LearnerMentorRatio from database
 	 * @author TechFinium 
 	 * @see    LearnerMentorRatio
 	 */
	public void learnermentorratioDelete() {
		try {
			 service.delete(this.learnermentorratio);
			  prepareNew();
			 learnermentorratioInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of LearnerMentorRatio 
 	 * @author TechFinium 
 	 * @see    LearnerMentorRatio
 	 */
	public void prepareNew() {
		learnermentorratio = new LearnerMentorRatio();
	}

/*
    public List<SelectItem> getLearnerMentorRatioDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	learnermentorratioInfo();
    	for (LearnerMentorRatio ug : learnermentorratioList) {
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
    public List<LearnerMentorRatio> complete(String desc) {
		List<LearnerMentorRatio> l = null;
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
    
    public List<LearnerMentorRatio> getLearnerMentorRatioList() {
		return learnermentorratioList;
	}
	public void setLearnerMentorRatioList(List<LearnerMentorRatio> learnermentorratioList) {
		this.learnermentorratioList = learnermentorratioList;
	}
	public LearnerMentorRatio getLearnermentorratio() {
		return learnermentorratio;
	}
	public void setLearnermentorratio(LearnerMentorRatio learnermentorratio) {
		this.learnermentorratio = learnermentorratio;
	}

    public List<LearnerMentorRatio> getLearnerMentorRatiofilteredList() {
		return learnermentorratiofilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param learnermentorratiofilteredList the new learnermentorratiofilteredList list
 	 * @see    LearnerMentorRatio
 	 */	
	public void setLearnerMentorRatiofilteredList(List<LearnerMentorRatio> learnermentorratiofilteredList) {
		this.learnermentorratiofilteredList = learnermentorratiofilteredList;
	}

	
	public LazyDataModel<LearnerMentorRatio> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LearnerMentorRatio> dataModel) {
		this.dataModel = dataModel;
	}
	
}
