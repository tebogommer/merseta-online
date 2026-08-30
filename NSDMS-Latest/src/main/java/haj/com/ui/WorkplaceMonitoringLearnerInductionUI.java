package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.WorkplaceMonitoringLearnerInduction;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.WorkplaceMonitoringLearnerInductionService;

@ManagedBean(name = "workplacemonitoringlearnerinductionUI")
@ViewScoped
public class WorkplaceMonitoringLearnerInductionUI extends AbstractUI {

	private WorkplaceMonitoringLearnerInductionService service = new WorkplaceMonitoringLearnerInductionService();
	private List<WorkplaceMonitoringLearnerInduction> workplacemonitoringlearnerinductionList = null;
	private List<WorkplaceMonitoringLearnerInduction> workplacemonitoringlearnerinductionfilteredList = null;
	private WorkplaceMonitoringLearnerInduction workplacemonitoringlearnerinduction = null;
	private LazyDataModel<WorkplaceMonitoringLearnerInduction> dataModel; 

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
	 * Initialize method to get all WorkplaceMonitoringLearnerInduction and prepare a for a create of a new WorkplaceMonitoringLearnerInduction
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringLearnerInduction
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		workplacemonitoringlearnerinductionInfo();
	}

	/**
	 * Get all WorkplaceMonitoringLearnerInduction for data table
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringLearnerInduction
 	 */
	public void workplacemonitoringlearnerinductionInfo() {
		dataModel = new LazyDataModel<WorkplaceMonitoringLearnerInduction>() {
			private static final long serialVersionUID = 1L;
			private List<WorkplaceMonitoringLearnerInduction> retorno = new ArrayList<>();
			
			@Override
			public List<WorkplaceMonitoringLearnerInduction> load(int first, int pageSize, String sortField,
					SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = service.allWorkplaceMonitoringLearnerInduction(WorkplaceMonitoringLearnerInduction.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(WorkplaceMonitoringLearnerInduction.class, filters));
				} catch (Exception e) {
					addErrorMessage(e.getMessage(), e);
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(WorkplaceMonitoringLearnerInduction obj) {
				return obj.getId();
			}

			@Override
			public WorkplaceMonitoringLearnerInduction getRowData(String rowKey) {
				for (WorkplaceMonitoringLearnerInduction obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	/**
	 * Insert WorkplaceMonitoringLearnerInduction into database
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringLearnerInduction
 	 */
	public void workplacemonitoringlearnerinductionInsert() {
		try {
				 service.create(this.workplacemonitoringlearnerinduction);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 workplacemonitoringlearnerinductionInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update WorkplaceMonitoringLearnerInduction in database
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringLearnerInduction
 	 */
    public void workplacemonitoringlearnerinductionUpdate() {
		try {
				 service.update(this.workplacemonitoringlearnerinduction);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 workplacemonitoringlearnerinductionInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete WorkplaceMonitoringLearnerInduction from database
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringLearnerInduction
 	 */
	public void workplacemonitoringlearnerinductionDelete() {
		try {
			 service.delete(this.workplacemonitoringlearnerinduction);
			  prepareNew();
			 workplacemonitoringlearnerinductionInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of WorkplaceMonitoringLearnerInduction 
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringLearnerInduction
 	 */
	public void prepareNew() {
		workplacemonitoringlearnerinduction = new WorkplaceMonitoringLearnerInduction();
	}

/*
    public List<SelectItem> getWorkplaceMonitoringLearnerInductionDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	workplacemonitoringlearnerinductionInfo();
    	for (WorkplaceMonitoringLearnerInduction ug : workplacemonitoringlearnerinductionList) {
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
    public List<WorkplaceMonitoringLearnerInduction> complete(String desc) {
		List<WorkplaceMonitoringLearnerInduction> l = null;
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
    
    public List<WorkplaceMonitoringLearnerInduction> getWorkplaceMonitoringLearnerInductionList() {
		return workplacemonitoringlearnerinductionList;
	}
	public void setWorkplaceMonitoringLearnerInductionList(List<WorkplaceMonitoringLearnerInduction> workplacemonitoringlearnerinductionList) {
		this.workplacemonitoringlearnerinductionList = workplacemonitoringlearnerinductionList;
	}
	public WorkplaceMonitoringLearnerInduction getWorkplacemonitoringlearnerinduction() {
		return workplacemonitoringlearnerinduction;
	}
	public void setWorkplacemonitoringlearnerinduction(WorkplaceMonitoringLearnerInduction workplacemonitoringlearnerinduction) {
		this.workplacemonitoringlearnerinduction = workplacemonitoringlearnerinduction;
	}

    public List<WorkplaceMonitoringLearnerInduction> getWorkplaceMonitoringLearnerInductionfilteredList() {
		return workplacemonitoringlearnerinductionfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param workplacemonitoringlearnerinductionfilteredList the new workplacemonitoringlearnerinductionfilteredList list
 	 * @see    WorkplaceMonitoringLearnerInduction
 	 */	
	public void setWorkplaceMonitoringLearnerInductionfilteredList(List<WorkplaceMonitoringLearnerInduction> workplacemonitoringlearnerinductionfilteredList) {
		this.workplacemonitoringlearnerinductionfilteredList = workplacemonitoringlearnerinductionfilteredList;
	}

	
	public LazyDataModel<WorkplaceMonitoringLearnerInduction> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<WorkplaceMonitoringLearnerInduction> dataModel) {
		this.dataModel = dataModel;
	}
	
}
