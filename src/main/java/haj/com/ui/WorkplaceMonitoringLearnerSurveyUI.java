package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.CompanyLearners;
import haj.com.entity.WorkplaceMonitoringLearnerSurvey;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.WorkplaceMonitoringLearnerSurveyService;

@ManagedBean(name = "workplacemonitoringlearnersurveyUI")
@ViewScoped
public class WorkplaceMonitoringLearnerSurveyUI extends AbstractUI {

	private WorkplaceMonitoringLearnerSurveyService service = new WorkplaceMonitoringLearnerSurveyService();
	private List<WorkplaceMonitoringLearnerSurvey> workplacemonitoringlearnersurveyList = null;
	private List<WorkplaceMonitoringLearnerSurvey> workplacemonitoringlearnersurveyfilteredList = null;
	private WorkplaceMonitoringLearnerSurvey workplacemonitoringlearnersurvey = null;
	private LazyDataModel<WorkplaceMonitoringLearnerSurvey> dataModel; 

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
	 * Initialize method to get all WorkplaceMonitoringLearnerSurvey and prepare a for a create of a new WorkplaceMonitoringLearnerSurvey
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringLearnerSurvey
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		workplacemonitoringlearnersurveyInfo();
	}

	/**
	 * Get all WorkplaceMonitoringLearnerSurvey for data table
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringLearnerSurvey
 	 */
	public void workplacemonitoringlearnersurveyInfo() {
		dataModel = new LazyDataModel<WorkplaceMonitoringLearnerSurvey>() {
			private static final long serialVersionUID = 1L;
			private List<WorkplaceMonitoringLearnerSurvey> retorno = new ArrayList<>();
			@Override
			public List<WorkplaceMonitoringLearnerSurvey> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = service.allWorkplaceMonitoringLearnerSurvey(WorkplaceMonitoringLearnerSurvey.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(WorkplaceMonitoringLearnerSurvey.class, filters));
				} catch (Exception e) {
					addErrorMessage(e.getMessage(), e);
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(WorkplaceMonitoringLearnerSurvey obj) {
				return obj.getId();
			}

			@Override
			public WorkplaceMonitoringLearnerSurvey getRowData(String rowKey) {
				for (WorkplaceMonitoringLearnerSurvey obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}

	/**
	 * Insert WorkplaceMonitoringLearnerSurvey into database
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringLearnerSurvey
 	 */
	public void workplacemonitoringlearnersurveyInsert() {
		try {
				 service.create(this.workplacemonitoringlearnersurvey);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 workplacemonitoringlearnersurveyInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update WorkplaceMonitoringLearnerSurvey in database
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringLearnerSurvey
 	 */
    public void workplacemonitoringlearnersurveyUpdate() {
		try {
				 service.update(this.workplacemonitoringlearnersurvey);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 workplacemonitoringlearnersurveyInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete WorkplaceMonitoringLearnerSurvey from database
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringLearnerSurvey
 	 */
	public void workplacemonitoringlearnersurveyDelete() {
		try {
			 service.delete(this.workplacemonitoringlearnersurvey);
			  prepareNew();
			 workplacemonitoringlearnersurveyInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of WorkplaceMonitoringLearnerSurvey 
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringLearnerSurvey
 	 */
	public void prepareNew() {
		workplacemonitoringlearnersurvey = new WorkplaceMonitoringLearnerSurvey();
	}

/*
    public List<SelectItem> getWorkplaceMonitoringLearnerSurveyDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	workplacemonitoringlearnersurveyInfo();
    	for (WorkplaceMonitoringLearnerSurvey ug : workplacemonitoringlearnersurveyList) {
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
    public List<WorkplaceMonitoringLearnerSurvey> complete(String desc) {
		List<WorkplaceMonitoringLearnerSurvey> l = null;
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
    
    public List<WorkplaceMonitoringLearnerSurvey> getWorkplaceMonitoringLearnerSurveyList() {
		return workplacemonitoringlearnersurveyList;
	}
	public void setWorkplaceMonitoringLearnerSurveyList(List<WorkplaceMonitoringLearnerSurvey> workplacemonitoringlearnersurveyList) {
		this.workplacemonitoringlearnersurveyList = workplacemonitoringlearnersurveyList;
	}
	public WorkplaceMonitoringLearnerSurvey getWorkplacemonitoringlearnersurvey() {
		return workplacemonitoringlearnersurvey;
	}
	public void setWorkplacemonitoringlearnersurvey(WorkplaceMonitoringLearnerSurvey workplacemonitoringlearnersurvey) {
		this.workplacemonitoringlearnersurvey = workplacemonitoringlearnersurvey;
	}

    public List<WorkplaceMonitoringLearnerSurvey> getWorkplaceMonitoringLearnerSurveyfilteredList() {
		return workplacemonitoringlearnersurveyfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param workplacemonitoringlearnersurveyfilteredList the new workplacemonitoringlearnersurveyfilteredList list
 	 * @see    WorkplaceMonitoringLearnerSurvey
 	 */	
	public void setWorkplaceMonitoringLearnerSurveyfilteredList(List<WorkplaceMonitoringLearnerSurvey> workplacemonitoringlearnersurveyfilteredList) {
		this.workplacemonitoringlearnersurveyfilteredList = workplacemonitoringlearnersurveyfilteredList;
	}

	
	public LazyDataModel<WorkplaceMonitoringLearnerSurvey> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<WorkplaceMonitoringLearnerSurvey> dataModel) {
		this.dataModel = dataModel;
	}
	
}
