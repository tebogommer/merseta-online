package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.Doc;
import haj.com.entity.Signoff;
import haj.com.entity.TempSignoff;
import haj.com.entity.Users;
import haj.com.entity.WorkplaceMonitoringActionPlan;
import haj.com.entity.WorkplaceMonitoringDgMonitoring;
import haj.com.entity.WorkplaceMonitoringDiscretionaryGrantComplianceSurvey;
import haj.com.entity.WorkplaceMonitoringLearnerInduction;
import haj.com.entity.WorkplaceMonitoringLearnerPayments;
import haj.com.entity.WorkplaceMonitoringLearnerSurvey;
import haj.com.entity.WorkplaceMonitoringLearnerSurveyAnswers;
import haj.com.entity.WorkplaceMonitoringMitigationPlan;
import haj.com.entity.WorkplaceMonitoringSiteVisit;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.IdPassportEnum;
import haj.com.entity.enums.TaskStatusEnum;
import haj.com.entity.enums.UsersStatusEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.framework.IDataEntity;
import haj.com.service.CompanyService;
import haj.com.service.CompanyUsersService;
import haj.com.service.DocService;
import haj.com.service.LearnersService;
import haj.com.service.SignoffService;
import haj.com.service.TasksService;
import haj.com.service.TempSignoffService;
import haj.com.service.UsersService;
import haj.com.service.WorkplaceMonitoringActionPlanService;
import haj.com.service.WorkplaceMonitoringDgMonitoringService;
import haj.com.service.WorkplaceMonitoringDiscretionaryGrantComplianceSurveyService;
import haj.com.service.WorkplaceMonitoringLearnerInductionService;
import haj.com.service.WorkplaceMonitoringLearnerPaymentsService;
import haj.com.service.WorkplaceMonitoringLearnerSurveyAnswersService;
import haj.com.service.WorkplaceMonitoringLearnerSurveyService;
import haj.com.service.WorkplaceMonitoringMitigationPlanService;
import haj.com.service.WorkplaceMonitoringSiteVisitService;
import haj.com.service.lookup.RejectReasonsService;

/**
 * The Class WorkplaceMonitoringSiteVisitWorkflowUI.
 */
@ManagedBean(name = "workplaceMonitoringSiteVisitWorkflowUI")
@ViewScoped
public class WorkplaceMonitoringSiteVisitWorkflowUI extends AbstractUI {

	// entity levels
	private Doc 									doc 									= null;
	private Company 								company 								= null;
	private WorkplaceMonitoringSiteVisit 			workplaceMonitoringSiteVisit 			= null;
	private Signoff				 					signOffSelected 						= null;
	private Users									userSelectionForMoaSignOff				= null;
	private ConfigDocProcessEnum					workflowProcess							= null;
	private CompanyLearners							companyLearners							= null;
	private IDataEntity								genericUploadObject						= null;
	private WorkplaceMonitoringActionPlan 			workplaceMonitoringActionPlan			= new WorkplaceMonitoringActionPlan();
	private WorkplaceMonitoringDgMonitoring			workplaceMonitoringDgMonitoring			= new WorkplaceMonitoringDgMonitoring();
	private WorkplaceMonitoringLearnerSurvey 		workplaceMonitoringLearnerSurvey 		= new WorkplaceMonitoringLearnerSurvey();
	private WorkplaceMonitoringMitigationPlan		workplaceMonitoringMitigationPlan		= null;
	private WorkplaceMonitoringLearnerPayments		workplaceMonitoringLearnerPayments		= null;
	private WorkplaceMonitoringLearnerInduction 	workplaceMonitoringLearnerInduction 	= new WorkplaceMonitoringLearnerInduction();
	private WorkplaceMonitoringLearnerSurvey 		workplaceMonitoringLearnerSurveyView 	= null;
	private WorkplaceMonitoringLearnerSurveyAnswers workplaceMonitoringLearnerSurveyAnswers = new WorkplaceMonitoringLearnerSurveyAnswers();
	
	private WorkplaceMonitoringDiscretionaryGrantComplianceSurvey workplaceMonitoringDiscretionaryGrantComplianceSurvey = new WorkplaceMonitoringDiscretionaryGrantComplianceSurvey();
	
	/* Service levels */
	private UsersService									usersService												= new UsersService();
	private LearnersService 								learnersService 											= new LearnersService();
	private CompanyService 									companyService 												= new CompanyService();
	private CompanyUsersService								companyUsersService											= new CompanyUsersService();
	private RejectReasonsService							rejectReasonsService										= new RejectReasonsService();
	private WorkplaceMonitoringSiteVisitService 			workplaceMonitoringSiteVisitService 						= new WorkplaceMonitoringSiteVisitService();
	private WorkplaceMonitoringActionPlanService			workplaceMonitoringActionPlanService						= new WorkplaceMonitoringActionPlanService();
	private WorkplaceMonitoringDgMonitoringService			workplaceMonitoringDgMonitoringService						= new WorkplaceMonitoringDgMonitoringService();
	private WorkplaceMonitoringLearnerSurveyService			workplaceMonitoringLearnerSurveyService						= new WorkplaceMonitoringLearnerSurveyService();
	private WorkplaceMonitoringMitigationPlanService		workplaceMonitoringMitigationPlanService					= new WorkplaceMonitoringMitigationPlanService();
	private WorkplaceMonitoringLearnerPaymentsService		workplaceMonitoringLearnerPaymentsService					= new WorkplaceMonitoringLearnerPaymentsService();
	private WorkplaceMonitoringLearnerInductionService 		workplaceMonitoringLearnerInductionService					= new WorkplaceMonitoringLearnerInductionService();
	private WorkplaceMonitoringLearnerSurveyAnswersService	workplaceMonitoringLearnerSurveyAnswersService				= new WorkplaceMonitoringLearnerSurveyAnswersService();
	
	
	private WorkplaceMonitoringDiscretionaryGrantComplianceSurveyService 	workplaceMonitoringDiscretionaryGrantComplianceSurveyService= new WorkplaceMonitoringDiscretionaryGrantComplianceSurveyService();
	
	/* Lazy data models */
	// all company learners assigned
	private LazyDataModel<CompanyLearners> 											allLearnersDataModel;
	// Action Plan
	private LazyDataModel<WorkplaceMonitoringActionPlan> 							workplaceMonitoringActionPlanDataModel;
	// Learner Survey
	private LazyDataModel<WorkplaceMonitoringLearnerSurvey> 						workplaceMonitoringLearnerSurveyDataModel;
	// learner induction
	private LazyDataModel<WorkplaceMonitoringLearnerInduction> 						workplaceMonitoringLearnerInductionDataModel;
	// Learner Survey Answers
	private LazyDataModel<WorkplaceMonitoringLearnerSurveyAnswers> 					workplaceMonitoringLearnerSurveyAnswersDataModel;
	// DG Monitoring
	private LazyDataModel<WorkplaceMonitoringDgMonitoring> 							workplaceMonitoringDgMonitoringDataModel;
	// MITIGATION PLAN
	private LazyDataModel<WorkplaceMonitoringMitigationPlan> 						workplaceMonitoringMitigationPlanDataModel;
	// Learner Payments
	private LazyDataModel<WorkplaceMonitoringLearnerPayments> 						workplaceMonitoringLearnerPaymentsDataModel;
	// Discretionary Grant Compliance Survey
	private LazyDataModel<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> 	workplaceMonitoringDiscretionaryGrantComplianceSurveyDataModel;
	
	// Array Lists
	private List<Signoff> 									signoffList 								= null;
	private List<Users> 									userSelectionList 							= null;
	private List<RejectReasons> 							rejectReasonsList 							= null;
	private List<RejectReasons> 							selectedRejectReasonsList 					= null;
	private List<WorkplaceMonitoringLearnerSurveyAnswers> 	workplaceMonitoringLearnerSurveyAnswersList = null;
	
	// Vars
	private Boolean accept 					= false;
	private Boolean viewAnswers				= false;
	private Boolean newlearnerSurvey		= false;
	private String 	currentSignOffUserInfo	= "";
	private Integer updateIndicatoryScreen  = null;
	
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

	private void runInit() throws Exception {
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.WorkplaceMonitoringSiteVisit) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			if (getSessionUI().getTask().getTaskStatus() == TaskStatusEnum.Completed) {
				getSessionUI().setTask(null);
				ajaxRedirectToDashboard();
			}
			workflowProcess = getSessionUI().getTask().getWorkflowProcess();
			workplaceMonitoringSiteVisit = workplaceMonitoringSiteVisitService.findByKey(getSessionUI().getTask().getTargetKey());
			company = companyService.findByKey(workplaceMonitoringSiteVisit.getCompany().getId());
			companyService.resolveCompanyAddresses(company);
			
			// preps docs
			workplaceMonitoringSiteVisitService.prepareDocumentsForWorkplaceMonitoringSiteVisit(workflowProcess, workplaceMonitoringSiteVisit, CompanyUserTypeEnum.Company);
			
			// Determines actions in Abstract UI
			determainUserActions();
			
			// Populate sign off assigned
			populateSignOffList();
			
			// populate rejection reasons
			populateRejectReasonsAssigned();
			
			// populate learner induction 
			workplaceMonitoringLearnerInductionDataModelInfo();
			
			// populate Grant Compliance Survey
			workplaceMonitoringDiscretionaryGrantComplianceSurveyDataModelInfo();
			
			// Action plan
			workplaceMonitoringActionPlanDataModelInfo();
			
			// Learner Surveys
			workplaceMonitoringLearnerSurveyDataModelInfo();
			
			// DG monitoring
			workplaceMonitoringDgMonitoringDataModelInfo();
			
			// Mitigation plan
			workplaceMonitoringMitigationPlanDataModelInfo();
			
			// Learner Payments
			workplaceMonitoringLearnerPaymentsDataModelInfo();
		} else {
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		}
	}
	
	private void populateSignOffList() throws Exception{
		signoffList = SignoffService.instance().findByTargetKeyAndClassAndLastest(workplaceMonitoringSiteVisit.getId(), workplaceMonitoringSiteVisit.getClass().getName(), true);
	}
	
	private void populateRejectReasonsAssigned() throws Exception {
		if (this.workplaceMonitoringSiteVisit != null && this.workplaceMonitoringSiteVisit.getId() != null) {
			rejectReasonsList = rejectReasonsService.locateReasonsSelectedByTargetKeyAndClass(this.workplaceMonitoringSiteVisit.getId(), this.workplaceMonitoringSiteVisit.getClass().getName());
		} else {
			rejectReasonsList = null;
		}
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
	
	public void workplaceMonitoringLearnerPaymentsDataModelInfo() {
		workplaceMonitoringLearnerPaymentsDataModel = new LazyDataModel<WorkplaceMonitoringLearnerPayments>() {
			private static final long serialVersionUID = 1L;
			private List<WorkplaceMonitoringLearnerPayments> retorno = new ArrayList<>();
			@Override
			public List<WorkplaceMonitoringLearnerPayments> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = workplaceMonitoringLearnerPaymentsService.allWorkplaceMonitoringLearnerPaymentsByTargetClassAndKey(first, pageSize, sortField, sortOrder, filters, workplaceMonitoringSiteVisit.getClass().getName(), workplaceMonitoringSiteVisit.getId());
					workplaceMonitoringLearnerPaymentsDataModel.setRowCount(workplaceMonitoringLearnerPaymentsService.countAllWorkplaceMonitoringLearnerPaymentsByTargetClassAndKey(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(WorkplaceMonitoringLearnerPayments obj) {
				return obj.getId();
			}
			@Override
			public WorkplaceMonitoringLearnerPayments getRowData(String rowKey) {
				for (WorkplaceMonitoringLearnerPayments obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void prepUpdateLearnerInduction(){
		try {
			runClientSideExecute("PF('learnerInductionDlg').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepNewLearnerInduction(){
		try {
			workplaceMonitoringLearnerInduction = new WorkplaceMonitoringLearnerInduction();
			workplaceMonitoringLearnerInduction.setSystemGenerated(false);
			runClientSideExecute("PF('learnerInductionDlg').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void createUpdateLearnerInduction(){
		try {
			workplaceMonitoringLearnerInductionService.createUpdateLearnerInduction(workplaceMonitoringLearnerInduction, workplaceMonitoringSiteVisit.getClass().getName(), workplaceMonitoringSiteVisit.getId() , getSessionUI().getActiveUser(), workplaceMonitoringLearnerInduction.getSystemGenerated());
			runClientSideExecute("PF('learnerInductionDlg').hide()");
			workplaceMonitoringLearnerInductionDataModelInfo();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void deleteLearnerInduction(){
		try {
			if (workplaceMonitoringLearnerInduction.getSystemGenerated()) {
				addErrorMessage("System generated Learner Inductions can not be deleted.");
			}else {
				workplaceMonitoringLearnerInductionService.delete(workplaceMonitoringLearnerInduction);
				workplaceMonitoringLearnerInductionDataModelInfo();
				addWarningMessage("Action Complete");
			}
			workplaceMonitoringLearnerInduction = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void viewLearnerSurveyAnswers(){
		try {
			viewAnswers = true;
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
	
	public void prepNewLearnerSurvey(){
		try {
			
			viewAnswers = false;
			
			workplaceMonitoringLearnerSurveyView = null;
			
			workplaceMonitoringLearnerSurvey = new WorkplaceMonitoringLearnerSurvey();
			workplaceMonitoringLearnerSurvey.setTargetClass(workplaceMonitoringSiteVisit.getClass().getName());
			workplaceMonitoringLearnerSurvey.setTargetKey(workplaceMonitoringSiteVisit.getId());
			
			companyLearners = null;
			
			workplaceMonitoringLearnerSurveyAnswersList = new ArrayList<>();
			
			allLearnersDataModelInfo();
			
			runClientSideExecute("PF('learnerSurveyDlg').show()");
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void allLearnersDataModelInfo() {
		allLearnersDataModel = new LazyDataModel<CompanyLearners>() {
			private static final long serialVersionUID = 1L;
			private List<CompanyLearners> retorno = new ArrayList<>();
			@Override
			public List<CompanyLearners> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = learnersService.allLearnersAssignedToEmployerWithoutStatusWithoutAdditionalLoad(first, pageSize, sortField, sortOrder, filters, workplaceMonitoringSiteVisit.getCompany().getId());
					allLearnersDataModel.setRowCount(learnersService.countAllLearnersAssignedToEmployerWithoutStatus(filters));
				} catch (Exception e) {
					addErrorMessage(e.getMessage(), e);
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(CompanyLearners obj) {
				return obj.getId();
			}

			@Override
			public CompanyLearners getRowData(String rowKey) {
				for (CompanyLearners obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}
	
	public void populateQuestionsBasedOnLearnerSelection() {
		try {
			if (workplaceMonitoringLearnerSurveyService.countByTargetClassKeyAndCompanyLearner(workplaceMonitoringSiteVisit.getClass().getName(),workplaceMonitoringSiteVisit.getId(), companyLearners.getId()) == 0) {
				workplaceMonitoringLearnerSurvey.setCompanyLearners(companyLearners);
				workplaceMonitoringLearnerSurvey.setInterventionType(companyLearners.getInterventionType());
				if (workplaceMonitoringLearnerSurvey.getCompanyLearners() != null && workplaceMonitoringLearnerSurvey.getInterventionType() != null && workplaceMonitoringLearnerSurvey.getInterventionType().getId() != null) {
					workplaceMonitoringLearnerSurveyAnswersList = workplaceMonitoringLearnerSurveyAnswersService.findByNoTargetClassAndKeyByIntervetionType(workplaceMonitoringLearnerSurvey.getInterventionType().getId());
				} else {
					addErrorMessage("Unable to locate comapany learner / intervention type. Contact Support!");
					workplaceMonitoringLearnerSurveyAnswersList = new ArrayList<>();
				}
			} else {
				addErrorMessage("Learner already has a survey assigned to monitoirng visit, select a different learner");
				workplaceMonitoringLearnerSurveyAnswersList = new ArrayList<>();
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void createLearnerSurveyAnswers() {
		try {
			workplaceMonitoringLearnerSurveyService.createFullSurvey(workplaceMonitoringSiteVisit, workplaceMonitoringLearnerSurvey, workplaceMonitoringLearnerSurveyAnswersList, getSessionUI().getActiveUser());
			workplaceMonitoringLearnerSurvey = null;
			workplaceMonitoringLearnerSurveyAnswersList = null;
			workplaceMonitoringLearnerSurveyDataModelInfo();
			workplaceMonitoringMitigationPlanDataModelInfo();
			runClientSideExecute("PF('learnerSurveyDlg').hide()");
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepUpdateComplianceSurvey(){
		try {
			runClientSideExecute("PF('grantComplianceSurveyDlg').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void updateComplianceSurvey(){
		try {
			workplaceMonitoringDiscretionaryGrantComplianceSurveyService.updateEntryAndActionPlan(workplaceMonitoringSiteVisit, workplaceMonitoringDiscretionaryGrantComplianceSurvey, getSessionUI().getActiveUser()); 
			runClientSideExecute("PF('grantComplianceSurveyDlg').hide()");
			workplaceMonitoringDiscretionaryGrantComplianceSurveyDataModelInfo();
			workplaceMonitoringMitigationPlanDataModelInfo();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepUpdateActionPlan(){
		try {
			runClientSideExecute("PF('actionPlanDlg').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void updateActionPlan(){
		try {
			workplaceMonitoringActionPlanService.updateExisitingEntry(workplaceMonitoringActionPlan, getSessionUI().getActiveUser()); 
			runClientSideExecute("PF('actionPlanDlg').hide()");
			workplaceMonitoringActionPlanDataModelInfo();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepUpdateMitigationPlan(){
		try {
			runClientSideExecute("PF('mitigationPlanDlg').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void updateMitigationPlan(){
		try {
			workplaceMonitoringMitigationPlanService.updatePlan(workplaceMonitoringMitigationPlan, getSessionUI().getActiveUser());
			runClientSideExecute("PF('mitigationPlanDlg').hide()");
			workplaceMonitoringMitigationPlanDataModelInfo();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepUpdateLearnerPayments(){
		try {
			runClientSideExecute("PF('learnerPaymentsDlg').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void updateLearnerPayments(){
		try {
			workplaceMonitoringLearnerPaymentsService.updateInfo(workplaceMonitoringLearnerPayments, getSessionUI().getActiveUser());
			runClientSideExecute("PF('learnerPaymentsDlg').hide()");
			workplaceMonitoringLearnerPaymentsDataModelInfo();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void updateLearnerPayment(){
		try {
			if (workplaceMonitoringLearnerPayments.getPayTranchPayment()) {
				workplaceMonitoringLearnerPayments.setPayTranchPayment(false);
			} else {
				workplaceMonitoringLearnerPayments.setPayTranchPayment(true);
			}
			workplaceMonitoringLearnerPaymentsService.updateInfo(workplaceMonitoringLearnerPayments, getSessionUI().getActiveUser());
			workplaceMonitoringLearnerPaymentsDataModelInfo();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void rerunLearnerPaymentsChecks(){
		try {
			workplaceMonitoringLearnerPaymentsService.reRunChecksForPayments(workplaceMonitoringSiteVisit.getClass().getName(), workplaceMonitoringSiteVisit.getId());
			workplaceMonitoringLearnerPaymentsDataModelInfo();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepChangeSignOffUser(){
		try {
			userSelectionList = companyUsersService.findDistinctUsersByCompany(workplaceMonitoringSiteVisit.getCompany());
			if (signOffSelected.getUser() != null && signOffSelected.getUser().getId() != null) {
				Users currentSignOffUser = usersService.findByKey(signOffSelected.getUser().getId());
				currentSignOffUserInfo = currentSignOffUser.getFirstName() + " " + currentSignOffUser.getLastName();
			} else if (signOffSelected.getTempSignoff() != null && signOffSelected.getTempSignoff().getId() != null) {
				currentSignOffUserInfo = signOffSelected.getTempSignoff().getFirstName() + " " + signOffSelected.getTempSignoff().getLastName();
			}
			userSelectionForMoaSignOff = null;
			runClientSideExecute("PF('changeSignOffUserDlg').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void changeSignOffType(){
		try {
			if (signOffSelected.getExternalUserSignoff()) {
				if (signOffSelected.getTempSignoff() == null) {
					TempSignoff tps = new TempSignoff();
					signOffSelected.setTempSignoff(tps);
				}
			} else {
				signOffSelected.setUser(null);
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void changeSignOffUser(){
		try {
			if (signOffSelected.getExternalUserSignoff()) {
				if (signOffSelected.getTempSignoff().getId() == null) {
					TempSignoff tpo = signOffSelected.getTempSignoff();
					tpo.setLastActionUser(getSessionUI().getActiveUser());
					tpo.setLastActionDate(getNow());
					if (tpo.getIdPassportSelection() != null && tpo.getIdPassportSelection() == IdPassportEnum.Passport) {
						tpo.setIdNumber(null);
					}else {
						tpo.setPassportNumber(null);
					}
					if (signOffSelected.getId() != null) {
						tpo.setRefToSignOffFlat(signOffSelected.getId());
					}
					TempSignoffService.instance().create(tpo);
					signOffSelected.setTempSignoff(tpo);
				} else {
					signOffSelected.getTempSignoff().setLastActionUser(getSessionUI().getActiveUser());
					signOffSelected.getTempSignoff().setLastActionDate(getNow());
					if (signOffSelected.getTempSignoff().getIdPassportSelection() != null && signOffSelected.getTempSignoff().getIdPassportSelection() == IdPassportEnum.Passport) {
						signOffSelected.getTempSignoff().setIdNumber(null);
					} else {
						signOffSelected.getTempSignoff().setPassportNumber(null);
					}
					TempSignoffService.instance().update(signOffSelected.getTempSignoff());
				}
				signOffSelected.setUser(null);
				signOffSelected.setDateSignOffUserChanged(getNow());
				signOffSelected.setExpiryDate(null);
				signOffSelected.setOneTimePassword(null);
				signOffSelected.setChangeUser(getSessionUI().getActiveUser());
				signOffSelected.setAccept(false);
				signOffSelected.setSignOffDate(null);
				SignoffService.instance().update(signOffSelected);
				addInfoMessage("Sign Off User Assigned");
				runClientSideExecute("PF('changeSignOffUserDlg').hide()");
			} else {
				if (userSelectionForMoaSignOff != null) {
					// validate sign off
					if (userSelectionForMoaSignOff.getStatus() != UsersStatusEnum.Active || userSelectionForMoaSignOff.getLastLogin() == null) {
						if (userSelectionForMoaSignOff.getStatus() == UsersStatusEnum.InActive) {
							throw new Exception("Selected User Is In-Active, Please Select A Different User For Sign Off");
						} else if (userSelectionForMoaSignOff.getStatus() == UsersStatusEnum.EmailNotConfrimed) {
							throw new Exception("Selected user has not confirmed their email address. Please select a different user for sign off.");
						} else {
							throw new Exception("Selected user has not completed first time log in. Please select a different user for sign off.");
						}
					}
					signOffSelected.setAccept(false);
					signOffSelected.setSignOffDate(null);
					signOffSelected.setUser(userSelectionForMoaSignOff);
					signOffSelected.setDateSignOffUserChanged(getNow());
					signOffSelected.setExpiryDate(null);
					signOffSelected.setOneTimePassword(null);
					signOffSelected.setChangeUser(getSessionUI().getActiveUser());
					signOffSelected.setTempSignoff(null);
					SignoffService.instance().update(signOffSelected);
					addInfoMessage("Sign Off User Assigned");
					runClientSideExecute("PF('changeSignOffUserDlg').hide()");
				} else {
					addErrorMessage("Select A User For Sign Off");
				}
			}
			populateSignOffList();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void testExternalSignOffUserEmail(){
		try {
			SignoffService.instance().testExternalUserEmailSent(signOffSelected);
			signOffSelected = null;
			addInfoMessage("Test Email Sent");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepSignOff(){
		try {
			if (getSessionUI().getActiveUser().getId().equals(signOffSelected.getUser().getId())) {
				runClientSideExecute("PF('signOffExtneralDlg').show()");
			} else {
				addErrorMessage("You do not have permission to sign off");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void submitForSignoff(){
		try {
			workplaceMonitoringSiteVisitService.sendWorkplaceMonitoringSiteVisitForSignOff(getSessionUI().getActiveUser(), workplaceMonitoringSiteVisit, getSessionUI().getTask());
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void signOff(){
		try {
			if (signOffSelected.getAccept()) {
				SignoffService.instance().update(signOffSelected);
				addInfoMessage("Sign Off Complete");
				runClientSideExecute("PF('signOffExtneralDlg').hide()");
				populateSignOffList();
			} else {
				addErrorMessage("Please Accept Acknowledgement Before Signing Off");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void completeSignOffInternalUser(){
		try {
			workplaceMonitoringSiteVisitService.signOffWorkplaceMonitoring(workplaceMonitoringSiteVisit, signoffList, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepSignOffCloCrm(){
		try {
			accept = false;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void completeCloSignOff(){
		try {
			if (accept == null || !accept) {
				throw new Exception("Sign Off Before Proceeding");
			}
			workplaceMonitoringSiteVisitService.completeCloSignOff(workplaceMonitoringSiteVisit, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepRejection(){
		try {
			selectedRejectReasonsList = new ArrayList<>();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void rejectBackToClo() {
		try {
			if (selectedRejectReasonsList == null || selectedRejectReasonsList.isEmpty()) {
				throw new Exception("Provide atleast one reason before proceeding");
			}
			workplaceMonitoringSiteVisitService.rejectToClo(workplaceMonitoringSiteVisit, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReasonsList);
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void completeCrmSignOff(){
		try {
			if (accept == null || !accept) {
				throw new Exception("Sign Off Before Proceeding");
			}
			workplaceMonitoringSiteVisitService.finalApproveWorkplaceMonitoring(workplaceMonitoringSiteVisit, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void storeFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setUsers(getSessionUI().getActiveUser());
			if (doc.getId() != null) {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			} else {
				doc.setTargetKey(workplaceMonitoringSiteVisit.getId());
				doc.setTargetClass(workplaceMonitoringSiteVisit.getClass().getName());
				if (doc.getId() == null)
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepGenericUpload(){
		try {
			if (genericUploadObject == null) {
				throw new Exception("Unable to find link for upload, contact support!");
			} else {
				doc = new Doc();
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void storeFileGeneric(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setUsers(getSessionUI().getActiveUser());
			if (doc.getId() != null) {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
				if (updateIndicatoryScreen != null) {
					updateTablesFromGenericUpload(updateIndicatoryScreen);
					updateIndicatoryScreen = null;
				}
//				
			} else {
				String targetClass = "";
				Long targetKey = null;
				int updateIndicator = 0;
				
				if (genericUploadObject instanceof WorkplaceMonitoringLearnerInduction) {
					WorkplaceMonitoringLearnerInduction learnerInduction = (WorkplaceMonitoringLearnerInduction) genericUploadObject;
					
					targetClass = WorkplaceMonitoringLearnerInduction.class.getName();
					if (learnerInduction.getId() != null) {
						targetKey = learnerInduction.getId();
					}
					updateIndicator = 1;
					
				} else if (genericUploadObject instanceof WorkplaceMonitoringActionPlan){
					WorkplaceMonitoringActionPlan actionPlan = (WorkplaceMonitoringActionPlan) genericUploadObject;
					
					targetClass = WorkplaceMonitoringActionPlan.class.getName();
					if (actionPlan.getId() != null) {
						targetKey = actionPlan.getId();
					}
					updateIndicator = 2;
					
				} else if (genericUploadObject instanceof WorkplaceMonitoringDgMonitoring){
					WorkplaceMonitoringDgMonitoring dgMonitoring = (WorkplaceMonitoringDgMonitoring) genericUploadObject;
					
					targetClass = WorkplaceMonitoringDgMonitoring.class.getName();
					if (dgMonitoring.getId() != null) {
						targetKey = dgMonitoring.getId();
					}
					updateIndicator = 3;
					
				} else if (genericUploadObject instanceof WorkplaceMonitoringLearnerSurvey){
					WorkplaceMonitoringLearnerSurvey learnerInduction = (WorkplaceMonitoringLearnerSurvey) genericUploadObject;
					
					targetClass = WorkplaceMonitoringLearnerSurvey.class.getName();
					if (learnerInduction.getId() != null) {
						targetKey = learnerInduction.getId();
					}
					updateIndicator = 4;
					
				} else if (genericUploadObject instanceof WorkplaceMonitoringMitigationPlan){
					WorkplaceMonitoringMitigationPlan mitigationPlan = (WorkplaceMonitoringMitigationPlan) genericUploadObject;
					
					targetClass = WorkplaceMonitoringMitigationPlan.class.getName();
					if (mitigationPlan.getId() != null) {
						targetKey = mitigationPlan.getId();
					}
					updateIndicator = 5;
					
				} else if (genericUploadObject instanceof WorkplaceMonitoringLearnerSurveyAnswers){
					WorkplaceMonitoringLearnerSurveyAnswers surveyAnswers = (WorkplaceMonitoringLearnerSurveyAnswers) genericUploadObject;
					
					targetClass = WorkplaceMonitoringLearnerSurveyAnswers.class.getName();
					if (surveyAnswers.getId() != null) {
						targetKey = surveyAnswers.getId();
					}
					updateIndicator = 7;
					
				} else if (genericUploadObject instanceof WorkplaceMonitoringDiscretionaryGrantComplianceSurvey){
					WorkplaceMonitoringDiscretionaryGrantComplianceSurvey complainceSurvey = (WorkplaceMonitoringDiscretionaryGrantComplianceSurvey) genericUploadObject;
					
					targetClass = WorkplaceMonitoringDiscretionaryGrantComplianceSurvey.class.getName();
					if (complainceSurvey.getId() != null) {
						targetKey = complainceSurvey.getId();
					}
					updateIndicator = 6;
				} else if (genericUploadObject instanceof WorkplaceMonitoringLearnerPayments){
					WorkplaceMonitoringLearnerPayments learnerPayment = (WorkplaceMonitoringLearnerPayments) genericUploadObject;
					
					targetClass = WorkplaceMonitoringLearnerPayments.class.getName();
					if (learnerPayment.getId() != null) {
						targetKey = learnerPayment.getId();
					}
					updateIndicator = 8;
				}
				
				if (targetClass != null && !targetClass.isEmpty() && targetKey != null) {
					doc.setTargetKey(targetKey);
					doc.setTargetClass(targetClass);
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
					addInfoMessage("Action Complete");
					genericUploadObject = null;
					updateTablesFromGenericUpload(updateIndicator);
				} else {
					addErrorMessage("Unable to locate link for upload, contact support!");
				}
				
			}
			super.runClientSideExecute("PF('dlgUploadGeneric').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void updateTablesFromGenericUpload(Integer updateIndicator){
		try {
			switch (updateIndicator) {
			case 1:
				workplaceMonitoringLearnerInductionDataModelInfo();
				break;
			case 2:
				workplaceMonitoringActionPlanDataModelInfo();
				break;
			case 3:
				workplaceMonitoringDgMonitoringDataModelInfo();					
				break;
			case 4:
				workplaceMonitoringLearnerSurveyDataModelInfo();
				break;
			case 5:
				workplaceMonitoringMitigationPlanDataModelInfo();
				break;
			case 6:
				workplaceMonitoringDiscretionaryGrantComplianceSurveyDataModelInfo();
				break;
			case 7:
				if(workplaceMonitoringLearnerSurveyView != null && workplaceMonitoringLearnerSurveyView.getId() != null)
				    workplaceMonitoringLearnerSurveyAnswersDataModelInfo();
				break;
			case 8:
				workplaceMonitoringLearnerPaymentsDataModelInfo();
				break;
			default:
				break;
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}
	
	public List<RejectReasons> getRejectReasonsForSelection() {
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(workflowProcess);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	/* Getters and setters */
	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Signoff> getSignoffList() {
		return signoffList;
	}

	public void setSignoffList(List<Signoff> signoffList) {
		this.signoffList = signoffList;
	}

	public WorkplaceMonitoringSiteVisit getWorkplaceMonitoringSiteVisit() {
		return workplaceMonitoringSiteVisit;
	}

	public void setWorkplaceMonitoringSiteVisit(WorkplaceMonitoringSiteVisit workplaceMonitoringSiteVisit) {
		this.workplaceMonitoringSiteVisit = workplaceMonitoringSiteVisit;
	}

	public Boolean getAccept() {
		return accept;
	}

	public void setAccept(Boolean accept) {
		this.accept = accept;
	}

	public Signoff getSignOffSelected() {
		return signOffSelected;
	}

	public void setSignOffSelected(Signoff signOffSelected) {
		this.signOffSelected = signOffSelected;
	}

	public List<Users> getUserSelectionList() {
		return userSelectionList;
	}

	public void setUserSelectionList(List<Users> userSelectionList) {
		this.userSelectionList = userSelectionList;
	}

	public String getCurrentSignOffUserInfo() {
		return currentSignOffUserInfo;
	}

	public void setCurrentSignOffUserInfo(String currentSignOffUserInfo) {
		this.currentSignOffUserInfo = currentSignOffUserInfo;
	}

	public Users getUserSelectionForMoaSignOff() {
		return userSelectionForMoaSignOff;
	}

	public void setUserSelectionForMoaSignOff(Users userSelectionForMoaSignOff) {
		this.userSelectionForMoaSignOff = userSelectionForMoaSignOff;
	}

	public List<RejectReasons> getRejectReasonsList() {
		return rejectReasonsList;
	}

	public void setRejectReasonsList(List<RejectReasons> rejectReasonsList) {
		this.rejectReasonsList = rejectReasonsList;
	}

	public List<RejectReasons> getSelectedRejectReasonsList() {
		return selectedRejectReasonsList;
	}

	public void setSelectedRejectReasonsList(List<RejectReasons> selectedRejectReasonsList) {
		this.selectedRejectReasonsList = selectedRejectReasonsList;
	}

	public ConfigDocProcessEnum getWorkflowProcess() {
		return workflowProcess;
	}

	public void setWorkflowProcess(ConfigDocProcessEnum workflowProcess) {
		this.workflowProcess = workflowProcess;
	}

	public LazyDataModel<WorkplaceMonitoringLearnerInduction> getWorkplaceMonitoringLearnerInductionDataModel() {
		return workplaceMonitoringLearnerInductionDataModel;
	}

	public void setWorkplaceMonitoringLearnerInductionDataModel(
			LazyDataModel<WorkplaceMonitoringLearnerInduction> workplaceMonitoringLearnerInductionDataModel) {
		this.workplaceMonitoringLearnerInductionDataModel = workplaceMonitoringLearnerInductionDataModel;
	}

	public WorkplaceMonitoringLearnerInduction getWorkplaceMonitoringLearnerInduction() {
		return workplaceMonitoringLearnerInduction;
	}

	public void setWorkplaceMonitoringLearnerInduction(
			WorkplaceMonitoringLearnerInduction workplaceMonitoringLearnerInduction) {
		this.workplaceMonitoringLearnerInduction = workplaceMonitoringLearnerInduction;
	}

	public LazyDataModel<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> getWorkplaceMonitoringDiscretionaryGrantComplianceSurveyDataModel() {
		return workplaceMonitoringDiscretionaryGrantComplianceSurveyDataModel;
	}

	public void setWorkplaceMonitoringDiscretionaryGrantComplianceSurveyDataModel(
			LazyDataModel<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> workplaceMonitoringDiscretionaryGrantComplianceSurveyDataModel) {
		this.workplaceMonitoringDiscretionaryGrantComplianceSurveyDataModel = workplaceMonitoringDiscretionaryGrantComplianceSurveyDataModel;
	}

	public WorkplaceMonitoringDiscretionaryGrantComplianceSurvey getWorkplaceMonitoringDiscretionaryGrantComplianceSurvey() {
		return workplaceMonitoringDiscretionaryGrantComplianceSurvey;
	}

	public void setWorkplaceMonitoringDiscretionaryGrantComplianceSurvey(
			WorkplaceMonitoringDiscretionaryGrantComplianceSurvey workplaceMonitoringDiscretionaryGrantComplianceSurvey) {
		this.workplaceMonitoringDiscretionaryGrantComplianceSurvey = workplaceMonitoringDiscretionaryGrantComplianceSurvey;
	}

	public WorkplaceMonitoringActionPlan getWorkplaceMonitoringActionPlan() {
		return workplaceMonitoringActionPlan;
	}

	public void setWorkplaceMonitoringActionPlan(WorkplaceMonitoringActionPlan workplaceMonitoringActionPlan) {
		this.workplaceMonitoringActionPlan = workplaceMonitoringActionPlan;
	}

	public LazyDataModel<WorkplaceMonitoringActionPlan> getWorkplaceMonitoringActionPlanDataModel() {
		return workplaceMonitoringActionPlanDataModel;
	}

	public void setWorkplaceMonitoringActionPlanDataModel(
			LazyDataModel<WorkplaceMonitoringActionPlan> workplaceMonitoringActionPlanDataModel) {
		this.workplaceMonitoringActionPlanDataModel = workplaceMonitoringActionPlanDataModel;
	}

	public WorkplaceMonitoringLearnerSurvey getWorkplaceMonitoringLearnerSurvey() {
		return workplaceMonitoringLearnerSurvey;
	}

	public void setWorkplaceMonitoringLearnerSurvey(WorkplaceMonitoringLearnerSurvey workplaceMonitoringLearnerSurvey) {
		this.workplaceMonitoringLearnerSurvey = workplaceMonitoringLearnerSurvey;
	}

	public WorkplaceMonitoringLearnerSurveyAnswers getWorkplaceMonitoringLearnerSurveyAnswers() {
		return workplaceMonitoringLearnerSurveyAnswers;
	}

	public void setWorkplaceMonitoringLearnerSurveyAnswers(
			WorkplaceMonitoringLearnerSurveyAnswers workplaceMonitoringLearnerSurveyAnswers) {
		this.workplaceMonitoringLearnerSurveyAnswers = workplaceMonitoringLearnerSurveyAnswers;
	}

	public List<WorkplaceMonitoringLearnerSurveyAnswers> getWorkplaceMonitoringLearnerSurveyAnswersList() {
		return workplaceMonitoringLearnerSurveyAnswersList;
	}

	public void setWorkplaceMonitoringLearnerSurveyAnswersList(
			List<WorkplaceMonitoringLearnerSurveyAnswers> workplaceMonitoringLearnerSurveyAnswersList) {
		this.workplaceMonitoringLearnerSurveyAnswersList = workplaceMonitoringLearnerSurveyAnswersList;
	}

	public LazyDataModel<WorkplaceMonitoringLearnerSurveyAnswers> getWorkplaceMonitoringLearnerSurveyAnswersDataModel() {
		return workplaceMonitoringLearnerSurveyAnswersDataModel;
	}

	public void setWorkplaceMonitoringLearnerSurveyAnswersDataModel(
			LazyDataModel<WorkplaceMonitoringLearnerSurveyAnswers> workplaceMonitoringLearnerSurveyAnswersDataModel) {
		this.workplaceMonitoringLearnerSurveyAnswersDataModel = workplaceMonitoringLearnerSurveyAnswersDataModel;
	}

	public LazyDataModel<WorkplaceMonitoringLearnerSurvey> getWorkplaceMonitoringLearnerSurveyDataModel() {
		return workplaceMonitoringLearnerSurveyDataModel;
	}

	public void setWorkplaceMonitoringLearnerSurveyDataModel(
			LazyDataModel<WorkplaceMonitoringLearnerSurvey> workplaceMonitoringLearnerSurveyDataModel) {
		this.workplaceMonitoringLearnerSurveyDataModel = workplaceMonitoringLearnerSurveyDataModel;
	}

	public Boolean getViewAnswers() {
		return viewAnswers;
	}

	public void setViewAnswers(Boolean viewAnswers) {
		this.viewAnswers = viewAnswers;
	}

	public Boolean getNewlearnerSurvey() {
		return newlearnerSurvey;
	}

	public void setNewlearnerSurvey(Boolean newlearnerSurvey) {
		this.newlearnerSurvey = newlearnerSurvey;
	}

	public WorkplaceMonitoringLearnerSurvey getWorkplaceMonitoringLearnerSurveyView() {
		return workplaceMonitoringLearnerSurveyView;
	}

	public void setWorkplaceMonitoringLearnerSurveyView(
			WorkplaceMonitoringLearnerSurvey workplaceMonitoringLearnerSurveyView) {
		this.workplaceMonitoringLearnerSurveyView = workplaceMonitoringLearnerSurveyView;
	}

	public LazyDataModel<CompanyLearners> getAllLearnersDataModel() {
		return allLearnersDataModel;
	}

	public void setAllLearnersDataModel(LazyDataModel<CompanyLearners> allLearnersDataModel) {
		this.allLearnersDataModel = allLearnersDataModel;
	}

	public CompanyLearners getCompanyLearners() {
		return companyLearners;
	}

	public void setCompanyLearners(CompanyLearners companyLearners) {
		this.companyLearners = companyLearners;
	}

	public LazyDataModel<WorkplaceMonitoringDgMonitoring> getWorkplaceMonitoringDgMonitoringDataModel() {
		return workplaceMonitoringDgMonitoringDataModel;
	}

	public void setWorkplaceMonitoringDgMonitoringDataModel(
			LazyDataModel<WorkplaceMonitoringDgMonitoring> workplaceMonitoringDgMonitoringDataModel) {
		this.workplaceMonitoringDgMonitoringDataModel = workplaceMonitoringDgMonitoringDataModel;
	}

	public WorkplaceMonitoringDgMonitoring getWorkplaceMonitoringDgMonitoring() {
		return workplaceMonitoringDgMonitoring;
	}

	public void setWorkplaceMonitoringDgMonitoring(WorkplaceMonitoringDgMonitoring workplaceMonitoringDgMonitoring) {
		this.workplaceMonitoringDgMonitoring = workplaceMonitoringDgMonitoring;
	}

	public LazyDataModel<WorkplaceMonitoringMitigationPlan> getWorkplaceMonitoringMitigationPlanDataModel() {
		return workplaceMonitoringMitigationPlanDataModel;
	}

	public void setWorkplaceMonitoringMitigationPlanDataModel(
			LazyDataModel<WorkplaceMonitoringMitigationPlan> workplaceMonitoringMitigationPlanDataModel) {
		this.workplaceMonitoringMitigationPlanDataModel = workplaceMonitoringMitigationPlanDataModel;
	}

	public WorkplaceMonitoringMitigationPlan getWorkplaceMonitoringMitigationPlan() {
		return workplaceMonitoringMitigationPlan;
	}

	public void setWorkplaceMonitoringMitigationPlan(WorkplaceMonitoringMitigationPlan workplaceMonitoringMitigationPlan) {
		this.workplaceMonitoringMitigationPlan = workplaceMonitoringMitigationPlan;
	}

	public IDataEntity getGenericUploadObject() {
		return genericUploadObject;
	}

	public void setGenericUploadObject(IDataEntity genericUploadObject) {
		this.genericUploadObject = genericUploadObject;
	}

	public Integer getUpdateIndicatoryScreen() {
		return updateIndicatoryScreen;
	}

	public void setUpdateIndicatoryScreen(Integer updateIndicatoryScreen) {
		this.updateIndicatoryScreen = updateIndicatoryScreen;
	}

	public WorkplaceMonitoringLearnerPayments getWorkplaceMonitoringLearnerPayments() {
		return workplaceMonitoringLearnerPayments;
	}

	public void setWorkplaceMonitoringLearnerPayments(
			WorkplaceMonitoringLearnerPayments workplaceMonitoringLearnerPayments) {
		this.workplaceMonitoringLearnerPayments = workplaceMonitoringLearnerPayments;
	}

	public LazyDataModel<WorkplaceMonitoringLearnerPayments> getWorkplaceMonitoringLearnerPaymentsDataModel() {
		return workplaceMonitoringLearnerPaymentsDataModel;
	}

	public void setWorkplaceMonitoringLearnerPaymentsDataModel(
			LazyDataModel<WorkplaceMonitoringLearnerPayments> workplaceMonitoringLearnerPaymentsDataModel) {
		this.workplaceMonitoringLearnerPaymentsDataModel = workplaceMonitoringLearnerPaymentsDataModel;
	}

}
