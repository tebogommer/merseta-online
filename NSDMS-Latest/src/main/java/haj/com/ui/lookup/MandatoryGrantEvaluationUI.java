package  haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.MandatoryGrantEvaluation;
import haj.com.service.lookup.MandatoryGrantEvaluationService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "mandatorygrantevaluationUI")
@ViewScoped
public class MandatoryGrantEvaluationUI extends AbstractUI {

	private MandatoryGrantEvaluationService service = new MandatoryGrantEvaluationService();
	private List<MandatoryGrantEvaluation> mandatorygrantevaluationList = null;
	private List<MandatoryGrantEvaluation> mandatorygrantevaluationfilteredList = null;
	private MandatoryGrantEvaluation mandatorygrantevaluation = null;
	private LazyDataModel<MandatoryGrantEvaluation> dataModel; 

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
	 * Initialize method to get all MandatoryGrantEvaluation and prepare a for a create of a new MandatoryGrantEvaluation
 	 * @author TechFinium 
 	 * @see    MandatoryGrantEvaluation
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		mandatorygrantevaluationInfo();
	}

	/**
	 * Get all MandatoryGrantEvaluation for data table
 	 * @author TechFinium 
 	 * @see    MandatoryGrantEvaluation
 	 */
	public void mandatorygrantevaluationInfo() {
	 
			
			 dataModel = new LazyDataModel<MandatoryGrantEvaluation>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<MandatoryGrantEvaluation> retorno = new  ArrayList<MandatoryGrantEvaluation>();
			   
			   @Override 
			   public List<MandatoryGrantEvaluation> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allMandatoryGrantEvaluation(MandatoryGrantEvaluation.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(MandatoryGrantEvaluation.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(MandatoryGrantEvaluation obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public MandatoryGrantEvaluation getRowData(String rowKey) {
			        for(MandatoryGrantEvaluation obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert MandatoryGrantEvaluation into database
 	 * @author TechFinium 
 	 * @see    MandatoryGrantEvaluation
 	 */
	public void mandatorygrantevaluationInsert() {
		try {
				 service.create(this.mandatorygrantevaluation);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 mandatorygrantevaluationInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update MandatoryGrantEvaluation in database
 	 * @author TechFinium 
 	 * @see    MandatoryGrantEvaluation
 	 */
    public void mandatorygrantevaluationUpdate() {
		try {
				 service.update(this.mandatorygrantevaluation);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 mandatorygrantevaluationInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete MandatoryGrantEvaluation from database
 	 * @author TechFinium 
 	 * @see    MandatoryGrantEvaluation
 	 */
	public void mandatorygrantevaluationDelete() {
		try {
			 service.delete(this.mandatorygrantevaluation);
			  prepareNew();
			 mandatorygrantevaluationInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of MandatoryGrantEvaluation 
 	 * @author TechFinium 
 	 * @see    MandatoryGrantEvaluation
 	 */
	public void prepareNew() {
		mandatorygrantevaluation = new MandatoryGrantEvaluation();
	}

/*
    public List<SelectItem> getMandatoryGrantEvaluationDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	mandatorygrantevaluationInfo();
    	for (MandatoryGrantEvaluation ug : mandatorygrantevaluationList) {
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
    public List<MandatoryGrantEvaluation> complete(String desc) {
		List<MandatoryGrantEvaluation> l = null;
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
    
    public List<MandatoryGrantEvaluation> getMandatoryGrantEvaluationList() {
		return mandatorygrantevaluationList;
	}
	public void setMandatoryGrantEvaluationList(List<MandatoryGrantEvaluation> mandatorygrantevaluationList) {
		this.mandatorygrantevaluationList = mandatorygrantevaluationList;
	}
	public MandatoryGrantEvaluation getMandatorygrantevaluation() {
		return mandatorygrantevaluation;
	}
	public void setMandatorygrantevaluation(MandatoryGrantEvaluation mandatorygrantevaluation) {
		this.mandatorygrantevaluation = mandatorygrantevaluation;
	}

    public List<MandatoryGrantEvaluation> getMandatoryGrantEvaluationfilteredList() {
		return mandatorygrantevaluationfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param mandatorygrantevaluationfilteredList the new mandatorygrantevaluationfilteredList list
 	 * @see    MandatoryGrantEvaluation
 	 */	
	public void setMandatoryGrantEvaluationfilteredList(List<MandatoryGrantEvaluation> mandatorygrantevaluationfilteredList) {
		this.mandatorygrantevaluationfilteredList = mandatorygrantevaluationfilteredList;
	}

	
	public LazyDataModel<MandatoryGrantEvaluation> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<MandatoryGrantEvaluation> dataModel) {
		this.dataModel = dataModel;
	}
	
}
