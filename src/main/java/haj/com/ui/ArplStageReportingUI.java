package haj.com.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.hbar.HorizontalBarChartDataSet;
import org.primefaces.model.charts.hbar.HorizontalBarChartModel;
import org.primefaces.model.charts.optionconfig.title.Title;

import haj.com.bean.ArplReportBean;
import haj.com.constants.HAJConstants;
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
import haj.com.entity.enums.AprlProgressEnum;
import haj.com.entity.enums.AprlProgressReportingEnum;
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

@ManagedBean(name = "arplStageReportingUI")
@ViewScoped
public class ArplStageReportingUI extends AbstractUI {
	
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
	
	/* Enums */
	private AprlProgressReportingEnum selectedReportingValue;

	/* Bar Models */
	private BarChartModel barModel;
	private HorizontalBarChartModel hbarModel;
	
	/* lazy load */
	private LazyDataModel<CompanyLearnersTradeTest> companyLearnersTradeTestDataModel;
	private LazyDataModel<DetailsOfExperienceArpl> dataModelDetailsOfExperienceArpl;
	private LazyDataModel<DetailsOfTrainingArpl> dataModelDetailsOfTrainingArpl;
	private LazyDataModel<TradeTestTaskResult> dataModelTradeTestTaskResult;
	private LazyDataModel<NambDecisionHistory> dataModelNambDecisionHistory;
	private LazyDataModel<Tasks> dataModelTasks;
	
	/* array lists */
	private List<ArplReportBean> arplReportBeanList = new ArrayList<>();
	private List<AprlProgressReportingEnum> aprlProgressReportingEnumList;
	private List<AprlProgressReportingEnum> aprlProgressReportingAllvaluesList;
	private List<Signoff> signOffLists = new ArrayList<>();
	
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
		populateHorzBar();
	}

	private void populateInformation() throws Exception{
		// populate report generation date
		now = getNow();
		// populate graph status
		aprlProgressReportingEnumList = AprlProgressReportingEnum.getGraphValues();
		// populate status selection
		aprlProgressReportingAllvaluesList = AprlProgressReportingEnum.getAllValues();
	}

	private void populateHorzBar() {
		hbarModel = new HorizontalBarChartModel();
        ChartData dataH = new ChartData();
         
        HorizontalBarChartDataSet hbarDataSet = new HorizontalBarChartDataSet();
        hbarDataSet.setLabel("Stages Amount");
        
        List<Number> values = new ArrayList<>();
		List<String> bgColor = new ArrayList<>();
		List<String> borderColor = new ArrayList<>();
		List<String> labels = new ArrayList<>();
		arplReportBeanList = new ArrayList<>();
		for (AprlProgressReportingEnum value : aprlProgressReportingEnumList) {
			
			// sets label
			labels.add(value.getDisplayNameReport());
			int amount = 0;
			try {
				switch (value) {
				case WithInitiatorOne:
					amount = companyLearnersTradeTestService.countArplTradeTestReportingByStatus(TradeTestTypeEnum.ARPL, AprlProgressEnum.WithInitiatorOne);
					break;
				case WithMersetaOne:
					amount = companyLearnersTradeTestService.countArplTradeTestReportingByStatus(TradeTestTypeEnum.ARPL, AprlProgressEnum.WithMersetaOne);
					break;
				case WithInitiatorTwo:
					amount = companyLearnersTradeTestService.countArplTradeTestReportingByStatus(TradeTestTypeEnum.ARPL, AprlProgressEnum.WithInitiatorTwo);
					break;
				case WithMersetaTwo:
					amount = companyLearnersTradeTestService.countArplTradeTestReportingByStatusOnHold(TradeTestTypeEnum.ARPL, AprlProgressEnum.WithMersetaTwo, false);
					break;
				case WithMersetaTwoHoldingArea:
					amount = companyLearnersTradeTestService.countArplTradeTestReportingByStatusOnHold(TradeTestTypeEnum.ARPL, AprlProgressEnum.WithMersetaTwo, true);
					break;
				case WithMersetaThree:
					amount = companyLearnersTradeTestService.countArplTradeTestReportingByStatus(TradeTestTypeEnum.ARPL, AprlProgressEnum.WithMersetaThree);
					break;
				case WithMersetaFour:
					// still open
					amount = companyLearnersTradeTestService.countArplTradeTestReportingByStatusAndApprovalStatus(TradeTestTypeEnum.ARPL, AprlProgressEnum.WithMersetaFour, ApprovalEnum.PendingApproval);
					break;
				case WithTestCenterOneProvideDate:
					// with test center not provided test date
					amount = companyLearnersTradeTestService.countCompanyLearnersTradeTestByTypeArplProgressTestDateProvidedReminder(TradeTestTypeEnum.ARPL, AprlProgressEnum.WithTestCenterOne, false, false);
					break;
				case WithTestCenterOneProvideResults:
					// with test center provided test date and not submitted
					amount = companyLearnersTradeTestService.countCompanyLearnersTradeTestByTypeArplProgressTestDateProvidedAwaitingResults(TradeTestTypeEnum.ARPL, AprlProgressEnum.WithTestCenterOne, false, true);
					break;
				case WithTestCenterOneAlterationAfterSubmission:
					// with test center provided test date and not submitted
					amount = companyLearnersTradeTestService.countArplWithTestCenterAfterSubmission(TradeTestTypeEnum.ARPL, AprlProgressEnum.WithTestCenterOne, true);
					break;
				case WithMersetaFive:
					// awaiting sign off and decision (needs work since learner can be none-competant)
					amount = companyLearnersTradeTestService.countArplTradeTestReportingByStatusAndApprovalStatus(TradeTestTypeEnum.ARPL, AprlProgressEnum.WithMersetaFive, ApprovalEnum.PendingApproval);
					break;
				case WithMersetaSix:
					// with ADMIN to provide
					amount = companyLearnersTradeTestService.countArplTradeTestReportingByStatusAndApprovalStatusAndNambDateNotProvided(TradeTestTypeEnum.ARPL, AprlProgressEnum.WithMersetaSix, ApprovalEnum.PendingApproval);
					break;
				case AwaitingNambDesc:
					// in NAMB holding area
					amount = companyLearnersTradeTestService.countArplTradeTestReportingByApprovalStatus(TradeTestTypeEnum.ARPL, ApprovalEnum.AwaitingNAMB);
					break;
				case ApprovedByNamb:
					// approved by NAMB awaiting certificate collection 
					amount = companyLearnersTradeTestService.countArplTradeTestReportingByStatusAndCertificateCollected(TradeTestTypeEnum.ARPL, ApprovalEnum.QualificationObtained, false);
					break;
				case CertificateDistribution:
					// Completed entire ARPL process
					amount = companyLearnersTradeTestService.countArplTradeTestReportingByStatusAndCertificateCollected(TradeTestTypeEnum.ARPL, ApprovalEnum.QualificationObtained, true);
					break;
				default:
					break;
				}
			} catch (Exception e) {
				amount = 0;
			}
			values.add(amount);
			
			// sets colour
			bgColor.add(value.getRgb());
			
			// sets boarder colour
			borderColor.add(value.getRgbBorder());
			
			ArplReportBean bean = new ArplReportBean();
			bean.setAprlProgressReportingEnum(value);
			bean.setCount(amount);
			bean.setDateGenerated(getNow());
			arplReportBeanList.add(bean);
		}
		// set all counts assigned
		hbarDataSet.setData(values);
		
		// sets background colour
		hbarDataSet.setBackgroundColor(bgColor);
		
		// sets boarder colour and width
		hbarDataSet.setBorderColor(borderColor);
		hbarDataSet.setBorderWidth(1);
		
		// sets data set
		dataH.addChartDataSet(hbarDataSet);
		
		// sets labels
		dataH.setLabels(labels);
		hbarModel.setData(dataH);
         
        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addXAxesData(linearAxes);
        options.setScales(cScales);
         
        Title title = new Title();
        title.setDisplay(true);
        title.setText("RPL Stages Report ( " + HAJConstants.sdfDDMMYYYY.format(now)+" )");
        options.setTitle(title);
         
        hbarModel.setOptions(options);
	}
	
	public void viewResults(){
		try {
			if (selectedReportingValue == null) {
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
			selectedReportingValue = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void companyLearnersTradeTestDataModelInfo() {
		companyLearnersTradeTestDataModel = new LazyDataModel<CompanyLearnersTradeTest>() {
			private static final long serialVersionUID = 1L;
			private List<CompanyLearnersTradeTest> retorno = new ArrayList<>();
			@Override
			public List<CompanyLearnersTradeTest> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					switch (selectedReportingValue) {
					case WithInitiatorOne:
						retorno = companyLearnersTradeTestService.allArplByStatus(first, pageSize, sortField, sortOrder, filters, TradeTestTypeEnum.ARPL, AprlProgressEnum.WithInitiatorOne);
						companyLearnersTradeTestDataModel.setRowCount(companyLearnersTradeTestService.countallArplByStatus(filters));
						break;
					case WithMersetaOne:
						retorno = companyLearnersTradeTestService.allArplByStatus(first, pageSize, sortField, sortOrder, filters, TradeTestTypeEnum.ARPL, AprlProgressEnum.WithMersetaOne);
						companyLearnersTradeTestDataModel.setRowCount(companyLearnersTradeTestService.countallArplByStatus(filters));
						break;
					case WithInitiatorTwo:
						retorno = companyLearnersTradeTestService.allArplByStatus(first, pageSize, sortField, sortOrder, filters, TradeTestTypeEnum.ARPL, AprlProgressEnum.WithInitiatorTwo);
						companyLearnersTradeTestDataModel.setRowCount(companyLearnersTradeTestService.countallArplByStatus(filters));
						break;
					case WithMersetaTwo:
						retorno = companyLearnersTradeTestService.allArplByStatusOnHold(first, pageSize, sortField, sortOrder, filters, TradeTestTypeEnum.ARPL, AprlProgressEnum.WithMersetaTwo, false);
						companyLearnersTradeTestDataModel.setRowCount(companyLearnersTradeTestService.countAllArplByStatusOnHold(filters));
						break;
					case WithMersetaTwoHoldingArea:
						retorno = companyLearnersTradeTestService.allArplByStatusOnHold(first, pageSize, sortField, sortOrder, filters, TradeTestTypeEnum.ARPL, AprlProgressEnum.WithMersetaTwo, true);
						companyLearnersTradeTestDataModel.setRowCount(companyLearnersTradeTestService.countAllArplByStatusOnHold(filters));
						break;
					case WithMersetaThree:
						retorno = companyLearnersTradeTestService.allArplByStatus(first, pageSize, sortField, sortOrder, filters, TradeTestTypeEnum.ARPL, AprlProgressEnum.WithMersetaThree);
						companyLearnersTradeTestDataModel.setRowCount(companyLearnersTradeTestService.countallArplByStatus(filters));
						break;
					case WithMersetaFour:
						retorno = companyLearnersTradeTestService.allArplByStatusAndApprovalStatus(first, pageSize, sortField, sortOrder, filters, TradeTestTypeEnum.ARPL, AprlProgressEnum.WithMersetaFour, ApprovalEnum.PendingApproval);
						companyLearnersTradeTestDataModel.setRowCount(companyLearnersTradeTestService.countAllArplByStatusAndApprovalStatus(filters));
						break;
					case WithTestCenterOneProvideDate:
						retorno = companyLearnersTradeTestService.allArplByTypeArplProgressTestDateProvidedReminder(first, pageSize, sortField, sortOrder, filters, TradeTestTypeEnum.ARPL, AprlProgressEnum.WithTestCenterOne, false, false);
						companyLearnersTradeTestDataModel.setRowCount(companyLearnersTradeTestService.countAllArplByTypeArplProgressTestDateProvidedReminder(filters));
						break;
					case WithTestCenterOneProvideResults:
						retorno = companyLearnersTradeTestService.allArplByTypeArplProgressTestDateProvidedAwaitingResults(first, pageSize, sortField, sortOrder, filters, TradeTestTypeEnum.ARPL, AprlProgressEnum.WithTestCenterOne, false, true);
						companyLearnersTradeTestDataModel.setRowCount(companyLearnersTradeTestService.countAllArplByTypeArplProgressTestDateProvidedAwaitingResults(filters));
						break;
					case WithTestCenterOneAlterationAfterSubmission:
						retorno = companyLearnersTradeTestService.allArplWithTestCenterAfterSubmission(first, pageSize, sortField, sortOrder, filters, TradeTestTypeEnum.ARPL, AprlProgressEnum.WithTestCenterOne, true);
						companyLearnersTradeTestDataModel.setRowCount(companyLearnersTradeTestService.countAllArplWithTestCenterAfterSubmission(filters));
						break;
					case WithMersetaFive:
						retorno = companyLearnersTradeTestService.allArplByStatusAndApprovalStatus(first, pageSize, sortField, sortOrder, filters, TradeTestTypeEnum.ARPL, AprlProgressEnum.WithMersetaFive, ApprovalEnum.PendingApproval);
						companyLearnersTradeTestDataModel.setRowCount(companyLearnersTradeTestService.countAllArplByStatusAndApprovalStatus(filters));
						break;
					case WithMersetaSix:
						retorno = companyLearnersTradeTestService.allArplByStatusAndApprovalStatusAndNambDateNotProvided(first, pageSize, sortField, sortOrder, filters, TradeTestTypeEnum.ARPL, AprlProgressEnum.WithMersetaSix, ApprovalEnum.PendingApproval);
						companyLearnersTradeTestDataModel.setRowCount(companyLearnersTradeTestService.countAllArplByStatusAndApprovalStatusAndNambDateNotProvided(filters));
						break;
					case AwaitingNambDesc:
						retorno = companyLearnersTradeTestService.allArplByApprovalStatus(first, pageSize, sortField, sortOrder, filters, TradeTestTypeEnum.ARPL, ApprovalEnum.AwaitingNAMB);
						companyLearnersTradeTestDataModel.setRowCount(companyLearnersTradeTestService.countAllArplByApprovalStatus(filters));
						break;
					case ApprovedByNamb:
						retorno = companyLearnersTradeTestService.allArplByStatusAndCertificateCollected(first, pageSize, sortField, sortOrder, filters, TradeTestTypeEnum.ARPL, ApprovalEnum.QualificationObtained, false);
						companyLearnersTradeTestDataModel.setRowCount(companyLearnersTradeTestService.countAllArplByStatusAndCertificateCollected(filters));
						break;
					case CertificateDistribution:
						retorno = companyLearnersTradeTestService.allArplByStatusAndCertificateCollected(first, pageSize, sortField, sortOrder, filters, TradeTestTypeEnum.ARPL, ApprovalEnum.QualificationObtained, true);
						companyLearnersTradeTestDataModel.setRowCount(companyLearnersTradeTestService.countAllArplByStatusAndCertificateCollected(filters));
						break;
					default:
						break;
					}
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
			viewCompanyLearnersTradeTest = companyLearnersTradeTestService.populateDocsByTargetClassAndKey(companyLearnersTradeTestService.findByKey(viewCompanyLearnersTradeTest.getId()));
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
	public BarChartModel getBarModel() {
		return barModel;
	}

	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}

	public List<AprlProgressReportingEnum> getAprlProgressReportingEnumList() {
		return aprlProgressReportingEnumList;
	}

	public void setAprlProgressReportingEnumList(List<AprlProgressReportingEnum> aprlProgressReportingEnumList) {
		this.aprlProgressReportingEnumList = aprlProgressReportingEnumList;
	}

	public List<ArplReportBean> getArplReportBeanList() {
		return arplReportBeanList;
	}

	public void setArplReportBeanList(List<ArplReportBean> arplReportBeanList) {
		this.arplReportBeanList = arplReportBeanList;
	}

	public HorizontalBarChartModel getHbarModel() {
		return hbarModel;
	}

	public void setHbarModel(HorizontalBarChartModel hbarModel) {
		this.hbarModel = hbarModel;
	}

	public List<AprlProgressReportingEnum> getAprlProgressReportingAllvaluesList() {
		return aprlProgressReportingAllvaluesList;
	}

	public void setAprlProgressReportingAllvaluesList(List<AprlProgressReportingEnum> aprlProgressReportingAllvaluesList) {
		this.aprlProgressReportingAllvaluesList = aprlProgressReportingAllvaluesList;
	}

	public AprlProgressReportingEnum getSelectedReportingValue() {
		return selectedReportingValue;
	}

	public void setSelectedReportingValue(AprlProgressReportingEnum selectedReportingValue) {
		this.selectedReportingValue = selectedReportingValue;
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

	public LazyDataModel<CompanyLearnersTradeTest> getCompanyLearnersTradeTestDataModel() {
		return companyLearnersTradeTestDataModel;
	}

	public void setCompanyLearnersTradeTestDataModel(
			LazyDataModel<CompanyLearnersTradeTest> companyLearnersTradeTestDataModel) {
		this.companyLearnersTradeTestDataModel = companyLearnersTradeTestDataModel;
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

	public Boolean getDisplayInfo() {
		return displayInfo;
	}

	public void setDisplayInfo(Boolean displayInfo) {
		this.displayInfo = displayInfo;
	}

	public Boolean getViewTradeTest() {
		return viewTradeTest;
	}

	public void setViewTradeTest(Boolean viewTradeTest) {
		this.viewTradeTest = viewTradeTest;
	}

}