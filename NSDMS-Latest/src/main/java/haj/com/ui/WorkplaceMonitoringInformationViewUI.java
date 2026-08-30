package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Signoff;
import haj.com.entity.Tasks;
import haj.com.entity.WorkplaceMonitoringActionPlan;
import haj.com.entity.WorkplaceMonitoringDgMonitoring;
import haj.com.entity.WorkplaceMonitoringDiscretionaryGrantComplianceSurvey;
import haj.com.entity.WorkplaceMonitoringLearnerInduction;
import haj.com.entity.WorkplaceMonitoringLearnerSurvey;
import haj.com.entity.WorkplaceMonitoringLearnerSurveyAnswers;
import haj.com.entity.WorkplaceMonitoringMitigationPlan;
import haj.com.entity.WorkplaceMonitoringSiteVisit;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;
import haj.com.service.SignoffService;
import haj.com.service.TasksService;
import haj.com.service.WorkplaceMonitoringActionPlanService;
import haj.com.service.WorkplaceMonitoringDgMonitoringService;
import haj.com.service.WorkplaceMonitoringDiscretionaryGrantComplianceSurveyService;
import haj.com.service.WorkplaceMonitoringLearnerInductionService;
import haj.com.service.WorkplaceMonitoringLearnerSurveyAnswersService;
import haj.com.service.WorkplaceMonitoringLearnerSurveyService;
import haj.com.service.WorkplaceMonitoringMitigationPlanService;
import haj.com.service.WorkplaceMonitoringSiteVisitService;

@ManagedBean(name = "workplaceMonitoringInformationViewUI")
@ViewScoped
public class WorkplaceMonitoringInformationViewUI extends AbstractUI {

	/* Entity Level */
	private WorkplaceMonitoringSiteVisit 		workplaceMonitoringSiteVisit 			= null;
	private WorkplaceMonitoringLearnerSurvey 	workplaceMonitoringLearnerSurveyView 	= null;
	
	/* Service Level */
	private TasksService													tasksService												= null;
	private CompanyService													companyService												= null;
	private WorkplaceMonitoringSiteVisitService 							workplaceMonitoringSiteVisitService 						= null;
	private WorkplaceMonitoringActionPlanService							workplaceMonitoringActionPlanService						= null;
	private WorkplaceMonitoringDgMonitoringService							workplaceMonitoringDgMonitoringService						= null;
	private WorkplaceMonitoringLearnerSurveyService							workplaceMonitoringLearnerSurveyService						= null;
	private WorkplaceMonitoringMitigationPlanService						workplaceMonitoringMitigationPlanService					= null;
	private WorkplaceMonitoringLearnerInductionService 						workplaceMonitoringLearnerInductionService					= null;
	private WorkplaceMonitoringLearnerSurveyAnswersService					workplaceMonitoringLearnerSurveyAnswersService				= null;
	private WorkplaceMonitoringDiscretionaryGrantComplianceSurveyService 	workplaceMonitoringDiscretionaryGrantComplianceSurveyService= null;
	
	/* Lazy Data Models */
	private LazyDataModel<Tasks> 													dataModelTasks;
	private LazyDataModel<WorkplaceMonitoringActionPlan> 							workplaceMonitoringActionPlanDataModel;
	private LazyDataModel<WorkplaceMonitoringDgMonitoring> 							workplaceMonitoringDgMonitoringDataModel;
	private LazyDataModel<WorkplaceMonitoringLearnerSurvey> 						workplaceMonitoringLearnerSurveyDataModel;
	private LazyDataModel<WorkplaceMonitoringMitigationPlan> 						workplaceMonitoringMitigationPlanDataModel;
	private LazyDataModel<WorkplaceMonitoringLearnerInduction> 						workplaceMonitoringLearnerInductionDataModel;
	private LazyDataModel<WorkplaceMonitoringLearnerSurveyAnswers> 					workplaceMonitoringLearnerSurveyAnswersDataModel;
	private LazyDataModel<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> 	workplaceMonitoringDiscretionaryGrantComplianceSurveyDataModel;
	
	/* Booleans */
	private Boolean viewAnswers = false;
	
	/* Array Lists */
	private List<Signoff> signoffList = null;
	
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

	private void runInit() throws Exception {
		workplaceMonitoringSiteVisit = null;
	}
	
	public void selectMonitoringToView(){
		try {
			if (workplaceMonitoringSiteVisit == null) {
				addErrorMessage("Unable to locate Workplace Monitoring to view. Contact support!");
			} else {
				populateInfo();
				addInfoMessage("Action Complete");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void selectMonitoringToViewNoMessage(){
		try {
			if (workplaceMonitoringSiteVisit == null) {
				addErrorMessage("Unable to locate Workplace Monitoring to view. Contact support!");
			} else {
				populateInfo();
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void populateInfo() throws Exception{
		// find by key
		if (workplaceMonitoringSiteVisitService == null) {
			workplaceMonitoringSiteVisitService = new WorkplaceMonitoringSiteVisitService();
		}
		workplaceMonitoringSiteVisit = workplaceMonitoringSiteVisitService.findByKey(workplaceMonitoringSiteVisit.getId());

		// resolve company address for org info
		if (companyService == null) {
			companyService = new CompanyService();
		}
		if (workplaceMonitoringSiteVisit.getCompany() != null && workplaceMonitoringSiteVisit.getCompany().getId() != null) {
			workplaceMonitoringSiteVisit.setCompany(companyService.resolveCompanyAddressesReturnCompany(workplaceMonitoringSiteVisit.getCompany()));
		}
		
		// Populate sign off assigned
		populateSignOffList();
		
		// Populate Tasks Assigned
		if (tasksService == null) {
			tasksService = new TasksService();
		}
		dataModelTasksInfo();
		
		// Populate Learner Induction
		if (workplaceMonitoringLearnerInductionService == null) {
			workplaceMonitoringLearnerInductionService = new WorkplaceMonitoringLearnerInductionService();
		}
		workplaceMonitoringLearnerInductionDataModelInfo();
		
		// populate Grant Compliance Survey
		if (workplaceMonitoringDiscretionaryGrantComplianceSurveyService == null) {
			workplaceMonitoringDiscretionaryGrantComplianceSurveyService = new WorkplaceMonitoringDiscretionaryGrantComplianceSurveyService();
		}
		workplaceMonitoringDiscretionaryGrantComplianceSurveyDataModelInfo();
		
		// Action plan
		if (workplaceMonitoringActionPlanService == null) {
			workplaceMonitoringActionPlanService = new WorkplaceMonitoringActionPlanService();
		}
		workplaceMonitoringActionPlanDataModelInfo();
		
		// Learner Surveys
		if (workplaceMonitoringLearnerSurveyService == null) {
			workplaceMonitoringLearnerSurveyService = new WorkplaceMonitoringLearnerSurveyService();
		}
		workplaceMonitoringLearnerSurveyDataModelInfo();
		
		// DG monitoring
		if (workplaceMonitoringDgMonitoringService == null) {
			workplaceMonitoringDgMonitoringService = new WorkplaceMonitoringDgMonitoringService();
		}
		workplaceMonitoringDgMonitoringDataModelInfo();
		
		// Mitigation plan
		if (workplaceMonitoringMitigationPlanService == null) {
			workplaceMonitoringMitigationPlanService = new WorkplaceMonitoringMitigationPlanService();
		}
		workplaceMonitoringMitigationPlanDataModelInfo();
		
		// page focus
		pageFocusModels();
		
		viewAnswers = false;
	}
	
	public void pageFocusModels() {
		try {
			String divName = "wpmInfoViewDiv";
			pageFocusRun(divName);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			logger.fatal(e.getMessage());
		}
	}
	
	public void closeMonitoringView(){
		try {
			workplaceMonitoringSiteVisit = null;
			addWarningMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void closeMonitoringViewNoMessage(){
		try {
			workplaceMonitoringSiteVisit = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void populateSignOffList() throws Exception{
		signoffList = SignoffService.instance().findByTargetKeyAndClassAndLastest(workplaceMonitoringSiteVisit.getId(), workplaceMonitoringSiteVisit.getClass().getName(), true);
	}
	
	public void dataModelTasksInfo() {
		dataModelTasks = new LazyDataModel<Tasks>() {
			private static final long serialVersionUID = 1L;
			private List<Tasks> retorno = new ArrayList<>();
			@Override
			public List<Tasks> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (sortField == null || sortField.isEmpty()) {
						sortField = "createDate";
						sortOrder = SortOrder.DESCENDING;
					}
					retorno = tasksService.allTasksByTargetClassAndKey(first, pageSize, sortField, sortOrder, filters, workplaceMonitoringSiteVisit.getId(), workplaceMonitoringSiteVisit.getClass().getName());
					dataModelTasks.setRowCount(tasksService.countAllTasksByTargetClassAndKey(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(Tasks obj) {
				return obj.getId();
			}
			@Override
			public Tasks getRowData(String rowKey) {
				for (Tasks obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void workplaceMonitoringLearnerInductionDataModelInfo() {
		workplaceMonitoringLearnerInductionDataModel = new LazyDataModel<WorkplaceMonitoringLearnerInduction>() {
			private static final long serialVersionUID = 1L;
			private List<WorkplaceMonitoringLearnerInduction> retorno = new ArrayList<>();
			@Override
			public List<WorkplaceMonitoringLearnerInduction> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = workplaceMonitoringLearnerInductionService.allWorkplaceMonitoringLearnerInductionByTargetClassAndKey(first, pageSize, sortField, sortOrder, filters, workplaceMonitoringSiteVisit.getClass().getName(), workplaceMonitoringSiteVisit.getId());
					workplaceMonitoringLearnerInductionDataModel.setRowCount(workplaceMonitoringLearnerInductionService.countAllWorkplaceMonitoringLearnerInductionByTargetClassAndKey(filters));
				} catch (Exception e) {
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
	
	public void workplaceMonitoringDiscretionaryGrantComplianceSurveyDataModelInfo() {
		workplaceMonitoringDiscretionaryGrantComplianceSurveyDataModel = new LazyDataModel<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey>() {
			private static final long serialVersionUID = 1L;
			private List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> retorno = new ArrayList<>();
			@Override
			public List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = workplaceMonitoringDiscretionaryGrantComplianceSurveyService.allWorkplaceMonitoringDiscretionaryGrantComplianceSurveyByTargetClassAndKey(first, pageSize, sortField, sortOrder, filters, workplaceMonitoringSiteVisit.getClass().getName(), workplaceMonitoringSiteVisit.getId());
					workplaceMonitoringDiscretionaryGrantComplianceSurveyDataModel.setRowCount(workplaceMonitoringDiscretionaryGrantComplianceSurveyService.countAllWorkplaceMonitoringDiscretionaryGrantComplianceSurveyByTargetClassAndKey(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(WorkplaceMonitoringDiscretionaryGrantComplianceSurvey obj) {
				return obj.getId();
			}
			@Override
			public WorkplaceMonitoringDiscretionaryGrantComplianceSurvey getRowData(String rowKey) {
				for (WorkplaceMonitoringDiscretionaryGrantComplianceSurvey obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void workplaceMonitoringActionPlanDataModelInfo() {
		workplaceMonitoringActionPlanDataModel = new LazyDataModel<WorkplaceMonitoringActionPlan>() {
			private static final long serialVersionUID = 1L;
			private List<WorkplaceMonitoringActionPlan> retorno = new ArrayList<>();
			@Override
			public List<WorkplaceMonitoringActionPlan> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = workplaceMonitoringActionPlanService.allWorkplaceMonitoringActionPlanByTargetClassAndKey(first, pageSize, sortField, sortOrder, filters, workplaceMonitoringSiteVisit.getClass().getName(), workplaceMonitoringSiteVisit.getId());
					workplaceMonitoringActionPlanDataModel.setRowCount(workplaceMonitoringActionPlanService.countAllWorkplaceMonitoringActionPlanByTargetClassAndKey(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(WorkplaceMonitoringActionPlan obj) {
				return obj.getId();
			}
			@Override
			public WorkplaceMonitoringActionPlan getRowData(String rowKey) {
				for (WorkplaceMonitoringActionPlan obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void workplaceMonitoringLearnerSurveyDataModelInfo() {
		workplaceMonitoringLearnerSurveyDataModel = new LazyDataModel<WorkplaceMonitoringLearnerSurvey>() {
			private static final long serialVersionUID = 1L;
			private List<WorkplaceMonitoringLearnerSurvey> retorno = new ArrayList<>();
			@Override
			public List<WorkplaceMonitoringLearnerSurvey> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = workplaceMonitoringLearnerSurveyService.allWorkplaceMonitoringLearnerSurveyByTargetClassAndKey(first, pageSize, sortField, sortOrder, filters, workplaceMonitoringSiteVisit.getClass().getName(), workplaceMonitoringSiteVisit.getId());
					workplaceMonitoringLearnerSurveyDataModel.setRowCount(workplaceMonitoringLearnerSurveyService.countAllWorkplaceMonitoringLearnerSurveyByTargetClassAndKey(filters));
				} catch (Exception e) {
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
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
		 
	public void workplaceMonitoringDgMonitoringDataModelInfo() {
		workplaceMonitoringDgMonitoringDataModel = new LazyDataModel<WorkplaceMonitoringDgMonitoring>() {
			private static final long serialVersionUID = 1L;
			private List<WorkplaceMonitoringDgMonitoring> retorno = new ArrayList<>();
			@Override
			public List<WorkplaceMonitoringDgMonitoring> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = workplaceMonitoringDgMonitoringService.allWorkplaceMonitoringDgMonitoringByTargetClassAndKey(first, pageSize, sortField, sortOrder, filters, workplaceMonitoringSiteVisit.getClass().getName(), workplaceMonitoringSiteVisit.getId());
					workplaceMonitoringDgMonitoringDataModel.setRowCount(workplaceMonitoringDgMonitoringService.countAllWorkplaceMonitoringDgMonitoringByTargetClassAndKey(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(WorkplaceMonitoringDgMonitoring obj) {
				return obj.getId();
			}
			@Override
			public WorkplaceMonitoringDgMonitoring getRowData(String rowKey) {
				for (WorkplaceMonitoringDgMonitoring obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void workplaceMonitoringMitigationPlanDataModelInfo() {
		workplaceMonitoringMitigationPlanDataModel = new LazyDataModel<WorkplaceMonitoringMitigationPlan>() {
			private static final long serialVersionUID = 1L;
			private List<WorkplaceMonitoringMitigationPlan> retorno = new ArrayList<>();
			@Override
			public List<WorkplaceMonitoringMitigationPlan> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = workplaceMonitoringMitigationPlanService.allWorkplaceMonitoringMitigationPlanBySiteVisit(first, pageSize, sortField, sortOrder, filters, workplaceMonitoringSiteVisit.getId());
					workplaceMonitoringMitigationPlanDataModel.setRowCount(workplaceMonitoringMitigationPlanService.countAllWorkplaceMonitoringMitigationPlanBySiteVisit(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(WorkplaceMonitoringMitigationPlan obj) {
				return obj.getId();
			}
			@Override
			public WorkplaceMonitoringMitigationPlan getRowData(String rowKey) {
				for (WorkplaceMonitoringMitigationPlan obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void viewLearnerSurveyAnswers(){
		try {
			viewAnswers = true;
			if (workplaceMonitoringLearnerSurveyAnswersService == null) {
				workplaceMonitoringLearnerSurveyAnswersService = new WorkplaceMonitoringLearnerSurveyAnswersService();
			}
			workplaceMonitoringLearnerSurveyAnswersDataModelInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void workplaceMonitoringLearnerSurveyAnswersDataModelInfo() {
		workplaceMonitoringLearnerSurveyAnswersDataModel = new LazyDataModel<WorkplaceMonitoringLearnerSurveyAnswers>() {
			private static final long serialVersionUID = 1L;
			private List<WorkplaceMonitoringLearnerSurveyAnswers> retorno = new ArrayList<>();
			@Override
			public List<WorkplaceMonitoringLearnerSurveyAnswers> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (workplaceMonitoringLearnerSurveyView != null && workplaceMonitoringLearnerSurveyView.getId() != null) {
						retorno = workplaceMonitoringLearnerSurveyAnswersService.allWorkplaceMonitoringLearnerSurveyAnswersByTargetClassAndKey(first, pageSize, sortField, sortOrder, filters, workplaceMonitoringLearnerSurveyView.getClass().getName(), workplaceMonitoringLearnerSurveyView.getId());
						workplaceMonitoringLearnerSurveyAnswersDataModel.setRowCount(workplaceMonitoringLearnerSurveyAnswersService.countAllWorkplaceMonitoringLearnerSurveyAnswersByTargetClassAndKey(filters));
					}
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(WorkplaceMonitoringLearnerSurveyAnswers obj) {
				return obj.getId();
			}
			@Override
			public WorkplaceMonitoringLearnerSurveyAnswers getRowData(String rowKey) {
				for (WorkplaceMonitoringLearnerSurveyAnswers obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void closeViewLearnerSurveyAnswers(){
		try {
			viewAnswers = false;
			workplaceMonitoringLearnerSurveyView = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/* Getters and Setters */
	public WorkplaceMonitoringSiteVisit getWorkplaceMonitoringSiteVisit() {
		return workplaceMonitoringSiteVisit;
	}

	public void setWorkplaceMonitoringSiteVisit(WorkplaceMonitoringSiteVisit workplaceMonitoringSiteVisit) {
		this.workplaceMonitoringSiteVisit = workplaceMonitoringSiteVisit;
	}

	public List<Signoff> getSignoffList() {
		return signoffList;
	}

	public void setSignoffList(List<Signoff> signoffList) {
		this.signoffList = signoffList;
	}

	public LazyDataModel<Tasks> getDataModelTasks() {
		return dataModelTasks;
	}

	public void setDataModelTasks(LazyDataModel<Tasks> dataModelTasks) {
		this.dataModelTasks = dataModelTasks;
	}

	public LazyDataModel<WorkplaceMonitoringActionPlan> getWorkplaceMonitoringActionPlanDataModel() {
		return workplaceMonitoringActionPlanDataModel;
	}

	public void setWorkplaceMonitoringActionPlanDataModel(
			LazyDataModel<WorkplaceMonitoringActionPlan> workplaceMonitoringActionPlanDataModel) {
		this.workplaceMonitoringActionPlanDataModel = workplaceMonitoringActionPlanDataModel;
	}

	public LazyDataModel<WorkplaceMonitoringLearnerSurvey> getWorkplaceMonitoringLearnerSurveyDataModel() {
		return workplaceMonitoringLearnerSurveyDataModel;
	}

	public void setWorkplaceMonitoringLearnerSurveyDataModel(
			LazyDataModel<WorkplaceMonitoringLearnerSurvey> workplaceMonitoringLearnerSurveyDataModel) {
		this.workplaceMonitoringLearnerSurveyDataModel = workplaceMonitoringLearnerSurveyDataModel;
	}

	public LazyDataModel<WorkplaceMonitoringLearnerInduction> getWorkplaceMonitoringLearnerInductionDataModel() {
		return workplaceMonitoringLearnerInductionDataModel;
	}

	public void setWorkplaceMonitoringLearnerInductionDataModel(
			LazyDataModel<WorkplaceMonitoringLearnerInduction> workplaceMonitoringLearnerInductionDataModel) {
		this.workplaceMonitoringLearnerInductionDataModel = workplaceMonitoringLearnerInductionDataModel;
	}

	public LazyDataModel<WorkplaceMonitoringLearnerSurveyAnswers> getWorkplaceMonitoringLearnerSurveyAnswersDataModel() {
		return workplaceMonitoringLearnerSurveyAnswersDataModel;
	}

	public void setWorkplaceMonitoringLearnerSurveyAnswersDataModel(
			LazyDataModel<WorkplaceMonitoringLearnerSurveyAnswers> workplaceMonitoringLearnerSurveyAnswersDataModel) {
		this.workplaceMonitoringLearnerSurveyAnswersDataModel = workplaceMonitoringLearnerSurveyAnswersDataModel;
	}

	public LazyDataModel<WorkplaceMonitoringDgMonitoring> getWorkplaceMonitoringDgMonitoringDataModel() {
		return workplaceMonitoringDgMonitoringDataModel;
	}

	public void setWorkplaceMonitoringDgMonitoringDataModel(
			LazyDataModel<WorkplaceMonitoringDgMonitoring> workplaceMonitoringDgMonitoringDataModel) {
		this.workplaceMonitoringDgMonitoringDataModel = workplaceMonitoringDgMonitoringDataModel;
	}

	public LazyDataModel<WorkplaceMonitoringMitigationPlan> getWorkplaceMonitoringMitigationPlanDataModel() {
		return workplaceMonitoringMitigationPlanDataModel;
	}

	public void setWorkplaceMonitoringMitigationPlanDataModel(
			LazyDataModel<WorkplaceMonitoringMitigationPlan> workplaceMonitoringMitigationPlanDataModel) {
		this.workplaceMonitoringMitigationPlanDataModel = workplaceMonitoringMitigationPlanDataModel;
	}

	public LazyDataModel<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> getWorkplaceMonitoringDiscretionaryGrantComplianceSurveyDataModel() {
		return workplaceMonitoringDiscretionaryGrantComplianceSurveyDataModel;
	}

	public void setWorkplaceMonitoringDiscretionaryGrantComplianceSurveyDataModel(
			LazyDataModel<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> workplaceMonitoringDiscretionaryGrantComplianceSurveyDataModel) {
		this.workplaceMonitoringDiscretionaryGrantComplianceSurveyDataModel = workplaceMonitoringDiscretionaryGrantComplianceSurveyDataModel;
	}

	public WorkplaceMonitoringLearnerSurvey getWorkplaceMonitoringLearnerSurveyView() {
		return workplaceMonitoringLearnerSurveyView;
	}

	public void setWorkplaceMonitoringLearnerSurveyView(
			WorkplaceMonitoringLearnerSurvey workplaceMonitoringLearnerSurveyView) {
		this.workplaceMonitoringLearnerSurveyView = workplaceMonitoringLearnerSurveyView;
	}

	public Boolean getViewAnswers() {
		return viewAnswers;
	}

	public void setViewAnswers(Boolean viewAnswers) {
		this.viewAnswers = viewAnswers;
	}
		
}