package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.ImpactOfStaffTraining;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.ImpactOfStaffTrainingService;

// TODO: Auto-generated Javadoc
/**
 * The Class ImpactOfStaffTrainingUI.
 */
@ManagedBean(name = "impactofstafftrainingUI")
@ViewScoped
public class ImpactOfStaffTrainingUI extends AbstractUI {

	/** The service. */
	private ImpactOfStaffTrainingService service = new ImpactOfStaffTrainingService();
	
	/** The impactofstafftraining list. */
	private List<ImpactOfStaffTraining> impactofstafftrainingList = null;
	
	/** The impactofstafftrainingfiltered list. */
	private List<ImpactOfStaffTraining> impactofstafftrainingfilteredList = null;
	
	/** The impactofstafftraining. */
	private ImpactOfStaffTraining impactofstafftraining = null;
	
	/** The data model. */
	private LazyDataModel<ImpactOfStaffTraining> dataModel; 

    /**
     * Inits the.
     */
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
	 * Initialize method to get all ImpactOfStaffTraining and prepare a for a create of a new ImpactOfStaffTraining.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    ImpactOfStaffTraining
	 */
	private void runInit() throws Exception {
		prepareNew();
		impactofstafftrainingInfo();
	}

	/**
	 * Get all ImpactOfStaffTraining for data table.
	 *
	 * @author TechFinium
	 * @see    ImpactOfStaffTraining
	 */
	public void impactofstafftrainingInfo() {
	 
			
			 dataModel = new LazyDataModel<ImpactOfStaffTraining>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<ImpactOfStaffTraining> retorno = new  ArrayList<ImpactOfStaffTraining>();
			   
			   @Override 
			   public List<ImpactOfStaffTraining> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allImpactOfStaffTraining(ImpactOfStaffTraining.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(ImpactOfStaffTraining.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(ImpactOfStaffTraining obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public ImpactOfStaffTraining getRowData(String rowKey) {
			        for(ImpactOfStaffTraining obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert ImpactOfStaffTraining into database.
	 *
	 * @author TechFinium
	 * @see    ImpactOfStaffTraining
	 */
	public void impactofstafftrainingInsert() {
		try {
				 service.create(this.impactofstafftraining);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 impactofstafftrainingInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update ImpactOfStaffTraining in database.
	 *
	 * @author TechFinium
	 * @see    ImpactOfStaffTraining
	 */
    public void impactofstafftrainingUpdate() {
		try {
				 service.update(this.impactofstafftraining);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 impactofstafftrainingInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete ImpactOfStaffTraining from database.
	 *
	 * @author TechFinium
	 * @see    ImpactOfStaffTraining
	 */
	public void impactofstafftrainingDelete() {
		try {
			 service.delete(this.impactofstafftraining);
			  prepareNew();
			 impactofstafftrainingInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of ImpactOfStaffTraining .
	 *
	 * @author TechFinium
	 * @see    ImpactOfStaffTraining
	 */
	public void prepareNew() {
		impactofstafftraining = new ImpactOfStaffTraining();
	}

/*
    public List<SelectItem> getImpactOfStaffTrainingDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	impactofstafftrainingInfo();
    	for (ImpactOfStaffTraining ug : impactofstafftrainingList) {
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
    public List<ImpactOfStaffTraining> complete(String desc) {
		List<ImpactOfStaffTraining> l = null;
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
    
    /**
     * Gets the impact of staff training list.
     *
     * @return the impact of staff training list
     */
    public List<ImpactOfStaffTraining> getImpactOfStaffTrainingList() {
		return impactofstafftrainingList;
	}
	
	/**
	 * Sets the impact of staff training list.
	 *
	 * @param impactofstafftrainingList the new impact of staff training list
	 */
	public void setImpactOfStaffTrainingList(List<ImpactOfStaffTraining> impactofstafftrainingList) {
		this.impactofstafftrainingList = impactofstafftrainingList;
	}
	
	/**
	 * Gets the impactofstafftraining.
	 *
	 * @return the impactofstafftraining
	 */
	public ImpactOfStaffTraining getImpactofstafftraining() {
		return impactofstafftraining;
	}
	
	/**
	 * Sets the impactofstafftraining.
	 *
	 * @param impactofstafftraining the new impactofstafftraining
	 */
	public void setImpactofstafftraining(ImpactOfStaffTraining impactofstafftraining) {
		this.impactofstafftraining = impactofstafftraining;
	}

    /**
     * Gets the impact of staff trainingfiltered list.
     *
     * @return the impact of staff trainingfiltered list
     */
    public List<ImpactOfStaffTraining> getImpactOfStaffTrainingfilteredList() {
		return impactofstafftrainingfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param impactofstafftrainingfilteredList the new impactofstafftrainingfilteredList list
	 * @see    ImpactOfStaffTraining
	 */	
	public void setImpactOfStaffTrainingfilteredList(List<ImpactOfStaffTraining> impactofstafftrainingfilteredList) {
		this.impactofstafftrainingfilteredList = impactofstafftrainingfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<ImpactOfStaffTraining> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<ImpactOfStaffTraining> dataModel) {
		this.dataModel = dataModel;
	}
	
}
