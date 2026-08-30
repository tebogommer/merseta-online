package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.microsoft.schemas.dynamics.gp._2006._01.Vendor;

import haj.com.constants.HAJConstants;
import haj.com.entity.BankingDetails;
import haj.com.entity.ChangeReason;
import haj.com.entity.Company;
import haj.com.entity.CompanyCommunication;
import haj.com.entity.CompanyUsers;
import haj.com.entity.Doc;
import haj.com.entity.Employees;
import haj.com.entity.MailLog;
import haj.com.entity.SDFCompany;
import haj.com.entity.SDFCompanyHistory;
import haj.com.entity.Sites;
import haj.com.entity.SitesSme;
import haj.com.entity.Tasks;
import haj.com.entity.TrainingComittee;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.Users;
import haj.com.entity.WorkPlaceApproval;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CategorizationEnum;
import haj.com.entity.enums.CompanyRegOrSDLEnum;
import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.ProviderNameRegisTrationNumberEnum;
import haj.com.entity.lookup.LegacyProviderAccreditation;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.framework.AbstractUIInterface;
import haj.com.service.BankingDetailsService;
import haj.com.service.ChangeReasonService;
import haj.com.service.CompanyCommunicationService;
import haj.com.service.CompanyService;
import haj.com.service.CompanyUsersService;
import haj.com.service.DocService;
import haj.com.service.EmployeesService;
import haj.com.service.MailLogService;
import haj.com.service.RemittanceAdviceService;
import haj.com.service.SDFCompanyHistoryService;
import haj.com.service.SDFCompanyService;
import haj.com.service.SitesService;
import haj.com.service.SitesSmeService;
import haj.com.service.TasksService;
import haj.com.service.TrainingComitteeService;
import haj.com.service.TrainingProviderApplicationService;
import haj.com.service.UsersService;
import haj.com.service.WorkPlaceApprovalService;
import haj.com.service.lookup.LegacyProviderAccreditationService;
import haj.com.validators.exports.services.CompanyValidationService;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchCompanyUI.
 */
@ManagedBean(name = "searchCompanyUI")
@ViewScoped
public class SearchCompanyUI extends AbstractUI {

	/** The criteria. */
	private String criteria;

	/** The object. */
	private AbstractUIInterface object;

	/** The company service. */
	private CompanyService companyService = new CompanyService();

	/** The company reg or SDL enum. */
	private CompanyRegOrSDLEnum companyRegOrSDLEnum;
	private ProviderNameRegisTrationNumberEnum providerNameRegisTrationNumberEnum;

	/** The Constant company registration number format. */
	private String companyRegistrationNumberFormat = HAJConstants.companyRegistrationNumberFormat;

	/** The Constant company accreditation number format. */
	private String companyAccreditationNumberFormat = HAJConstants.companyAccreditationNumberFormat;

	/** The Constant company levy number format. */
	private String companyLevyNumberFormat = HAJConstants.companyLevyNumberFormat;

	/** The company */
	private Company company = new Company();

	/** The search NGO. */
	private boolean searchNGO;

	/** The search Accreditation Number. */
	private boolean searchAccNumber;

	/** The search Entity ID. */
	private boolean searchEntityId;

	private boolean searchProvider;

	private LazyDataModel<Company> dataModelBankingDetails;

	private boolean searchLevyPaying;
	private boolean searchExistingNonLevyPaying;
	private boolean searchNonLevyPaying;
	private boolean searchNonSeta;
	private boolean searchTrainingProvider;
	/** Company lazy data model */
	private LazyDataModel<Company> dataModel;

	/** The company list **/
	private List<Company> companyFilteredList = null;

	private MailLogService mailLogService = new MailLogService();

	private List<MailLog> mailLogs;

	/** The company users. */
	private List<CompanyUsers> companyUsers;
	/** The company users service. */
	private CompanyUsersService companyUsersService = new CompanyUsersService();
	private SDFCompanyHistoryService sdfCompanyHistoryService = new SDFCompanyHistoryService();

	/** The employees data model. */
	private LazyDataModel<Employees> empDataModel;
	/** The employeesService */
	private EmployeesService employeesService = new EmployeesService();
	private List<SDFCompanyHistory> sdfCompanyHistory;

	/** The SDF data model. */
	private LazyDataModel<SDFCompany> sdfDataModel;

	private SDFCompany selectedSdfCompany = null;
	private Users sdf = null;

	/** The SDFCompanyService */
	private SDFCompanyService sdfCompanyService = new SDFCompanyService();

	/** The Banking Details */
	private List<BankingDetails> bankingDetailsList;
	/** The banking details service. */
	private BankingDetailsService bankingDetailsService = new BankingDetailsService();
	/** The banking details. */
	private BankingDetails bankingDetails;
	private LazyDataModel<Tasks> dataModelBankingDetailsTasks;
	private TasksService tasksService;
	
	
	private Vendor vendor;

	/** The linked companies. */
	private List<Company> linkedCompanies;

	/** The training comittees. */
	private List<TrainingComittee> trainingComittees;
	/** The training comittee service. */
	private TrainingComitteeService trainingComitteeService = new TrainingComitteeService();

	/** The sites service. */
	private SitesService sitesService = new SitesService();
	/** The sites. */
	private List<Sites> sites;

	/** The Sites SME */
	private SitesSmeService sitesSmeService = null;
	private LazyDataModel<SitesSme> allSmeDataModel;

	/** The workplace approval data */
	private WorkPlaceApprovalService workPlaceApprovalService = null;
	private LazyDataModel<WorkPlaceApproval> allWorkPlaceApprovalDataModel;
	private WorkPlaceApproval workPlaceApproval;

	private UsersService usersService = new UsersService();
	private List<Doc> changeReasonDocs = null;
	private boolean displaySdfRegDocs = false;
	private boolean displayChangeDocs = false;
	private boolean sdfHistory = false;
	/** The search N Number. */
	private boolean searchNNumber;
	/** The search Non merSETA Company. */
	private boolean nonMersetaCompany;

	/** The TrainingProviderApplicationService */
	private TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();

	/** The Constant company levy number format. */
	private String companyNNumberFormat = HAJConstants.companyNNumberFormat;
	
	private LazyDataModel<MailLog> companyMailLogDataModel;
	
	/* Company Communications Section */
	private CompanyCommunicationService companyCommunicationService; 
	private LazyDataModel<CompanyCommunication> companyCommunicationDataModel;
	private CompanyCommunication companyCommunication = null;
	private Doc doc;
	

	/**
	 * Inits the.
	 */
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

	public void handleChange() {
		this.runClientSideExecute("checkMaskValue();");
	}

	/**
	 * Run init.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public void runInit() throws Exception {
		clearCriteria();
		companyInfo();
		companyBankingDetilasInfo();
		workPlaceApproval = new WorkPlaceApproval();
	}

	public void sdfCompanyHistortInfo() {
		try {
			sdfCompanyHistory = sdfCompanyHistoryService.allSDFCompanyHistoryWithresolve(this.company);
			sdfHistory = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void companyInfo() {
		dataModel = new LazyDataModel<Company>() {
			private static final long serialVersionUID = 1L;
			private List<Company> companyList = new ArrayList<>();

			@Override
			public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (sortField == null) {
						sortField = "companyStatus";
						sortOrder = SortOrder.DESCENDING;
					}
					
					companyList = companyService.allCompanySetaCompanies(Company.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(companyService.countSetaCompanies(Company.class, filters));
					
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return companyList;
			}

			@Override
			public Object getRowKey(Company object) {
				// TODO Auto-generated method stub
				return object.getId();
			}

			@Override
			public Company getRowData(String rowKey) {
				for (Company obj : companyList) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}

	public void prepareCompayInfo() {
		try {
			companyUsers = companyUsersService.findByCompanyNotSDF(company);
			employeesInfo();
			sdfInfo();
			sdfHistory = false;
			bankingDetailsInfo();
			linkedCompanies = companyService.findLinkedCompany(company);
			trainingComittees = trainingComitteeService.findByCompany(company);
			sites = sitesService.findByCompany(company);
			siteSmeDataInfo();
			workplaceApprovalDataInfo();
			companyService.resolveDocsForProcessAndUserType(company, ConfigDocProcessEnum.SDF, CompanyUserTypeEnum.Company);
			companyCommunicationDataModelInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void onTabChange(TabChangeEvent event) {
		try {
			switch (event.getTab().getId()) {
			case "companyDetailsTab":
				break;
			case "contactPersonsTab":
				break;
			case "employeesTab":
				break;
			case "sdfManagemntTab":
				break;
			case "bankDetailsTab":
				break;
			case "documents":
				break;
			case "linkedCompaniesTab":
				break;
			case "trainingCommitteeTab":
				break;
			case "sitesTab":
				break;
			case "siteSme":
				break;
			case "workplaceApprovalTab":
				break;
			case "companyCommunicationTab":
				companyCommunication = null;
				companyCommunicationDataModelInfo();
				break;
			default:
				break;
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Get all Employees for data table.
	 *
	 * @author TechFinium
	 * @see Employees
	 */
	public void employeesInfo() {

		empDataModel = new LazyDataModel<Employees>() {

			private static final long serialVersionUID = 1L;
			private List<Employees> retorno = new ArrayList<Employees>();

			@Override
			public List<Employees> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = employeesService.allEmployees(Employees.class, first, pageSize, sortField, sortOrder,
							filters, company);
					empDataModel.setRowCount(employeesService.count(Employees.class, filters, company));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Employees obj) {
				return obj.getId();
			}

			@Override
			public Employees getRowData(String rowKey) {
				for (Employees obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Get all SDFCompany for data table.
	 *
	 * @author TechFinium
	 * @see SDFCompany
	 */
	public void sdfInfo() {
		sdfDataModel = new LazyDataModel<SDFCompany>() {

			private static final long serialVersionUID = 1L;
			private List<SDFCompany> retorno = new ArrayList<SDFCompany>();

			@Override
			public List<SDFCompany> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					retorno = sdfCompanyService.allSDFForCompany(SDFCompany.class, first, pageSize, sortField,
							sortOrder, filters, company, getSessionUI().getActiveUser());
					sdfDataModel.setRowCount(sdfCompanyService
							.allSDFForCompanyCount(filters, company, getSessionUI().getActiveUser()).intValue());

				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SDFCompany obj) {
				return obj.getId();
			}

			@Override
			public SDFCompany getRowData(String rowKey) {
				for (SDFCompany obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	public void viewSdfDocumnets() {
		try {
			displaySdfRegDocs = false;
			displayChangeDocs = false;
			// locates sdf doc from registartion
			sdf = usersService.findByKey(selectedSdfCompany.getSdf().getId());
			// usersService.resolveDocs(sdf, ConfigDocProcessEnum.SDF,
			// CompanyUserTypeEnum.User);
			usersService.locateUserDocs(sdf);
			if (sdf.getDocs() != null && sdf.getDocs().size() != 0) {
				displaySdfRegDocs = true;
			}
			changeReasonDocs = new ArrayList<>();
			// if documents assigned to any change reasons
			List<ChangeReason> changeReasons = ChangeReasonService.instance()
					.findByTargetClassAndTargetKey(selectedSdfCompany.getClass().getName(), selectedSdfCompany.getId());
			if (changeReasons.size() != 0) {
				for (ChangeReason changeReason : changeReasons) {
					if (changeReason.getDocs() != null && changeReason.getDocs().size() != 0) {
						displayChangeDocs = true;
						changeReasonDocs.addAll(changeReason.getDocs());
					}
				}
			}
			// if no doucments will display message none found
			if ((sdf.getDocs() != null && sdf.getDocs().size() != 0) || (changeReasonDocs.size() != 0)) {
				// oncomplete="PF('mailLogDlg').show()"
				super.runClientSideExecute("PF('sdfdocsDlg').show()");
				super.runClientSideUpdate("sdfDocForm");
			} else {
				addErrorMessage("No Documents Found For User");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void bankingDetailsInfo() {
		try {
			bankingDetails = bankingDetailsService.findByCompanyLates(company);
			if (bankingDetails == null) {
				bankingDetails = new BankingDetails();
			}
			if (bankingDetails.getId() != null) {
				dataModelBankningDetailsTasksInfo();
			}

			bankingDetailsList = new ArrayList<>();
			if (bankingDetailsService.findByCompanyCount(company) > 1) {
				bankingDetailsList = bankingDetailsService.findByCompany(company);
				bankingDetailsList.remove(bankingDetails);
			} else if (bankingDetails.getId() == null && bankingDetailsService.findByCompanyCount(company) > 0) {
				bankingDetailsList = bankingDetailsService.findByCompany(company);
				// bankingDetailsList.remove(bankingDetails);
			}
			this.vendor = null;
			this.vendor = bankingDetailsService.getGPDetailsForLNumber(company.getLevyNumber(), bankingDetails.getBankAccNumber());
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void dataModelBankningDetailsTasksInfo() {
		if (tasksService == null) {
			tasksService = new TasksService();
		}
		dataModelBankingDetailsTasks = new LazyDataModel<Tasks>() {
			private static final long serialVersionUID = 1L;
			private List<Tasks> retorno = new ArrayList<Tasks>();
			@Override
			public List<Tasks> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = tasksService.allTasksByTargetClassAndKey(first, pageSize, sortField, sortOrder, filters, bankingDetails.getId(), bankingDetails.getClass().getName());
					dataModelBankingDetailsTasks.setRowCount(tasksService.countAllTasksByTargetClassAndKey(filters));
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

	public void siteSmeDataInfo() {
		if (sitesSmeService == null) {
			sitesSmeService = new SitesSmeService();
		}

		allSmeDataModel = new LazyDataModel<SitesSme>() {
			private static final long serialVersionUID = 1L;
			private List<SitesSme> retorno = new ArrayList<SitesSme>();

			@Override
			public List<SitesSme> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					retorno = sitesSmeService.allSitesSmeByCompany(first, pageSize, sortField, sortOrder, filters, company, null, null);
					allSmeDataModel.setRowCount(sitesSmeService.countAllSitesSmeByCompany(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SitesSme obj) {
				return obj.getId();
			}

			@Override
			public SitesSme getRowData(String rowKey) {
				for (SitesSme obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	private void workplaceApprovalDataInfo() {
		if (workPlaceApprovalService == null) {
			workPlaceApprovalService = new WorkPlaceApprovalService();
		}
		allWorkPlaceApprovalDataModel = new LazyDataModel<WorkPlaceApproval>() {
			private static final long serialVersionUID = 1L;
			private List<WorkPlaceApproval> retorno = new ArrayList<WorkPlaceApproval>();

			@Override
			public List<WorkPlaceApproval> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					retorno = workPlaceApprovalService.allWorkPlaceApprovalByCompany(first, pageSize, sortField, sortOrder, filters, company);
					allWorkPlaceApprovalDataModel.setRowCount(workPlaceApprovalService.countAllWorkPlaceApprovalByCompany(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(WorkPlaceApproval obj) {
				return obj.getId();
			}

			@Override
			public WorkPlaceApproval getRowData(String rowKey) {
				for (WorkPlaceApproval obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	/**
	 * Clear criteria.
	 */
	public void clearCriteria() {
		this.criteria = "";
		this.companyRegOrSDLEnum = null;
		this.searchNGO = false;
		this.searchLevyPaying = false;
		this.searchExistingNonLevyPaying = false;
		this.searchNonLevyPaying = false;
		this.searchNonSeta = false;
		this.sdfCompanyHistory = null;
		this.sdfHistory = false;
	}

	/**
	 * Find.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public void find() throws Exception {
		Company company = companyService.searchByReg(criteria);
		if (company == null) {
			company = new Company();
			company.setExistingCompany(false);
			company.setCompanyRegistrationNumber(criteria);
		} else {
			company.setExistingCompany(true);
		}
		company.setNonLevyPaying(false);
		company.setDoneSearch(true);
		company.setNonSetaCompany(true);
		clearCriteria();
		object.callBackMethod(company);
	}
	
	public void findForNewRegNumber() throws Exception {
		Company company = companyService.searchByReg(criteria);
		if (company == null) {
			company = new Company();
			company.setExistingCompany(false);
			company.setCompanyRegistrationNumber(criteria);
		} else {
			company = null;
			//addErrorMessage("Company Registration Number Has Been Located On NSDMS. Please Enter a Differenet Company Registration Number or Search Via: Levy-Paying or Non-Levy Paying Entity Selection.");
			throw new Exception("Company Registration Number Has Been Located On NSDMS. Please Enter a Differenet Company Registration Number or Search Via: Levy-Paying or Non-Levy Paying Entity Selection.");
//			company.setExistingCompany(true);
		}
		company.setNonLevyPaying(false);
		company.setDoneSearch(true);
		company.setNonSetaCompany(true);
		clearCriteria();
		object.callBackMethod(company);
	}
	
	public void findNonMersetaCompany() throws Exception {
		Company company = companyService.findNonMersetaCompany(criteria);
		if (company == null) {
			company = new Company();
			company.setExistingCompany(false);
			company.setCompanyRegistrationNumber(criteria);
		} else {
			company.setExistingCompany(true);
		}
		company.setNonLevyPaying(false);
		company.setDoneSearch(true);
		clearCriteria();
		object.callBackMethod(company);
	}
	
	public void findNonSeta() throws Exception {
		Company company = companyService.searchNonSetaByReg(criteria);
		if (company == null) {
			company = new Company();
			company.setExistingCompany(false);
			company.setCompanyRegistrationNumber(criteria);
		} else {
			company.setExistingCompany(true);
		}
		company.setNonLevyPaying(false);
		company.setDoneSearch(true);
		clearCriteria();
		object.callBackMethod(company);
	}

	/**
	 * Find by SDL.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public void findBySDL() throws Exception {
		System.out.println("Inside SearchCompany:: findBySDL :: criteria ---" + criteria);
		
		Company company = companyService.searchBySDL(criteria);
		if (company == null) {
			company = new Company();
			company.setExistingCompany(false);
			company.setLevyNumber(criteria);
		} else {
			company.setExistingCompany(true);
		}
		if (company.getNonLevyPaying() == null) {
			if (criteria != null && !criteria.trim().isEmpty() && criteria.trim().contains("L")) {
				company.setNonLevyPaying(false);
			}else {
				company.setNonLevyPaying(true);
			}
		}
//		company.setNonLevyPaying(false);
		company.setDoneSearch(true);
		clearCriteria();
		object.callBackMethod(company);
	}

	/**
	 * Method to find by SDL number and display error if accured
	 */
	public void findBySDLTrainingProviderRegistartionMethod() {
		try {
			findBySDLTrainingProviderRegistartion();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Find by SDL.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public void findBySDLTrainingProviderRegistartion() throws Exception {
		String msg = criteria;
		Company company = companyService.searchBySDL(criteria);
		if (company == null) {
			throw new Exception(msg + " is not registered on the portal.");
		} else if (company != null) {
			List<TrainingProviderApplication> tpa = trainingProviderApplicationService.findByCompany(company);
			if (tpa != null && tpa.size() > 0) {
				//throw new Exception("The selected company is in use");
			}
		} else {
			company.setExistingCompany(true);
		}
		company.setNonLevyPaying(false);
		company.setDoneSearch(true);
		clearCriteria();
		object.callBackMethod(company);
	}

	/**
	 * Find by SDL.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public void searchLegacyTP(){
		try {
			LegacyProviderAccreditationService legacyProviderAccreditationService=new LegacyProviderAccreditationService();
			LegacyProviderAccreditation lpa=null;
			/*
			 *  provider search 
			 */
			List<LegacyProviderAccreditation> list = legacyProviderAccreditationService.searchProviderVersionTwo(criteria);
//			List<LegacyProviderAccreditation> list=legacyProviderAccreditationService.searchProvider(criteria);
			if(list !=null && list.size() > 0 ) {
				lpa=list.get(0);
				// check if acc already in use
				if (trainingProviderApplicationService.countLegacyProviderApllicationsLinkedToOpenApplications(lpa.getId(), ApprovalEnum.getOpenStatusForTrainingProviderApplicationsForLegacy()) > 0) {
					lpa = null;
					throw new Exception("Accreditation already in use on NSDMS, contact support for "+criteria);
				}
				Company company = companyService.searchBySDL(criteria);
				if (company==null) {
					company = new Company();
					company.setExistingCompany(false);
					company.setCompanyName(lpa.getOrganisationNameLegal());
					company.setTradingName(lpa.getOrganisationNameTrade());
					company.setAccreditationNumber(lpa.getAccreditationNo());
					company.setNonSetaCompany(true);
				} else {
					company.setExistingCompany(true);
				}
				company.setNonLevyPaying(false);
				company.setDoneSearch(true);
				clearCriteria();
				lpa.setCompany(company);
				object.callBackMethod(lpa);
			} else {
				throw new Exception("No Active Accreditation Legacy Data Found For "+criteria);
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
		
	}
	
	public void searchLegacyTpVersionTwo(){
		try {
			LegacyProviderAccreditationService legacyProviderAccreditationService=new LegacyProviderAccreditationService();
			LegacyProviderAccreditation lpa=null;
			/*
			 *  provider search 
			 */
			List<LegacyProviderAccreditation> list = legacyProviderAccreditationService.searchProviderVersionTwo(criteria);
			if(list !=null && list.size() > 0 ) {
				lpa=list.get(0);
				// check if Accreditation already in use
				if (trainingProviderApplicationService.countLegacyProviderApllicationsLinkedToOpenApplications(lpa.getId(), ApprovalEnum.getOpenStatusForTrainingProviderApplicationsForLegacy()) > 0) {
					lpa = null;
					throw new Exception("Accreditation already in use on NSDMS, contact support for "+criteria);
				}
				// ensure linked SDL number assigned
				if (lpa.getLinkedSdl() == null || lpa.getLinkedSdl().isEmpty()) {
					lpa = null;
					throw new Exception("Accreditation has no linked SDL Number assigned, contact support with "+criteria + " and message displayed.");
				}
				// Validate Linked SDL Number
				if (!CompanyValidationService.instance().levyNumberValidation(lpa.getLinkedSdl().trim())) {
					throw new Exception("Accreditation has an invalid linked SDL number assigned, contact support with "+criteria + " and invalid SDL Number: " + lpa.getLinkedSdl().trim());
				}
				// finds by levy number assigned to legacy Accreditation
				//Company company = companyService.findByLevyNumberIncludingNonSeta(lpa.getLinkedSdl().trim());
				Company company = companyService.findByLevyNumberWhereStatusNotInList(lpa.getLinkedSdl().trim(), CompanyStatusEnum.getIgnoreStatusList());
				
				
				if (company == null) {
					lpa = null;
					throw new Exception("Unable to locate SDL number profile linked to the legacy accrediciation. Contact support!");
//					company = new Company();
//					company.setExistingCompany(false);
//					company.setCompanyName(lpa.getOrganisationNameLegal());
//					company.setTradingName(lpa.getOrganisationNameTrade());
//					company.setAccreditationNumber(lpa.getAccreditationNo());
//					company.setLevyNumber(lpa.getLinkedSdl());
//					company.setNonSetaCompany(true);
//					company.setNonLevyPaying(true);
//					company.setResidentialAddress(new Address());
//					company.setPostalAddress(new Address());
				} else {
					companyService.resolveCompanyAddresses(company);
					company.setExistingCompany(true);
				}
				companyService.determainAlterationsByConfigProcess(company, ConfigDocProcessEnum.SDP_LEGACY_APPLICATION);
				company.setDoneSearch(true);
				company.setLockInfoUpdate(false);
				clearCriteria();
				lpa.setCompany(company);
				object.callBackMethod(lpa);
			} else {
				throw new Exception("No Active Accreditation Legacy Data Found For "+criteria);
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		
	}
	
	public void findByNonSetaCompany() throws Exception {
		// to add for future development for re-registration of non-merSETA companies
		if (nonMersetaCompany) {
			searchEntityId = false;
			searchNNumber = false;
			searchNGO=false;
		}
		company = new Company();
		company.setExistingCompany(false);
		company.setNonLevyPaying(false);
		company.setNonSetaCompany(true);
		company.setDoneSearch(true);
		clearCriteria();
		object.callBackMethod(company);
	}

	/**
	 * Find by AccreditationNumber.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public void findByAccreditationNumberTrainingProviderRegistartion() throws Exception {
		Company company = companyService.searchByAccreditationNumber(criteria);
		if (company == null) {
			company = new Company();
			company.setExistingCompany(false);
			company.setAccreditationNumber(criteria);
		} else {
			company.setExistingCompany(true);
		}
		company.setDoneSearch(true);
		clearCriteria();
		object.callBackMethod(company);
	}

	public void returnNonLevyPaying() {
		if (searchNGO) {
			Company company;
			company = new Company();
			company.setExistingCompany(false);
			company.setDoneSearch(true);
			company.setNonLevyPaying(true);
			company.setNonSetaCompany(false);
			company.setCategorization(CategorizationEnum.Silver);
			company.setOrganisationType(null);
			company.setSeta(null);
			company.setPayeSDLNumber(null);
			clearCriteria();
			object.callBackMethod(company);
		}
	}

	public void clearCheckboxForLevyPaying() {
		searchNonSeta = false;
		searchNonLevyPaying = false;
		searchExistingNonLevyPaying = false;
	}
	
	public void clearCheckboxForExistingNonLevyPaying() {
		searchNonSeta = false;
		searchNonLevyPaying = false;
		searchLevyPaying = false;
	}

	public void clearCheckboxForNonLevyPaying() {
		searchNonSeta = false;
		searchLevyPaying = false;
		searchExistingNonLevyPaying = false;
	}

	public void clearCheckboxForNonSeta() {
		searchLevyPaying = false;
		searchNonLevyPaying = false;
		searchExistingNonLevyPaying = false;
	}

	public void clearCheckbox1() {
		searchLevyPaying = false;
		searchExistingNonLevyPaying = false;
		searchTrainingProvider = false;
	}

	public void clearCheckbox2() {
		searchProvider = false;
		searchTrainingProvider = false;
	}

	public void clearCheckbox3() {
		searchProvider = false;
		searchLevyPaying = false;
		searchExistingNonLevyPaying = false;
	}

	public void searchByAccNumber() {
		try {
			if (searchAccNumber) {
				searchEntityId = false;
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void searchByNNumber() {
		if (searchNNumber) {
			searchEntityId = false;
			nonMersetaCompany = false;
			searchNGO=false;
		}
	}

	public void searchByNomMersetaComp() {
		if (nonMersetaCompany) {
			searchEntityId = false;
			searchNNumber = false;
		}
	}

	public void searchByEntityId() {
		try {
			if (searchEntityId) {
				searchNNumber = false;
				nonMersetaCompany = false;
				searchNGO=false;
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void companyBankingDetilasInfo() {
		dataModelBankingDetails = new LazyDataModel<Company>() {
			private static final long serialVersionUID = 1L;
			private List<Company> companyList = new ArrayList<>();

			@Override
			public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					if (sortField == null) {
						sortField = "companyStatus";
						sortOrder = SortOrder.DESCENDING;
					}
					filters.put("companyStatus", CompanyStatusEnum.Active);
					companyList = companyService.allCompanyBankingDetails(Company.class, first, pageSize, sortField,
							sortOrder, filters);
					dataModelBankingDetails.setRowCount(companyService.count(Company.class, filters));

				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return companyList;
			}

			@Override
			public Object getRowKey(Company object) {
				// TODO Auto-generated method stub
				return object.getId();
			}

			@Override
			public Company getRowData(String rowKey) {
				for (Company obj : companyList) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}
	
	public void companyCommunicationDataModelInfo() {
		if (companyCommunicationService == null) {
			companyCommunicationService = new CompanyCommunicationService();
		}

		companyCommunicationDataModel = new LazyDataModel<CompanyCommunication>() {
			private static final long serialVersionUID = 1L;
			private List<CompanyCommunication> retorno = new ArrayList<CompanyCommunication>();

			@Override
			public List<CompanyCommunication> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					retorno = companyCommunicationService.allCompanyCommunicationByCompanyId(first, pageSize, sortField, sortOrder, filters, company.getId());
					companyCommunicationDataModel.setRowCount(companyCommunicationService.countAllCompanyCommunicationByCompanyId(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(CompanyCommunication obj) {
				return obj.getId();
			}
			@Override
			public CompanyCommunication getRowData(String rowKey) {
				for (CompanyCommunication obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void prepNewCompanyCommunication(){
		try {
			companyCommunication = new CompanyCommunication(company);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void switchCompanyCommunicationActive(){
		try {
			if (companyCommunication == null || companyCommunication.getId() == null) {
				throw new Exception("Unable to locate Company Communication, contact support!");
			} else {
				if (companyCommunication.getVisible()) {
					companyCommunication.setVisible(Boolean.FALSE);
				} else {
					companyCommunication.setVisible(Boolean.TRUE);
				}
				companyCommunicationService.createUpdateCompanyCommunication(companyCommunication, getSessionUI().getActiveUser());
				companyCommunication = null;
				companyCommunicationDataModelInfo();
				
				addInfoMessage("Action Complete");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void createUpdateCompanyCommunication(){
		try {
			if (companyCommunicationService == null) {
				companyCommunicationService = new CompanyCommunicationService();
			}
			companyCommunicationService.createUpdateCompanyCommunication(companyCommunication, getSessionUI().getActiveUser());
			companyCommunication = null;
			companyCommunicationDataModelInfo();
			super.runClientSideExecute("PF('companyCommunicationDlg').hide()");
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}
	
	public void cancelCreateUpdateCompanyCommunication(){
		try {
			companyCommunication = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}
	
	public void prepDocUploadCompanyCommunication(){
		try {
			doc = new Doc();
			super.runClientSideExecute("PF('dlgUploadCompanyCommunication').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}
	
	public void storeFileCompanyCommunication(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setUsers(getSessionUI().getActiveUser());
			
			if (doc.getId() != null) {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			} else {
				doc.setTargetKey(companyCommunication.getId());
				doc.setTargetClass(CompanyCommunication.class.getName());
				if (doc.getId() == null)
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUploadCompanyCommunication').hide()");
			companyCommunicationDataModelInfo();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Company> completeCompany(String desc) {
		List<Company> l = null;
		try {
			l = companyService.findByNameOrLevyNum(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the object.
	 *
	 * @return the object
	 */
	public AbstractUIInterface getObject() {
		return object;
	}

	/**
	 * Sets the object.
	 *
	 * @param object
	 *            the new object
	 */
	public void setObject(AbstractUIInterface object) {
		this.object = object;
		clearCriteria();
	}

	/**
	 * Gets the criteria.
	 *
	 * @return the criteria
	 */
	public String getCriteria() {
		return criteria;
	}

	/**
	 * Sets the criteria.
	 *
	 * @param criteria
	 *            the new criteria
	 */
	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	/**
	 * Gets the company reg or SDL enum.
	 *
	 * @return the company reg or SDL enum
	 */
	public CompanyRegOrSDLEnum getCompanyRegOrSDLEnum() {
		return companyRegOrSDLEnum;
	}

	/**
	 * Sets the company reg or SDL enum.
	 *
	 * @param companyRegOrSDLEnum
	 *            the new company reg or SDL enum
	 */
	public void setCompanyRegOrSDLEnum(CompanyRegOrSDLEnum companyRegOrSDLEnum) {
		this.companyRegOrSDLEnum = companyRegOrSDLEnum;
	}

	/**
	 * Checks if is search NGO.
	 *
	 * @return true, if is search NGO
	 */
	public boolean isSearchNGO() {
		return searchNGO;
	}

	/**
	 * Sets the search NGO.
	 *
	 * @param searchNGO
	 *            the new search NGO
	 */
	public void setSearchNGO(boolean searchNGO) {
		this.searchNGO = searchNGO;
	}

	public String getCompanyRegistrationNumberFormat() {
		return companyRegistrationNumberFormat;
	}

	public void setCompanyRegistrationNumberFormat(String companyRegistrationNumberFormat) {
		this.companyRegistrationNumberFormat = companyRegistrationNumberFormat;
	}

	public String getCompanyLevyNumberFormat() {
		return companyLevyNumberFormat;
	}

	public void setCompanyLevyNumberFormat(String companyLevyNumberFormat) {
		this.companyLevyNumberFormat = companyLevyNumberFormat;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {

		this.company = company;
	}

	public boolean isSearchLevyPaying() {
		return searchLevyPaying;
	}

	public void setSearchLevyPaying(boolean searchLevyPaying) {
		this.searchLevyPaying = searchLevyPaying;
	}

	public LazyDataModel<Company> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<Company> dataModel) {
		this.dataModel = dataModel;
	}

	public List<Company> getCompanyFilteredList() {
		return companyFilteredList;
	}

	public void setCompanyFilteredList(List<Company> companyFilteredList) {
		this.companyFilteredList = companyFilteredList;
	}

	public List<MailLog> getMailLogs() {
		return mailLogs;
	}

	public void setMailLogs(List<MailLog> mailLogs) {
		this.mailLogs = mailLogs;
	}

	public void findMailLog() {
		try {
			this.mailLogs = mailLogService.allMailForCompany(company);
			if (this.mailLogs.size() < 1) {
				addInfoMessage("No mails has been send");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void findMailLogVersionTwo() {
		try {
			if (mailLogService.allMailForCompanyCount(company.getId()) < 1) {
				addWarningMessage("No mails has been sent");
			} else {
				companyMailLogDataModelInfo();
				runClientSideExecute("PF('mailLogV2Dlg').show()");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void companyMailLogDataModelInfo() {
		companyMailLogDataModel = new LazyDataModel<MailLog>() {
			private static final long serialVersionUID = 1L;
			private List<MailLog> list = new ArrayList<>();
			@Override
			public List<MailLog> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					list = mailLogService.allMailLogForCompany(first, pageSize, sortField,sortOrder, filters, company);
					companyMailLogDataModel.setRowCount(mailLogService.countAllMailLogForCompany(filters));
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return list;
			}
			@Override
			public Object getRowKey(MailLog object) {
				return object.getId();
			}
			@Override
			public MailLog getRowData(String rowKey) {
				for (MailLog obj : list) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}
	
	public void downloadWorkPlaceApproval() {
		try {
			workPlaceApprovalService.downloadVTwo(workPlaceApproval);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void downloadWorkPlaceApprovalLetter() {
		try {
			workPlaceApprovalService.downloadWorkPlaceApprovalLetter(workPlaceApproval);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void downloadRemittanceAdvice() {
		try {
			RemittanceAdviceService.instance().generateRemittanceAdvice(company);
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public List<CompanyUsers> getCompanyUsers() {
		return companyUsers;
	}

	public void setCompanyUsers(List<CompanyUsers> companyUsers) {
		this.companyUsers = companyUsers;
	}

	public LazyDataModel<Employees> getEmpDataModel() {
		return empDataModel;
	}

	public void setEmpDataModel(LazyDataModel<Employees> empDataModel) {
		this.empDataModel = empDataModel;
	}

	public LazyDataModel<SDFCompany> getSdfDataModel() {
		return sdfDataModel;
	}

	public void setSdfDataModel(LazyDataModel<SDFCompany> sdfDataModel) {
		this.sdfDataModel = sdfDataModel;
	}

	public List<BankingDetails> getBankingDetailsList() {
		return bankingDetailsList;
	}

	public void setBankingDetailsList(List<BankingDetails> bankingDetailsList) {
		this.bankingDetailsList = bankingDetailsList;
	}

	public BankingDetails getBankingDetails() {
		return bankingDetails;
	}

	public void setBankingDetails(BankingDetails bankingDetails) {
		this.bankingDetails = bankingDetails;
	}

	public List<Company> getLinkedCompanies() {
		return linkedCompanies;
	}

	public void setLinkedCompanies(List<Company> linkedCompanies) {
		this.linkedCompanies = linkedCompanies;
	}

	public List<TrainingComittee> getTrainingComittees() {
		return trainingComittees;
	}

	public void setTrainingComittees(List<TrainingComittee> trainingComittees) {
		this.trainingComittees = trainingComittees;
	}

	public List<Sites> getSites() {
		return sites;
	}

	public void setSites(List<Sites> sites) {
		this.sites = sites;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public boolean isSearchProvider() {
		return searchProvider;
	}

	public void setSearchProvider(boolean searchProvider) {
		this.searchProvider = searchProvider;
	}

	public ProviderNameRegisTrationNumberEnum getProviderNameRegisTrationNumberEnum() {
		return providerNameRegisTrationNumberEnum;
	}

	public void setProviderNameRegisTrationNumberEnum(
			ProviderNameRegisTrationNumberEnum providerNameRegisTrationNumberEnum) {
		this.providerNameRegisTrationNumberEnum = providerNameRegisTrationNumberEnum;
	}

	public String getCompanyAccreditationNumberFormat() {
		return companyAccreditationNumberFormat;
	}

	public void setCompanyAccreditationNumberFormat(String companyAccreditationNumberFormat) {
		this.companyAccreditationNumberFormat = companyAccreditationNumberFormat;
	}

	public boolean isSearchAccNumber() {
		return searchAccNumber;
	}

	public void setSearchAccNumber(boolean searchAccNumber) {
		this.searchAccNumber = searchAccNumber;
	}

	public boolean isSearchEntityId() {
		return searchEntityId;
	}

	public void setSearchEntityId(boolean searchEntityId) {
		this.searchEntityId = searchEntityId;
	}

	public boolean isSearchTrainingProvider() {
		return searchTrainingProvider;
	}

	public void setSearchTrainingProvider(boolean searchTrainingProvider) {
		this.searchTrainingProvider = searchTrainingProvider;
	}

	public LazyDataModel<SitesSme> getAllSmeDataModel() {
		return allSmeDataModel;
	}

	public void setAllSmeDataModel(LazyDataModel<SitesSme> allSmeDataModel) {
		this.allSmeDataModel = allSmeDataModel;
	}

	public LazyDataModel<WorkPlaceApproval> getAllWorkPlaceApprovalDataModel() {
		return allWorkPlaceApprovalDataModel;
	}

	public void setAllWorkPlaceApprovalDataModel(LazyDataModel<WorkPlaceApproval> allWorkPlaceApprovalDataModel) {
		this.allWorkPlaceApprovalDataModel = allWorkPlaceApprovalDataModel;
	}

	public SDFCompany getSelectedSdfCompany() {
		return selectedSdfCompany;
	}

	public void setSelectedSdfCompany(SDFCompany selectedSdfCompany) {
		this.selectedSdfCompany = selectedSdfCompany;
	}

	public Users getSdf() {
		return sdf;
	}

	public void setSdf(Users sdf) {
		this.sdf = sdf;
	}

	public List<Doc> getChangeReasonDocs() {
		return changeReasonDocs;
	}

	public void setChangeReasonDocs(List<Doc> changeReasonDocs) {
		this.changeReasonDocs = changeReasonDocs;
	}

	public boolean isDisplaySdfRegDocs() {
		return displaySdfRegDocs;
	}

	public void setDisplaySdfRegDocs(boolean displaySdfRegDocs) {
		this.displaySdfRegDocs = displaySdfRegDocs;
	}

	public boolean isDisplayChangeDocs() {
		return displayChangeDocs;
	}

	public void setDisplayChangeDocs(boolean displayChangeDocs) {
		this.displayChangeDocs = displayChangeDocs;
	}

	public List<SDFCompanyHistory> getSdfCompanyHistory() {
		return sdfCompanyHistory;
	}

	public void setSdfCompanyHistory(List<SDFCompanyHistory> sdfCompanyHistory) {
		this.sdfCompanyHistory = sdfCompanyHistory;
	}

	public boolean isSdfHistory() {
		return sdfHistory;
	}

	public void setSdfHistory(boolean sdfHistory) {
		this.sdfHistory = sdfHistory;
	}

	public String getCompanyNNumberFormat() {
		return companyNNumberFormat;
	}

	public void setCompanyNNumberFormat(String companyNNumberFormat) {
		this.companyNNumberFormat = companyNNumberFormat;
	}

	public LazyDataModel<Company> getDataModelBankingDetails() {
		return dataModelBankingDetails;
	}

	public void setDataModelBankingDetails(LazyDataModel<Company> dataModelBankingDetails) {
		this.dataModelBankingDetails = dataModelBankingDetails;
	}

	public boolean isSearchNonLevyPaying() {
		return searchNonLevyPaying;
	}

	public void setSearchNonLevyPaying(boolean searchNonLevyPaying) {
		this.searchNonLevyPaying = searchNonLevyPaying;
	}

	public boolean isSearchNonSeta() {
		return searchNonSeta;
	}

	public void setSearchNonSeta(boolean searchNonSeta) {
		this.searchNonSeta = searchNonSeta;
	}

	public boolean isSearchNNumber() {
		return searchNNumber;
	}

	public void setSearchNNumber(boolean searchNNumber) {
		this.searchNNumber = searchNNumber;
	}

	public boolean isNonMersetaCompany() {
		return nonMersetaCompany;
	}

	public void setNonMersetaCompany(boolean nonMersetaCompany) {
		this.nonMersetaCompany = nonMersetaCompany;
	}

	public boolean isSearchExistingNonLevyPaying() {
		return searchExistingNonLevyPaying;
	}

	public void setSearchExistingNonLevyPaying(boolean searchExistingNonLevyPaying) {
		this.searchExistingNonLevyPaying = searchExistingNonLevyPaying;
	}
    public void prepareNonSetaCompany()
    {
    	searchNNumber=false;
    	searchEntityId=false;
    	nonMersetaCompany=false;
        nonMersetaCompany=true;
    }

	public WorkPlaceApproval getWorkPlaceApproval() {
		return workPlaceApproval;
	}

	public void setWorkPlaceApproval(WorkPlaceApproval workPlaceApproval) {
		this.workPlaceApproval = workPlaceApproval;
	}

	public LazyDataModel<CompanyCommunication> getCompanyCommunicationDataModel() {
		return companyCommunicationDataModel;
	}

	public void setCompanyCommunicationDataModel(LazyDataModel<CompanyCommunication> companyCommunicationDataModel) {
		this.companyCommunicationDataModel = companyCommunicationDataModel;
	}

	public CompanyCommunication getCompanyCommunication() {
		return companyCommunication;
	}

	public void setCompanyCommunication(CompanyCommunication companyCommunication) {
		this.companyCommunication = companyCommunication;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public LazyDataModel<Tasks> getDataModelBankingDetailsTasks() {
		return dataModelBankingDetailsTasks;
	}

	public void setDataModelBankingDetailsTasks(LazyDataModel<Tasks> dataModelBankingDetailsTasks) {
		this.dataModelBankingDetailsTasks = dataModelBankingDetailsTasks;
	}

	public LazyDataModel<MailLog> getCompanyMailLogDataModel() {
		return companyMailLogDataModel;
	}

	public void setCompanyMailLogDataModel(LazyDataModel<MailLog> companyMailLogDataModel) {
		this.companyMailLogDataModel = companyMailLogDataModel;
	}


}
