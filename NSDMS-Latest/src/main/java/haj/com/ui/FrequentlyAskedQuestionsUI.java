package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.FrequentlyAskedQuestions;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.FrequentlyAskedQuestionsService;

@ManagedBean(name = "frequentlyaskedquestionsUI")
@ViewScoped
public class FrequentlyAskedQuestionsUI extends AbstractUI {

	private FrequentlyAskedQuestionsService service = new FrequentlyAskedQuestionsService();
	private List<FrequentlyAskedQuestions> frequentlyaskedquestionsList = null;
	private List<FrequentlyAskedQuestions> frequentlyaskedquestionsfilteredList = null;
	private List<FrequentlyAskedQuestions> frequentlyaskedquestionsActiveList = null;
	private List<FrequentlyAskedQuestions> frequentlyaskedquestionsSubmittedList = null;
	private List<FrequentlyAskedQuestions> frequentlyaskedquestionsDefinedList = null;
	private FrequentlyAskedQuestions frequentlyaskedquestions = null;
	private LazyDataModel<FrequentlyAskedQuestions> dataModel; 

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
	 * Initialize method to get all FrequentlyAskedQuestions and prepare a for a create of a new FrequentlyAskedQuestions
 	 * @author TechFinium 
 	 * @see    FrequentlyAskedQuestions
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		frequentlyaskedquestionsInfo();
	}

	/**
	 * Get all FrequentlyAskedQuestions for data table
 	 * @author TechFinium 
 	 * @see    FrequentlyAskedQuestions
 	 */
	public void frequentlyaskedquestionsInfo() {
	 
			try {
				this.frequentlyaskedquestionsActiveList = service.findActiveFAQ();
				this.frequentlyaskedquestionsSubmittedList = service.allSubmittedFAQ();
				this.frequentlyaskedquestionsDefinedList = service.allFAQ();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 dataModel = new LazyDataModel<FrequentlyAskedQuestions>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<FrequentlyAskedQuestions> retorno = new  ArrayList<FrequentlyAskedQuestions>();
			   
			   @Override 
			   public List<FrequentlyAskedQuestions> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allFrequentlyAskedQuestions(FrequentlyAskedQuestions.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(FrequentlyAskedQuestions.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(FrequentlyAskedQuestions obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public FrequentlyAskedQuestions getRowData(String rowKey) {
			        for(FrequentlyAskedQuestions obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert FrequentlyAskedQuestions into database
 	 * @author TechFinium 
 	 * @see    FrequentlyAskedQuestions
 	 */
	public void frequentlyaskedquestionsInsert() {
		try {
				 service.create(this.frequentlyaskedquestions);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 frequentlyaskedquestionsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update FrequentlyAskedQuestions in database
 	 * @author TechFinium 
 	 * @see    FrequentlyAskedQuestions
 	 */
    public void frequentlyaskedquestionsUpdate() {
		try {
				 service.update(this.frequentlyaskedquestions);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 frequentlyaskedquestionsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete FrequentlyAskedQuestions from database
 	 * @author TechFinium 
 	 * @see    FrequentlyAskedQuestions
 	 */
	public void frequentlyaskedquestionsDelete() {
		try {
			 service.delete(this.frequentlyaskedquestions);
			  prepareNew();
			 frequentlyaskedquestionsInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of FrequentlyAskedQuestions 
 	 * @author TechFinium 
 	 * @see    FrequentlyAskedQuestions
 	 */
	public void prepareNew() {
		frequentlyaskedquestions = new FrequentlyAskedQuestions();
	}

/*
    public List<SelectItem> getFrequentlyAskedQuestionsDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	frequentlyaskedquestionsInfo();
    	for (FrequentlyAskedQuestions ug : frequentlyaskedquestionsList) {
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
    public List<FrequentlyAskedQuestions> complete(String desc) {
		List<FrequentlyAskedQuestions> l = null;
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
    
    public List<FrequentlyAskedQuestions> getFrequentlyAskedQuestionsList() {
		return frequentlyaskedquestionsList;
	}
	public void setFrequentlyAskedQuestionsList(List<FrequentlyAskedQuestions> frequentlyaskedquestionsList) {
		this.frequentlyaskedquestionsList = frequentlyaskedquestionsList;
	}
	public FrequentlyAskedQuestions getFrequentlyaskedquestions() {
		return frequentlyaskedquestions;
	}
	public void setFrequentlyaskedquestions(FrequentlyAskedQuestions frequentlyaskedquestions) {
		this.frequentlyaskedquestions = frequentlyaskedquestions;
	}

    public List<FrequentlyAskedQuestions> getFrequentlyAskedQuestionsfilteredList() {
		return frequentlyaskedquestionsfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param frequentlyaskedquestionsfilteredList the new frequentlyaskedquestionsfilteredList list
 	 * @see    FrequentlyAskedQuestions
 	 */	
	public void setFrequentlyAskedQuestionsfilteredList(List<FrequentlyAskedQuestions> frequentlyaskedquestionsfilteredList) {
		this.frequentlyaskedquestionsfilteredList = frequentlyaskedquestionsfilteredList;
	}
	
	public List<FrequentlyAskedQuestions> getFrequentlyaskedquestionsActiveList() {
		return frequentlyaskedquestionsActiveList;
	}

	public void setFrequentlyaskedquestionsActiveList(List<FrequentlyAskedQuestions> frequentlyaskedquestionsActiveList) {
		this.frequentlyaskedquestionsActiveList = frequentlyaskedquestionsActiveList;
	}

	public List<FrequentlyAskedQuestions> getFrequentlyaskedquestionsSubmittedList() {
		return frequentlyaskedquestionsSubmittedList;
	}

	public void setFrequentlyaskedquestionsSubmittedList(
			List<FrequentlyAskedQuestions> frequentlyaskedquestionsSubmittedList) {
		this.frequentlyaskedquestionsSubmittedList = frequentlyaskedquestionsSubmittedList;
	}

	public List<FrequentlyAskedQuestions> getFrequentlyaskedquestionsDefinedList() {
		return frequentlyaskedquestionsDefinedList;
	}

	public void setFrequentlyaskedquestionsDefinedList(List<FrequentlyAskedQuestions> frequentlyaskedquestionsDefinedList) {
		this.frequentlyaskedquestionsDefinedList = frequentlyaskedquestionsDefinedList;
	}

	public LazyDataModel<FrequentlyAskedQuestions> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<FrequentlyAskedQuestions> dataModel) {
		this.dataModel = dataModel;
	}
	
}
