package haj.com.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;

import haj.com.bean.ArplReportBean;
import haj.com.entity.Blank;
import haj.com.entity.CompanyLearnersTradeTest;
import haj.com.entity.CompanyTradeTestEmployer;
import haj.com.entity.DetailsOfExperienceArpl;
import haj.com.entity.DetailsOfTrainingArpl;
import haj.com.entity.NambDecisionHistory;
import haj.com.entity.Signoff;
import haj.com.entity.Tasks;
import haj.com.entity.TradeTestTaskResult;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CollectionEnum;
import haj.com.entity.enums.TradeTestTypeEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyLearnersTradeTestService;
import haj.com.service.CompanyTradeTestEmployerService;
import haj.com.service.DetailsOfExperienceArplService;
import haj.com.service.DetailsOfTrainingArplService;
import haj.com.service.NambDecisionHistoryService;
import haj.com.service.SignoffService;
import haj.com.service.TasksService;
import haj.com.service.TradeTestTaskResultService;

@ManagedBean(name = "arplCollectionReportingUI")
@ViewScoped
public class ArplCollectionReportingUI extends AbstractUI {
	
	/* Entity */
	private CompanyLearnersTradeTest viewCompanyLearnersTradeTest = null;
	private CompanyTradeTestEmployer companyTradeTestEmployer = null;

	/* Service Levels */
	private CompanyLearnersTradeTestService companyLearnersTradeTestService = new CompanyLearnersTradeTestService();
	private CompanyTradeTestEmployerService companyTradeTestEmployerService = new CompanyTradeTestEmployerService();
	private SignoffService signoffService = new SignoffService();
	private NambDecisionHistoryService nambDecisionHistoryService = new NambDecisionHistoryService();
	private DetailsOfExperienceArplService detailsOfExperienceArplService = new DetailsOfExperienceArplService();
	private DetailsOfTrainingArplService detailsOfTrainingArplService = new DetailsOfTrainingArplService();
	private TradeTestTaskResultService tradeTestTaskResultService = new TradeTestTaskResultService();
	private TasksService tasksService = new TasksService();

	/* Pie Model */
	private PieChartModel pieModel;

	/* lazy load */
	private LazyDataModel<CompanyLearnersTradeTest> companyLearnersTradeTestDataModel;
	private LazyDataModel<DetailsOfExperienceArpl> dataModelDetailsOfExperienceArpl;
	private LazyDataModel<DetailsOfTrainingArpl> dataModelDetailsOfTrainingArpl;
	private LazyDataModel<TradeTestTaskResult> dataModelTradeTestTaskResult;
	private LazyDataModel<NambDecisionHistory> dataModelNambDecisionHistory;
	private LazyDataModel<Tasks> dataModelTasks;

	/* array lists */
	private List<CollectionEnum> collectionEnumList;
	private List<ArplReportBean> arplReportBeanList;
	private List<Signoff> signOffLists = new ArrayList<>();
	
	/* Enums */
	private CollectionEnum selectedCollectionEnum;

	// Vars
	private Boolean displayInfo = false;
	private Boolean viewTradeTest = false;
	private Date now;

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
	 * Initialize method to get all Blank and prepare a for a create of a new
	 * Blank.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see Blank
	 */
	private void runInit() throws Exception {
		populateInformation();
		createPieModel();
	}

	private void populateInformation() throws Exception {
		// populate report generation date
		now = getNow();
		// populate list
		collectionEnumList = CollectionEnum.getAllValues();
	}

	private void createPieModel() {
		pieModel = new PieChartModel();
		ChartData data = new ChartData();

		PieChartDataSet dataSet = new PieChartDataSet();
		List<Number> values = new ArrayList<>();
		List<String> bgColors = new ArrayList<>();
		List<String> labels = new ArrayList<>();
		arplReportBeanList = new ArrayList<>();
		for (CollectionEnum collectionEnum : collectionEnumList) {
			
			int amount = 0;
			try {
				amount = companyLearnersTradeTestService.countArplTradeTestReportingByStatusAndCollectionType(TradeTestTypeEnum.ARPL, ApprovalEnum.QualificationObtained, collectionEnum);
			} catch (Exception e) {
				amount = 0;
			}
			
			values.add(amount);
			bgColors.add(collectionEnum.getRgb());
			labels.add(collectionEnum.getRegistrationName());
			
			ArplReportBean bean = new ArplReportBean();
			bean.setCollectionEnum(collectionEnum);
			bean.setCount(amount);
			bean.setDateGenerated(getNow());
			arplReportBeanList.add(bean);
		}
		dataSet.setData(values);
		dataSet.setBackgroundColor(bgColors);
		data.addChartDataSet(dataSet);
		data.setLabels(labels);

		pieModel.setData(data);
	}
	
	public void viewResults(){
		try {
			if (selectedCollectionEnum == null) {
				throw new Exception("Unable to locate report selected, contact support");
			}
			companyLearnersTradeTestDataModelInfo();
			displayInfo = true;
			viewTradeTest = false;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void closeReport(){
		try {
			displayInfo = false;
			viewTradeTest = false;
			selectedCollectionEnum = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void companyLearnersTradeTestDataModelInfo() {
		companyLearnersTradeTestDataModel = new LazyDataModel<CompanyLearnersTradeTest>() {
			private static final long serialVersionUID = 1L;
			private List<CompanyLearnersTradeTest> retorno = new ArrayList<CompanyLearnersTradeTest>();
			@Override
			public List<CompanyLearnersTradeTest> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = companyLearnersTradeTestService.allArplTradeTestReportingByStatusAndCollectionType(first, pageSize, sortField, sortOrder, filters, TradeTestTypeEnum.ARPL, ApprovalEnum.QualificationObtained, selectedCollectionEnum);
					companyLearnersTradeTestDataModel.setRowCount(companyLearnersTradeTestService.countAllArplTradeTestReportingByStatusAndCollectionType(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(CompanyLearnersTradeTest obj) {
				return obj.getId();
			}
			@Override
			public CompanyLearnersTradeTest getRowData(String rowKey) {
				for (CompanyLearnersTradeTest obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	/* ARPL VIEW START */
	public void prepViewArpl(){
		try {
			viewCompanyLearnersTradeTest =  companyLearnersTradeTestService.populateDocsByTargetClassAndKey(companyLearnersTradeTestService.findByKey(viewCompanyLearnersTradeTest.getId()));
			dataModelDetailsOfExperienceArplInfo();
			dataModelDetailsOfTrainingArplInfo();
			if (viewCompanyLearnersTradeTest.getEmployer() != null && viewCompanyLearnersTradeTest.getEmployer().getId() != null) {
				companyTradeTestEmployer = companyTradeTestEmployerService.findByKey(viewCompanyLearnersTradeTest.getEmployer().getId());
			}
			populateSignOffs();
			dataModelTradeTestTaskResultInfo();
			dataModelNambDecisionHistoryInfo();
			dataModelTasksInfo();
			viewTradeTest = true;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void populateSignOffs() throws Exception{
		if (viewCompanyLearnersTradeTest != null && viewCompanyLearnersTradeTest.getId() != null) {
			signOffLists = signoffService.findByTargetKeyAndClassAndLastest(viewCompanyLearnersTradeTest.getId(), viewCompanyLearnersTradeTest.getClass().getName(), true);
		}
	}
	
	public void dataModelDetailsOfExperienceArplInfo() {
		dataModelDetailsOfExperienceArpl = new LazyDataModel<DetailsOfExperienceArpl>() {
			private static final long serialVersionUID = 1L;
			private List<DetailsOfExperienceArpl> retorno = new ArrayList<DetailsOfExperienceArpl>();
			@Override
			public List<DetailsOfExperienceArpl> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = detailsOfExperienceArplService.allDetailsOfExperienceArplByTradeTestId(first, pageSize, sortField, sortOrder, filters, viewCompanyLearnersTradeTest);
					dataModelDetailsOfExperienceArpl.setRowCount(detailsOfExperienceArplService.countAllDetailsOfExperienceArplByTradeTestId(filters));
				} catch (Exception e) {
					logger.fatal(e.getMessage(), e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(DetailsOfExperienceArpl obj) {
				return obj.getId();
			}
			@Override
			public DetailsOfExperienceArpl getRowData(String rowKey) {
				for (DetailsOfExperienceArpl obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void dataModelDetailsOfTrainingArplInfo() {
		dataModelDetailsOfTrainingArpl = new LazyDataModel<DetailsOfTrainingArpl>() {
			private static final long serialVersionUID = 1L;
			private List<DetailsOfTrainingArpl> retorno = new ArrayList<DetailsOfTrainingArpl>();
			@Override
			public List<DetailsOfTrainingArpl> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = detailsOfTrainingArplService.allDetailsOfTrainingArplByTradeTestId(first, pageSize, sortField, sortOrder, filters, viewCompanyLearnersTradeTest);
					dataModelDetailsOfTrainingArpl.setRowCount(detailsOfTrainingArplService.countAllDetailsOfTrainingArplByTradeTestId(filters));
				} catch (Exception e) {
					logger.fatal(e.getMessage(), e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(DetailsOfTrainingArpl obj) {
				return obj.getId();
			}

			@Override
			public DetailsOfTrainingArpl getRowData(String rowKey) {
				for (DetailsOfTrainingArpl obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void dataModelTradeTestTaskResultInfo() {
		dataModelTradeTestTaskResult = new LazyDataModel<TradeTestTaskResult>() {
			private static final long serialVersionUID = 1L;
			private List<TradeTestTaskResult> retorno = new ArrayList<TradeTestTaskResult>();

			@Override
			public List<TradeTestTaskResult> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = tradeTestTaskResultService.allTradeTestTaskResultByTradeTestId(first, pageSize, sortField, sortOrder, filters, viewCompanyLearnersTradeTest);
					dataModelTradeTestTaskResult.setRowCount(tradeTestTaskResultService.countAllTradeTestTaskResultByTradeTestId(filters));
				} catch (Exception e) {
					e.printStackTrace();
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(TradeTestTaskResult obj) {
				return obj.getId();
			}

			@Override
			public TradeTestTaskResult getRowData(String rowKey) {
				for (TradeTestTaskResult obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void dataModelNambDecisionHistoryInfo() {
		dataModelNambDecisionHistory = new LazyDataModel<NambDecisionHistory>() {
			private static final long serialVersionUID = 1L;
			private List<NambDecisionHistory> retorno = new ArrayList<NambDecisionHistory>();
			@Override
			public List<NambDecisionHistory> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = nambDecisionHistoryService.allNambDecisionHistoryByTargetClassAndKey(first, pageSize, sortField, sortOrder, filters, viewCompanyLearnersTradeTest.getId(), viewCompanyLearnersTradeTest.getClass().getName());
					dataModelNambDecisionHistory.setRowCount(nambDecisionHistoryService.countAllNambDecisionHistoryByTargetClassAndKey(filters));
				} catch (Exception e) {
					e.printStackTrace();
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(NambDecisionHistory obj) {
				return obj.getId();
			}

			@Override
			public NambDecisionHistory getRowData(String rowKey) {
				for (NambDecisionHistory obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void dataModelTasksInfo() {
		dataModelTasks = new LazyDataModel<Tasks>() {
			private static final long serialVersionUID = 1L;
			private List<Tasks> retorno = new ArrayList<Tasks>();
			@Override
			public List<Tasks> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (sortField == null || sortField.isEmpty()) {
						sortField = "createDate";
						sortOrder = SortOrder.DESCENDING;
					}
					retorno = tasksService.allTasksByTargetClassAndKey(first, pageSize, sortField, sortOrder, filters, viewCompanyLearnersTradeTest.getId(), viewCompanyLearnersTradeTest.getClass().getName());
					dataModelTasks.setRowCount(tasksService.countAllTasksByTargetClassAndKey(filters));
				} catch (Exception e) {
					e.printStackTrace();
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
	
	public void closeViewArpl(){
		try {
			viewTradeTest = false;
			viewCompanyLearnersTradeTest = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	/* ARPL VIEW END */

	/* Getters and setters */
	public LazyDataModel<CompanyLearnersTradeTest> getCompanyLearnersTradeTestDataModel() {
		return companyLearnersTradeTestDataModel;
	}

	public void setCompanyLearnersTradeTestDataModel(
			LazyDataModel<CompanyLearnersTradeTest> companyLearnersTradeTestDataModel) {
		this.companyLearnersTradeTestDataModel = companyLearnersTradeTestDataModel;
	}

	public List<CollectionEnum> getCollectionEnumList() {
		return collectionEnumList;
	}

	public void setCollectionEnumList(List<CollectionEnum> collectionEnumList) {
		this.collectionEnumList = collectionEnumList;
	}

	public Boolean getDisplayInfo() {
		return displayInfo;
	}

	public void setDisplayInfo(Boolean displayInfo) {
		this.displayInfo = displayInfo;
	}

	public List<ArplReportBean> getArplReportBeanList() {
		return arplReportBeanList;
	}

	public void setArplReportBeanList(List<ArplReportBean> arplReportBeanList) {
		this.arplReportBeanList = arplReportBeanList;
	}

	public PieChartModel getPieModel() {
		return pieModel;
	}

	public void setPieModel(PieChartModel pieModel) {
		this.pieModel = pieModel;
	}

	public CollectionEnum getSelectedCollectionEnum() {
		return selectedCollectionEnum;
	}

	public void setSelectedCollectionEnum(CollectionEnum selectedCollectionEnum) {
		this.selectedCollectionEnum = selectedCollectionEnum;
	}

	public Boolean getViewTradeTest() {
		return viewTradeTest;
	}

	public void setViewTradeTest(Boolean viewTradeTest) {
		this.viewTradeTest = viewTradeTest;
	}

	public CompanyLearnersTradeTest getViewCompanyLearnersTradeTest() {
		return viewCompanyLearnersTradeTest;
	}

	public void setViewCompanyLearnersTradeTest(CompanyLearnersTradeTest viewCompanyLearnersTradeTest) {
		this.viewCompanyLearnersTradeTest = viewCompanyLearnersTradeTest;
	}

	public CompanyTradeTestEmployer getCompanyTradeTestEmployer() {
		return companyTradeTestEmployer;
	}

	public void setCompanyTradeTestEmployer(CompanyTradeTestEmployer companyTradeTestEmployer) {
		this.companyTradeTestEmployer = companyTradeTestEmployer;
	}

	public LazyDataModel<DetailsOfExperienceArpl> getDataModelDetailsOfExperienceArpl() {
		return dataModelDetailsOfExperienceArpl;
	}

	public void setDataModelDetailsOfExperienceArpl(
			LazyDataModel<DetailsOfExperienceArpl> dataModelDetailsOfExperienceArpl) {
		this.dataModelDetailsOfExperienceArpl = dataModelDetailsOfExperienceArpl;
	}

	public LazyDataModel<DetailsOfTrainingArpl> getDataModelDetailsOfTrainingArpl() {
		return dataModelDetailsOfTrainingArpl;
	}

	public void setDataModelDetailsOfTrainingArpl(LazyDataModel<DetailsOfTrainingArpl> dataModelDetailsOfTrainingArpl) {
		this.dataModelDetailsOfTrainingArpl = dataModelDetailsOfTrainingArpl;
	}

	public LazyDataModel<TradeTestTaskResult> getDataModelTradeTestTaskResult() {
		return dataModelTradeTestTaskResult;
	}

	public void setDataModelTradeTestTaskResult(LazyDataModel<TradeTestTaskResult> dataModelTradeTestTaskResult) {
		this.dataModelTradeTestTaskResult = dataModelTradeTestTaskResult;
	}

	public LazyDataModel<NambDecisionHistory> getDataModelNambDecisionHistory() {
		return dataModelNambDecisionHistory;
	}

	public void setDataModelNambDecisionHistory(LazyDataModel<NambDecisionHistory> dataModelNambDecisionHistory) {
		this.dataModelNambDecisionHistory = dataModelNambDecisionHistory;
	}

	public LazyDataModel<Tasks> getDataModelTasks() {
		return dataModelTasks;
	}

	public void setDataModelTasks(LazyDataModel<Tasks> dataModelTasks) {
		this.dataModelTasks = dataModelTasks;
	}

	public List<Signoff> getSignOffLists() {
		return signOffLists;
	}

	public void setSignOffLists(List<Signoff> signOffLists) {
		this.signOffLists = signOffLists;
	}

}