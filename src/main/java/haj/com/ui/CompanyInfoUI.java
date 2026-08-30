package haj.com.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.microsoft.schemas.dynamics.gp._2006._01.Vendor;

import haj.com.constants.HAJConstants;
import haj.com.entity.ActiveContracts;
import haj.com.entity.BankingDetails;
import haj.com.entity.ChangeReason;
import haj.com.entity.Company;
import haj.com.entity.CompanyCommunication;
import haj.com.entity.CompanyUsers;
import haj.com.entity.ConfigDoc;
import haj.com.entity.Doc;
import haj.com.entity.OfoCodes;
import haj.com.entity.RejectReasonsChild;
import haj.com.entity.SDFCompany;
import haj.com.entity.Sites;
import haj.com.entity.SitesSme;
import haj.com.entity.SmeQualifications;
import haj.com.entity.TrainingComittee;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.WorkPlaceApproval;
import haj.com.entity.WorkPlaceApprovalMentors;
import haj.com.entity.Wsp;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.WorkplaceApprovalTypeEnum;
import haj.com.entity.lookup.Learnership;
import haj.com.entity.lookup.LegacyEmployerWA2Learnership;
import haj.com.entity.lookup.LegacyEmployerWA2Qualification;
import haj.com.entity.lookup.LegacyEmployerWA2SkillsProgramme;
import haj.com.entity.lookup.LegacyEmployerWA2Trade;
import haj.com.entity.lookup.LegacyEmployerWA2UnitStandard;
import haj.com.entity.lookup.LegacyEmployerWA2Workplace;
import haj.com.entity.lookup.LegacyOrganisationSites;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.entity.lookup.UnitStandards;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.ActiveContractsService;
import haj.com.service.AddressService;
import haj.com.service.BankingDetailsService;
import haj.com.service.ChangeReasonService;
import haj.com.service.CompanyCommunicationService;
import haj.com.service.CompanyService;
import haj.com.service.CompanyUsersService;
import haj.com.service.ConfigDocService;
import haj.com.service.DocService;
import haj.com.service.JasperService;
import haj.com.service.RejectReasonsChildService;
import haj.com.service.RemittanceAdviceService;
import haj.com.service.SDFCompanyService;
import haj.com.service.SitesService;
import haj.com.service.SitesSmeService;
import haj.com.service.SmeQualificationsService;
import haj.com.service.TrainingComitteeService;
import haj.com.service.TrainingProviderApplicationService;
import haj.com.service.UnitStandardsService;
import haj.com.service.UpdateAuditTrailService;
import haj.com.service.WorkPlaceApprovalMentorsService;
import haj.com.service.WorkPlaceApprovalService;
import haj.com.service.WspService;
import haj.com.service.lookup.LearnershipService;
import haj.com.service.lookup.LegacyOrganisationSitesService;
import haj.com.service.lookup.SkillsProgramService;
import haj.com.utils.GenericUtility;
import haj.com.validators.exports.services.AddressValidationService;


/**
 * The Class CompanyInfoUI.
 */
@ManagedBean(name = "companyinfoUI")
@ViewScoped
public class CompanyInfoUI extends AbstractUI {
	
	private SDFCompanyService service = new SDFCompanyService();

	/** The data model. */
	private LazyDataModel<SDFCompany> dataModel;
	private LazyDataModel<SDFCompany> usersDataModel;
	private SDFCompany sdfCompany;
	private static SDFCompany globalSsdfCompany;

	/** The company service. */
	private CompanyService companyService = new CompanyService();

	/** The company. */
	private Company company = null;
	
	/** The previously linked company */
	private Company previouslyLinkedCompany = null;

	private AddressValidationService addressValidationService=new AddressValidationService();
	
	private AddressService addressService = new AddressService();
	
	/** The js. */
	private JasperService js = new JasperService();

	/** The banking details. */
	private BankingDetails bankingDetails;
	private List<BankingDetails> bankingDetailsList;

	/** The banking details service. */
	private BankingDetailsService bankingDetailsService = new BankingDetailsService();
	/** The company users service. */
	private CompanyUsersService companyUsersService = new CompanyUsersService();

	/** The sites service. */
	private SitesService sitesService = new SitesService();
	/** The linked companies. */
	private List<Company> linkedCompanies;

	/** The sites. */
	private List<Sites> sites;
	private ActiveContractsService activeContractsService = new ActiveContractsService();
	private List<ActiveContracts> activeContracts;
	List<Doc> docs = new ArrayList<>();
	private List<SmeQualifications> smeQualificationsList = null;

	private LazyDataModel<Wsp> dataModelWspWithAllocation;
	private WspService wspService = new WspService();

	/** The site. */
	private Sites site = new Sites();

	/** The training comittees. */
	private List<TrainingComittee> trainingComittees;

	/** The training comittee service. */
	private TrainingComitteeService trainingComitteeService = new TrainingComitteeService();
	/** The company users. */
	private List<CompanyUsers> companyUsers;

	/** Redirection page after providing change reason */
	private String redirectionPage;
	public static ChangeReason changeReason = new ChangeReason();

	/** The trainingcomittee. */
	private TrainingComittee trainingcomittee = new TrainingComittee();

	/** The Sites SME */
	private SitesSme sitesSme = null;
	private SitesSmeService sitesSmeService = null;
	private LazyDataModel<SitesSme> allSmeDataModel;
	private LazyDataModel<SitesSme> sitesSmeDataModel;

	/** Workplace approval */
	private WorkPlaceApprovalService workPlaceApprovalService = null;
	private LazyDataModel<WorkPlaceApproval> allWorkPlaceApprovalDataModel;
	private WorkPlaceApproval workPlaceApproval = null;
	private Qualification qualification = null;
	private OfoCodes ofoCodes = null;
	private boolean byQualification = true;
	private List<Sites> sitesList = null;
	private Sites selectedSite = null;
	private boolean useCompanyAsSite = false;

	private int index;
	private boolean copyAddress;
	private boolean canCreateNewCompany;

	private Doc doc;
	private Doc newVersionDoc;

	@ManagedProperty(value = "#{employeesimportUI}")
	private EmployeesImportUI employeesImportUI;

	@ManagedProperty(value = "#{employeesUI}")
	private EmployeesUI employeesUI;

	private RejectReasonsChildService rejectReasonsService = new RejectReasonsChildService();
	private List<RejectReasonsChild> rejectReasonsChild;
	private SmeQualificationsService smeQualificationsService = new SmeQualificationsService();

	private LazyDataModel<TrainingProviderApplication> tpDataModel;

	private TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();
	/** The Training Provider Application */
	private TrainingProviderApplication selectTrainingProviderApplication;

	private LegacyOrganisationSitesService legacyOrganisationSitesService = new LegacyOrganisationSitesService();
	private LazyDataModel<LegacyOrganisationSites> dataModelLegacySites;
	private LegacyEmployerWA2Workplace legacyEmployerWA2Workplace;
	
	private LegacyEmployerWA2Learnership legacyEmployerWA2Learnership;
	private LegacyEmployerWA2Qualification legacyEmployerWA2Qualification;
	private LegacyEmployerWA2SkillsProgramme legacyEmployerWA2SkillsProgramme;
	private LegacyEmployerWA2Trade legacyEmployerWA2Trade;
	private LegacyEmployerWA2UnitStandard legacyEmployerWA2UnitStandard;
	
	private String validiationExceptionsCompany = "";
	
	/* Company Communications Section */
	private CompanyCommunicationService companyCommunicationService; 
	private LazyDataModel<CompanyCommunication> companyCommunicationDataModel;
	
	private Vendor vendor;
	private WorkPlaceApprovalMentors workPlaceApprovalMentors;
	private WorkPlaceApprovalMentorsService workPlaceApprovalMentorsService = new WorkPlaceApprovalMentorsService();
	
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
	 * Initialize method to get all Company and prepare a for a create of a new
	 * Company.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see Company
	 */
	private void runInit() throws Exception {
		if (super.getParameter("id", false) != null) {
			company = companyService.findByGUID(super.getParameter("id", false).toString());
			bankingDetails = bankingDetailsService.findByCompanyLates(company);
		} else {
			companysdfInfo();
			nonSetaCompanysdfInfo();
		}
	}

	public void companysdfInfo() {
		System.out.println("companysdfInfo");
		try {
			if (getSessionUI().getActiveUser() != null) {
				Map<String, Object> filters = new HashMap<>();
				canCreateNewCompany = service.allCompanyFromSDFCountWherePrimary(filters, getSessionUI().getActiveUser()).intValue() == 0;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		dataModel = new LazyDataModel<SDFCompany>() {
			private static final long serialVersionUID = 1L;
			private List<SDFCompany> retorno = new ArrayList<SDFCompany>();

			@Override
			public List<SDFCompany> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (getSessionUI().getActiveUser().getAdmin() == null || !getSessionUI().getActiveUser().getAdmin()) {
						retorno = service.allCompanyFromSDFWherePrimary(SDFCompany.class, first, pageSize, sortField, sortOrder, filters, getSessionUI().getActiveUser());
						dataModel.setRowCount(service.allCompanyFromSDFCountWherePrimary(filters, getSessionUI().getActiveUser()).intValue());
					} else {
						retorno = service.allSDFCompany(SDFCompany.class, first, pageSize, sortField, sortOrder, filters);
						dataModel.setRowCount(service.count(SDFCompany.class, filters));
					}
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
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}

	public void nonSetaCompanysdfInfo() {
		tpDataModel = new LazyDataModel<TrainingProviderApplication>() {

			private static final long serialVersionUID = 1L;
			private List<TrainingProviderApplication> retorno = new ArrayList<TrainingProviderApplication>();

			@Override
			public List<TrainingProviderApplication> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					filters.put("userID", getSessionUI().getActiveUser().getId());
					retorno = trainingProviderApplicationService.findUserTP(first, pageSize, sortField, sortOrder, filters);
					tpDataModel.setRowCount(trainingProviderApplicationService.countUuserTP(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(TrainingProviderApplication obj) {
				return obj.getId();
			}

			@Override
			public TrainingProviderApplication getRowData(String rowKey) {
				for (TrainingProviderApplication obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	public void listenerMethod(SitesSme sitesSme) {
		try {
			this.docs = sitesSme.getDocs();
			smeQualificationsList = smeQualificationsService.findBySme(sitesSme);
			for (SmeQualifications smequalifications : smeQualificationsList) {
				if (smequalifications.getDocs().size() > 0) docs.addAll(smequalifications.getDocs());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void gotoNewCompany() {
		if (canCreateNewCompany) super.setParameter("startFromNew", canCreateNewCompany, true);
		super.setParameter("previousCompany", null, true);
		super.ajaxRedirect("/pages/externalparty/newcompany.jsf");
	}

	public void gotoRegisterNewLevyNumber() {
		if (canCreateNewCompany) super.setParameter("startFromNew", canCreateNewCompany, true);
		super.setParameter("previousCompany", sdfCompany, true);
		super.ajaxRedirect("/pages/externalparty/newcompany.jsf");
	}
	
	public void clearQualification() {
		this.qualification = null;
	}

	public void save() {
		try {
			sdfCompany.getCompany().setCompanyStatus(CompanyStatusEnum.PendingChangeApproval);
			sdfCompany.getCompany().setLocked(true);
			sdfCompany.getCompany().getResidentialAddress().setSameAddress(copyAddress);
			sdfCompany.getCompany().getPostalAddress().setSameAddress(copyAddress);
			service.changeDetails(sdfCompany.getCompany(), getSessionUI().getActiveUser());
			companysdfInfo();
			addInfoMessage(super.getEntryLanguage("update.successful"));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void clearPostal() {
		if (!copyAddress) {
			AddressService.instance().clearAddress(sdfCompany.getCompany().getPostalAddress());
		}
	}

	public void unlock() {
		try {
			sdfCompany = service.findByKey(sdfCompany.getId());
			sdfCompany.getCompany().setLocked(false);
			service.update(sdfCompany.getCompany());
			onTabChange();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void lock() {
		try {
			sdfCompany = service.findByKey(sdfCompany.getId());
			sdfCompany.getCompany().setLocked(true);
			service.update(sdfCompany.getCompany());
			onTabChange();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void employeeInfo() {

		getEmployeesUI().setCompany(sdfCompany.getCompany());
		getEmployeesImportUI().setCompany(sdfCompany.getCompany());
		if (sdfCompany.getCompany().getResidentialAddress() != null) getEmployeesUI().getEmployees().setMunicipality(sdfCompany.getCompany().getResidentialAddress().getMunicipality());
		getEmployeesImportUI().employeesInfo();
		getEmployeesUI().employeesInfo();

	}

	public void onTabChange() {
		clearInformation();
		switch (index) {
			case 1:
				companyUsersInfo();
				break;
			case 2:
				employeeInfo();
				break;
			case 3:
				sdfDetail();
				break;
			case 4:
				bankingDetailsInfo();
				break;
			case 5:
				docInfo();
				break;
			case 6:
				linkedCompaniesInfo();
				break;
			case 7:
				trainingComitteesInfo();
				break;
			case 8:
				sitesInfo();
				break;
			case 9:
				contractInfo();
				break;
			case 10:
				sitesSmeInfo();
				break;
			case 11:
				workplaceApprovalInfo();
				break;
			case 12:
				companyCommunicationDataModelInfo();
				break;
			default:
				break;
		}
		super.runClientSideUpdate("companyForm:tabView");
	}

	private void clearInformation() {
		validiationExceptionsCompany = "";
	}

	private void linkedCompaniesInfo() {
		try {
			linkedCompanies = companyService.findLinkedCompany(sdfCompany.getCompany());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void sitesInfo() {
		try {
			sites = sitesService.findByCompany(sdfCompany.getCompany());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void contractInfo() {
		try {
			activeContracts = activeContractsService.findByCompany(sdfCompany.getCompany());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void companyUsersInfo() {
		try {
			companyUsers = companyUsersService.findByCompanyNotSDF(sdfCompany.getCompany());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void trainingComitteesInfo() {
		try {
			trainingComittees = trainingComitteeService.findByCompany(sdfCompany.getCompany());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void bankingdetailsInsert() {
		try {
			bankingDetailsService.bankingDetailsValidiation(bankingDetails);
			for (Doc doc : bankingDetails.getDocs()) {
				if (doc.getData() == null) throw new Exception("Please upload all documents");
			}
			bankingDetails.setCompany(sdfCompany.getCompany());
			UpdateAuditTrailService.instance().checkEntities(getSessionUI().getActiveUser(), bankingDetails, bankingDetails.getId(), sdfCompany.getCompany(), bankingDetails);
			bankingDetails.setId(null);
			bankingDetails.setStatus(ApprovalEnum.PendingSignOff);
			bankingDetailsService.createNoUpdate(this.bankingDetails, getSessionUI().getActiveUser());
			for (Doc doc : bankingDetails.getDocs()) {
				DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
			}
			onTabChange();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void docInfo() {
		try {
			service.resolveDocs(sdfCompany);
			this.newVersionDoc = new Doc();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void bankingDetailsInfo() {
		try {
			bankingDetails = bankingDetailsService.findByCompanyLates(sdfCompany.getCompany());
			if (bankingDetails == null) {
				bankingDetails = new BankingDetails();
			}
			bankingDetailsService.prepareNewDocs(ConfigDocProcessEnum.BANKING_DETAIL_MANAGEMENT, bankingDetails, CompanyUserTypeEnum.Company);
			if (bankingDetailsService.findByCompanyCount(sdfCompany.getCompany()) > 1) {
				bankingDetailsList = bankingDetailsService.findByCompany(sdfCompany.getCompany());
				bankingDetailsList.remove(bankingDetails);
			}
			this.vendor = bankingDetailsService.getGPDetailsForLNumber(sdfCompany.getCompany().getLevyNumber(), bankingDetails.getBankAccNumber());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void sdfDetail() {
		usersDataModel = new LazyDataModel<SDFCompany>() {

			private static final long serialVersionUID = 1L;
			private List<SDFCompany> retorno = new ArrayList<SDFCompany>();

			@Override
			public List<SDFCompany> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allSDFForCompany(SDFCompany.class, first, pageSize, sortField, sortOrder, filters, sdfCompany.getCompany(), getSessionUI().getActiveUser());
					usersDataModel.setRowCount(service.allSDFForCompanyCount(filters, sdfCompany.getCompany(), getSessionUI().getActiveUser()).intValue());

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
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	public void onRowSelect(SelectEvent event) {
		// this.sdfCompany = (SDFCompany)event.getObject();
		if (sdfCompany.getCompany().getCompanyStatus() == CompanyStatusEnum.Active || sdfCompany.getCompany().getCompanyStatus() == CompanyStatusEnum.DeRegistered) {
			this.index = 0;
			if (sdfCompany.getCompany().getPostalAddress() != null && sdfCompany.getCompany().getPostalAddress().getSameAddress() != null) {
				this.copyAddress = sdfCompany.getCompany().getPostalAddress().getSameAddress();
			} else {
				this.copyAddress = false;
			}
			globalSsdfCompany=sdfCompany;
			getEmployeesUI().setCompany(null);
			getEmployeesImportUI().setCompany(null);
			legacyorganisationsitesInfo();
			populatePreviuslyLinkedCompany();
		} else {
			this.sdfCompany = null;
			addErrorMessage("Your company is not active yet. Please allow time for the company to be approved");
		}

	}

	private void populatePreviuslyLinkedCompany(){
		try {
			if (sdfCompany.getCompany() != null && sdfCompany.getCompany().getPreviousCompany() != null && sdfCompany.getCompany().getPreviousCompany().getId() != null) {
				previouslyLinkedCompany = companyService.findByKeyFoReview(sdfCompany.getCompany().getPreviousCompany().getId());
			} else {
				previouslyLinkedCompany = null;
			}
		} catch (Exception e) {
			previouslyLinkedCompany = null;
			logger.fatal(e);
		}
	}

	public void onRowSelectNonSeta(SelectEvent event) {
		// this.sdfCompany = (SDFCompany)event.getObject();
		try {
			if (selectTrainingProviderApplication.getCompany().getCompanyStatus() == CompanyStatusEnum.Active || true) {
				this.index = 0;
				selectTrainingProviderApplication.setCompany(companyService.findByKey(selectTrainingProviderApplication.getCompany().getId()));
				if (selectTrainingProviderApplication.getCompany().getPostalAddress() != null && selectTrainingProviderApplication.getCompany().getPostalAddress().getSameAddress() != null) {
					this.copyAddress = selectTrainingProviderApplication.getCompany().getPostalAddress().getSameAddress();
				} else {
					this.copyAddress = false;
				}
				getEmployeesUI().setCompany(null);
				getEmployeesImportUI().setCompany(null);
			} else {
				this.selectTrainingProviderApplication = null;
				addErrorMessage("Your company is not active yet. Please allow time for the company to be approved");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}

	}
	
	public void legacyorganisationsitesInfo() {
		dataModelLegacySites = new LazyDataModel<LegacyOrganisationSites>() {
			private static final long serialVersionUID = 1L;
			private List<LegacyOrganisationSites> retorno = new ArrayList<LegacyOrganisationSites>();

			@Override
			public List<LegacyOrganisationSites> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					filters.put("linkedSdl", sdfCompany.getCompany().getLevyNumber());
					retorno = legacyOrganisationSitesService.allLegacyOrganisationSites(LegacyOrganisationSites.class, first, pageSize,
							sortField, sortOrder, filters);
					dataModelLegacySites.setRowCount(legacyOrganisationSitesService.count(LegacyOrganisationSites.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyOrganisationSites obj) {
				return obj.getId();
			}

			@Override
			public LegacyOrganisationSites getRowData(String rowKey) {
				for (LegacyOrganisationSites obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	/**
	 * Download application form.
	 */
	public void downloadApplicationForm() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("company_id", company.getId());
		try {
			String barcode = company.getCompanyGuid();
			// Generating barcode image
			params.put("barcode", GenericUtility.generateBarcode(barcode));
			JasperService.addLogo(params);
			// Adding SDF signature subreport
			JasperService.addApplicationSubreport(params);
			js.createReportFromDBtoServletOutputStream("MerSETAReport.jasper", params, company.getLevyNumber() + "-(" + GenericUtility.sdf6.format(getNow()) + ")-Application.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void prepChangeDoc() {
		clearChangeReason();
		doc = new Doc();
		doc.setChangeReason(changeReason);
	}
	
	public void prepWithdrawDoc() {
		try {
			ConfigDocService configDocService = new ConfigDocService();
			List<Doc>list = new ArrayList<Doc>();
			List<ConfigDoc> l = configDocService.findByProcess(ConfigDocProcessEnum.WITHDRAW_WORKPLACE_APPROVAL, CompanyUserTypeEnum.Company);
			l.forEach(a -> {
				list.add(new Doc(a));
			});
			if(list.size() > 0) {
				doc = list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void storeChangeNewFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setUsers(getSessionUI().getActiveUser());
			changeReason.setDoc(doc);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void storeWithdrawalFile(FileUploadEvent event) {
		try {
			prepWithdrawDoc();
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setUsers(getSessionUI().getActiveUser());
			doc.setTargetClass(workPlaceApproval.getClass().getName());
			doc.setTargetKey(workPlaceApproval.getId());
			witdrawWorkPlaceApproval();
			//changeReason.setDoc(doc);
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void storeNewFile(FileUploadEvent event) {
		try {
			if (index != 4) {
				doc.setCompany(sdfCompany.getCompany());
			} else {
				doc.setBankingDetails(bankingDetails);
			}
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			if (index != 4) {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
				onTabChange();
			} else {
				super.runClientSideUpdate("companyForm:tabView");
			}

			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

	}

	public void storeNewFileAndCreateTask(FileUploadEvent event) {
		try {
			if (index != 4) {
				doc.setCompany(sdfCompany.getCompany());
			} else {
				doc.setBankingDetails(bankingDetails);
			}
			doc.setApprovalStatus(ApprovalEnum.PendingApproval);
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			if (index != 4) {
				DocService.instance().uploadNewVersionAndCreateTask(doc, getSessionUI().getActiveUser());
				onTabChange();
			} else {
				super.runClientSideUpdate("companyForm:tabView");
			}

			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

	}

	public void updateCompayAndCreateTask(FileUploadEvent event) {
		CompanyStatusEnum orignalStatus = null;
		if (sdfCompany.getCompany() != null && sdfCompany.getCompany().getCompanyStatus() != null) {
			orignalStatus  = sdfCompany.getCompany().getCompanyStatus();
		}
		try {
			getSessionUI().setValidationErrors(null);
			runClientSideUpdate("validationErrorForm");
			validiationExceptionsCompany = "";
			sdfCompany.getCompany().setCompanyStatus(CompanyStatusEnum.PendingChangeApproval);
			sdfCompany.getCompany().setLocked(true);
			sdfCompany.getCompany().getResidentialAddress().setSameAddress(copyAddress);
			sdfCompany.getCompany().getPostalAddress().setSameAddress(copyAddress);
			service.changeDetails(sdfCompany.getCompany(), getSessionUI().getActiveUser());
			companysdfInfo();
			storeChangeNewFile(event);
			// Adding Change Reason
			ChangeReasonService.instance().createChangeReason(sdfCompany.getCompany().getId(), sdfCompany.getCompany().getClass().getName(), CompanyInfoUI.changeReason);
			addInfoMessage(super.getEntryLanguage("update.successful"));
		} catch (javax.validation.ConstraintViolationException e) {
			// fall back for SETMIS validiation fail
			if (orignalStatus == null) {
				sdfCompany.getCompany().setCompanyStatus(CompanyStatusEnum.Active);
			} else {
				sdfCompany.getCompany().setCompanyStatus(orignalStatus);
			}
			sdfCompany.getCompany().setLocked(false);
			validiationExceptionsCompany = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message by the company information.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void updateCompayWithoutTask() {
		CompanyStatusEnum orignalStatus = null;
		if (sdfCompany.getCompany() != null && sdfCompany.getCompany().getCompanyStatus() != null) {
			orignalStatus  = sdfCompany.getCompany().getCompanyStatus();
		}
		try {
			
			boolean result=false;
			System.out.println("HAJConstants.ADDRESS_SETMIS_VALIDATION_ON:"+HAJConstants.ADDRESS_SETMIS_VALIDATION_ON);
			if(!addressValidationService.validatingLatitudeDegrees(sdfCompany.getCompany().getResidentialAddress().getLatitudeDegrees())) {
				addErrorMessage("Validation Failed For SETMIS Latitude Degrees /Field may not start with a space.Uppercase value in field may only contain characters 1234567890-/Field must be a negative value, may not be greater than -22 and may not have a value less than -35");
				result=true;
			}
			if(!addressValidationService.validatingLatitudeMinutes(sdfCompany.getCompany().getResidentialAddress().getLatitudeMinutes())) {
				addErrorMessage("Validation Failed For SETMIS Latitude Minutes.Uppercase value in field may only contain characters 1234567890 . Value must have a length of exactly 2 (leading zeros) and may not be greater than 59");
				result=true;
			}
			if(!addressValidationService.validatingLatitudeSeconds(sdfCompany.getCompany().getResidentialAddress().getLatitudeSeconds())) {
				addErrorMessage("Validation Failed For SETMIS Latitude Seconds.Field may not start with a space.Uppercase value in field may only contain characters 1234567890.  Value must have a length of exactly 6 (nn.nnn) and may not be greater than 59.999");
				result=true;
			}
			
			
			if(!addressValidationService.validatingLongitudeDegrees(sdfCompany.getCompany().getResidentialAddress().getLongitudeDegrees())) {
				addErrorMessage("Validation Failed For SETMIS Longitude Degrees /Field may not start with a space.Uppercase value in field may only contain characters 1234567890-Field must not be a negative value, may not be greater than 33 and may not have a value less than 16");
				result=true;
			}
			if(!addressValidationService.validatingLongitudeMinutes(sdfCompany.getCompany().getResidentialAddress().getLongitudeMinutes())) {
				addErrorMessage("Validation Failed For SETMIS Longitude Minutes.Uppercase value in field may only contain characters 1234567890 . Value must have a length of exactly 2 (leading zeros) and may not be greater than 59");
				result=true;
			}
			if(!addressValidationService.validatingLongitudeSeconds(sdfCompany.getCompany().getResidentialAddress().getLongitudeSeconds())) {
				addErrorMessage("Validation Failed For SETMIS Longitude Seconds.Field may not start with a space.Uppercase value in field may only contain characters 1234567890.  Value must have a length of exactly 6 (nn.nnn) and may not be greater than 59.999");
				result=true;
			}
			
			if(result==false)
			{
			getSessionUI().setValidationErrors(null);
			runClientSideUpdate("validationErrorForm");
			sdfCompany.getCompany().getResidentialAddress().setSameAddress(copyAddress);
			sdfCompany.getCompany().getPostalAddress().setSameAddress(copyAddress);
			service.changeDetailsNoTask(sdfCompany.getCompany(), getSessionUI().getActiveUser());
			companysdfInfo();
			validiationExceptionsCompany = "";
			addInfoMessage(super.getEntryLanguage("update.successful"));
			}
		} catch (javax.validation.ConstraintViolationException e) {
			// fall back for SETMIS validiation fail
			if (orignalStatus == null) {
				sdfCompany.getCompany().setCompanyStatus(CompanyStatusEnum.Active);
			} else {
				sdfCompany.getCompany().setCompanyStatus(orignalStatus);
			}
			sdfCompany.getCompany().setLocked(false);
			validiationExceptionsCompany = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message by the company information.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void updateNonSetaCompayAndCreateTask(FileUploadEvent event) {
		try {
			selectTrainingProviderApplication.getCompany().setCompanyStatus(CompanyStatusEnum.PendingChangeApproval);
			selectTrainingProviderApplication.getCompany().setLocked(true);
			selectTrainingProviderApplication.getCompany().getResidentialAddress().setSameAddress(copyAddress);
			selectTrainingProviderApplication.getCompany().getPostalAddress().setSameAddress(copyAddress);
			service.changeDetails(selectTrainingProviderApplication.getCompany(), getSessionUI().getActiveUser());
			trainingProviderApplicationService.update(selectTrainingProviderApplication);
			nonSetaCompanysdfInfo();
			storeChangeNewFile(event);
			// Adding Change Reason
			ChangeReasonService.instance().createChangeReason(selectTrainingProviderApplication.getCompany().getId(), selectTrainingProviderApplication.getCompany().getClass().getName(), CompanyInfoUI.changeReason);
			addInfoMessage(super.getEntryLanguage("update.successful"));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void createTrainingCommitteeDelTask() {
		try {

			TrainingComitteeService comitteeService = new TrainingComitteeService();
			trainingcomittee.setApprovalStatus(ApprovalEnum.PendingApproval);
			comitteeService.updateTrainingComAndCreateDelTask(trainingcomittee, getSessionUI().getActiveUser());
			addInfoMessage(super.getEntryLanguage("update.successful"));
			super.runClientSideExecute("uploadDone()");
			super.runClientSideExecute("PF('dlgDelTCChangeReason').hide()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void doChange() {
		try {
			if (changeReason.getDescription().length() > 1000) {
				throw new Exception("Your reason is too long");
			}
			super.runClientSideExecute("PF('dlgChangeReasonText').hide()");
			super.runClientSideExecute(redirectionPage);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void redirect(FileUploadEvent event) {
		try {

			if (redirectionPage.equals("deleteSiste")) {
				storeChangeNewFile(event);
				sitesService.createDeleteSisteTask(site, getSessionUI().getActiveUser());

				super.runClientSideExecute("PF('dlgChangeReason').hide()");
				addInfoMessage(super.getEntryLanguage("update.successful"));
				super.runClientSideExecute("uploadDone()");
			} else {
				storeChangeNewFile(event);

				super.runClientSideExecute("PF('dlgChangeReason').hide()");
				super.runClientSideExecute(redirectionPage);
				super.runClientSideExecute("uploadDone()");
			}

		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void findByCompanyBankingDetails() {
		try {
			this.rejectReasonsChild = rejectReasonsService.findByCompanyBankingDetails(sdfCompany.getCompany());
		} catch (Exception e) {
			logger.fatal(e.getMessage(), e);
		}
	}

	public void clearChangeReason() {
		changeReason = new ChangeReason();
		changeReason.setDescription(null);
	}

	private void sitesSmeInfo() {
		try {
			if (sitesSmeService == null) {
				sitesSmeService = new SitesSmeService();
			}
			sitesSme = null;
			siteSmeDataInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void siteSmeDataInfo() {
		allSmeDataModel = new LazyDataModel<SitesSme>() {
			private static final long serialVersionUID = 1L;
			private List<SitesSme> retorno = new ArrayList<SitesSme>();

			@Override
			public List<SitesSme> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = sitesSmeService.allSitesSmeByCompany(first, pageSize, sortField, sortOrder, filters, sdfCompany.getCompany(), null, null);
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
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}

	private void workplaceApprovalInfo() {
		try {
			if (workPlaceApprovalService == null) {
				workPlaceApprovalService = new WorkPlaceApprovalService();
			}
			workplaceApprovalDataInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void workplaceApprovalDataInfo() {
		allWorkPlaceApprovalDataModel = new LazyDataModel<WorkPlaceApproval>() {
			private static final long serialVersionUID = 1L;
			private List<WorkPlaceApproval> retorno = new ArrayList<WorkPlaceApproval>();

			@Override
			public List<WorkPlaceApproval> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = workPlaceApprovalService.allWorkPlaceApprovalByCompanyAndSkillProgran(first, pageSize, sortField, sortOrder, filters, sdfCompany.getCompany());
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
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}

	public void prepWorkplaceApprovalRequest() {
		try {
			sitesList = sitesService.findByCompany(sdfCompany.getCompany());
			useCompanyAsSite = false;
			qualification = null;
			ofoCodes = null;
			byQualification = true;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepareWorkPlaceApproval() {
		workPlaceApproval = new WorkPlaceApproval();
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
					retorno = companyCommunicationService.allCompanyCommunicationByCompanyIdAndVisible(first, pageSize, sortField, sortOrder, filters, sdfCompany.getCompany().getId(), Boolean.TRUE);
					companyCommunicationDataModel.setRowCount(companyCommunicationService.countAllCompanyCommunicationByCompanyIdAndVisible(filters));
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
	
	public void prepLegacyEmployerWA2LearnershipRequest() {
		try {
			qualification = null;
			legacyEmployerWA2Qualification=null;
			//legacyEmployerWA2Learnership=null;
			legacyEmployerWA2SkillsProgramme=null;
			legacyEmployerWA2Trade = null;
			legacyEmployerWA2UnitStandard=null;
			if(legacyEmployerWA2Learnership != null) {
				LearnershipService learnershipService = new LearnershipService();
				String code = legacyEmployerWA2Learnership.getLearnershipCode();
				Learnership learnership = learnershipService.findByCode(code);
				if(learnership == null) {
					runClientSideExecute("PF('dlgAddLegacyWpa').hide()");
					throw new Exception("Error on registration, please contact your administrator");
				}else {
					prepareWorkPlaceApproval();
					workPlaceApproval.setLearnership(learnership);
					workPlaceApproval.setWorkplaceApprovalTypeEnum(WorkplaceApprovalTypeEnum.Learnership);
					sitesList = sitesService.findByCompany(sdfCompany.getCompany());
					useCompanyAsSite = false;
					qualification = learnership.getQualification();
					ofoCodes = null;
					byQualification = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepLegacyEmployerWA2QualificationRequest() {
		try {
			qualification = null;
			//legacyEmployerWA2Qualification=null;
			legacyEmployerWA2Learnership=null;
			legacyEmployerWA2SkillsProgramme=null;
			legacyEmployerWA2Trade = null;
			legacyEmployerWA2UnitStandard=null;
			if(legacyEmployerWA2Qualification != null) {
				qualification = legacyEmployerWA2Qualification.getQualification();
				if(qualification == null) {
					runClientSideExecute("PF('dlgAddLegacyWpa').hide()");
					throw new Exception("Error on registration, please contact your administrator");
				}else {
					prepareWorkPlaceApproval();
					workPlaceApproval.setQualification(qualification);
					workPlaceApproval.setWorkplaceApprovalTypeEnum(WorkplaceApprovalTypeEnum.Qualification);
					sitesList = sitesService.findByCompany(sdfCompany.getCompany());
					useCompanyAsSite = false;
					//qualification = null;
					ofoCodes = null;
					byQualification = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepLegacyEmployerWA2SkillsProgrammeRequest() {
		try {
			qualification = null;
			legacyEmployerWA2Qualification=null;
			legacyEmployerWA2Learnership=null;
			//legacyEmployerWA2SkillsProgramme=null;
			legacyEmployerWA2Trade = null;
			legacyEmployerWA2UnitStandard=null;
			if(legacyEmployerWA2SkillsProgramme != null) {
				SkillsProgramService skillsProgramService = new SkillsProgramService();
				SkillsProgram skillsProgram = skillsProgramService.findByProgrammeIDListReturnOne(legacyEmployerWA2SkillsProgramme.getsProgrammeCode());
				
				if(skillsProgram == null) {
					runClientSideExecute("PF('dlgAddLegacyWpa').hide()");
					throw new Exception("Error on registration, please contact your administrator");
				}else {
					prepareWorkPlaceApproval();
					workPlaceApproval.setSkillsProgram(skillsProgram);
					workPlaceApproval.setWorkplaceApprovalTypeEnum(WorkplaceApprovalTypeEnum.SkillsProgram);
					sitesList = sitesService.findByCompany(sdfCompany.getCompany());
					useCompanyAsSite = false;
					qualification = skillsProgram.getQualification();
					ofoCodes = null;
					byQualification = true;
					skillsProgramService = null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepLegacyEmployerWA2TradeRequest() {
		try {
			qualification = null;
			legacyEmployerWA2Qualification=null;
			legacyEmployerWA2Learnership=null;
			legacyEmployerWA2SkillsProgramme=null;
			//legacyEmployerWA2Trade = null;
			legacyEmployerWA2UnitStandard=null;
			if(legacyEmployerWA2Trade != null) {
				qualification = legacyEmployerWA2Trade.getQualification();
				if(qualification == null) {
					runClientSideExecute("PF('dlgAddLegacyWpa').hide()");
					throw new Exception("Error on registration, please contact your administrator");
				}else {
					prepareWorkPlaceApproval();
					workPlaceApproval.setQualification(qualification);
					workPlaceApproval.setWorkplaceApprovalTypeEnum(WorkplaceApprovalTypeEnum.Trade);
					sitesList = sitesService.findByCompany(sdfCompany.getCompany());
					useCompanyAsSite = false;
					//qualification = null;
					ofoCodes = null;
					byQualification = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepLegacyEmployerWA2UnitStandardRequest() {
		try {
			qualification = null;
			legacyEmployerWA2Qualification=null;
			legacyEmployerWA2Learnership=null;
			legacyEmployerWA2SkillsProgramme=null;
			legacyEmployerWA2Trade = null;
			//legacyEmployerWA2UnitStandard=null;
			if(legacyEmployerWA2UnitStandard != null) {
				UnitStandardsService unitStandardsService= new UnitStandardsService();
				UnitStandards unitStandards = unitStandardsService.findByUnitStandartCodeString(legacyEmployerWA2UnitStandard.getUnitStdCode());
				if(unitStandards == null) {
					runClientSideExecute("PF('dlgAddLegacyWpa').hide()");
					throw new Exception("Error on registration, please contact your administrator");
				}else {
					prepareWorkPlaceApproval();
					workPlaceApproval.setUnitstandard(unitStandards);
					workPlaceApproval.setWorkplaceApprovalTypeEnum(WorkplaceApprovalTypeEnum.UnitStandard);
					sitesList = sitesService.findByCompany(sdfCompany.getCompany());
					useCompanyAsSite = false;
					qualification = unitStandards.getQualification();
					ofoCodes = null;
					byQualification = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public List<Sites> completeSites(String desc) {
		List<Sites> l = null;
		try {
			l = sitesService.findByNameAndCompany(desc, sdfCompany.getCompany());
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Creates workplace approval
	 */
	public void createNewWorkPlaceApproval() {
		try {
			if (workPlaceApprovalService == null) {
				workPlaceApprovalService = new WorkPlaceApprovalService();
			}
			if (sitesList.size() != 0 && !useCompanyAsSite) {
				if (selectedSite == null) {
					throw new Exception("Select A Site Before Proceeding");
				}
			}
			if (byQualification) {
				ofoCodes = null;
			}else { 
				ofoCodes = null;
				//qualification = null; 
			}
			
			if (qualification == null && ofoCodes == null) {
				addWarningMessage("Provide either: Trade or Qualification Before Proceeding.");
			} else {
				if (workPlaceApprovalService.checkForExistingRecords(sdfCompany.getCompany(), qualification, ofoCodes, selectedSite) == 0) {
					workPlaceApprovalService.createWorkplaceApproval(sdfCompany.getCompany(), qualification, ofoCodes, selectedSite, getSessionUI().getActiveUser(), byQualification);
					addInfoMessage("Workplace Approval application initiated. Review application");
					runClientSideExecute("PF('dlgAddWpa').hide()");
					workplaceApprovalInfo();
				} else {
					addWarningMessage("A Workplace Approval has been created for that qualification/trade, please apply for a different qualification/trade");
				}
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Creates workplace approval
	 */
	public void createLegacyWorkPlaceApproval() {
		try {
			if (workPlaceApprovalService == null) {
				workPlaceApprovalService = new WorkPlaceApprovalService();
			}
			if (sitesList.size() != 0 && !useCompanyAsSite) {
				if (selectedSite == null) {
					throw new Exception("Select A Site Before Proceeding");
				}
			}
			if (byQualification) {
				ofoCodes = null;
			}else { 
				ofoCodes = null;
				//qualification = null; 
			}
			
			if (qualification == null && ofoCodes == null) {
				addWarningMessage("Provide either: Trade or Qualification Before Proceeding.");
			} else {
				if (workPlaceApprovalService.checkForExistingRecords(sdfCompany.getCompany(), qualification, ofoCodes, selectedSite) == 0) {
					workPlaceApprovalService.createLegacyWorkplaceApproval(workPlaceApproval, sdfCompany.getCompany(), qualification, ofoCodes, selectedSite, getSessionUI().getActiveUser(), byQualification, legacyEmployerWA2Workplace,
							 legacyEmployerWA2Learnership,  legacyEmployerWA2Qualification,  legacyEmployerWA2SkillsProgramme,  legacyEmployerWA2Trade, legacyEmployerWA2UnitStandard);
					
					
					addInfoMessage("Workplace Approval application initiated. Review application");
					runClientSideExecute("PF('dlgAddLegacyWpa').hide()");
					workplaceApprovalInfo();
				} else {
					addWarningMessage("A Workplace Approval has been created for that qualification/trade, please apply for a different qualification/trade");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void downloadWorkPlaceApproval() {
		try {
			workPlaceApprovalService.downloadVTwo(workPlaceApproval);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void witdrawWorkPlaceApproval() {
		try {
			if(doc == null) {
				throw new Exception("Please Upload Required Document");
			}
			if(doc != null && doc.getTargetKey() == null) {
				throw new Exception("Please Upload Required Document");
			}
			workPlaceApprovalService.witdrawWorkPlaceApproval(workPlaceApproval, getSessionUI().getActiveUser(), null, doc);
			addInfoMessage("Your Application Has Been Submitted For Review");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createMentorWorkPlaceApproval() {
		try {
			if(workPlaceApproval != null) {
				workPlaceApprovalMentorsService.createWorkplaceApproval(workPlaceApproval, getSessionUI().getActiveUser());
				addInfoMessage("Your Application Has Been Submitted For Review");
			}else {
				throw new Exception("WorkPlace Approval Error");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage());
		}
	}

	public void prepareMentorWorkPlaceApproval() {
		try {
			//workPlaceApprovalMentors = new WorkPlaceApprovalMentors();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void downloadRemittanceAdvice() {
		try {
			RemittanceAdviceService.instance().generateRemittanceAdvice(sdfCompany.getCompany());
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void getCoOrdinates_vh() {
		System.out.println("in getCoOrdinates_vh");
		System.out.println("-----"+sdfCompany.getCompany().getResidentialAddress().getAddressLine1());
		System.out.println("-----"+sdfCompany.getCompany().getResidentialAddress().getAddressLine2());
		addressService.lookupLongitudeLatitude(sdfCompany.getCompany().getResidentialAddress());
	}
	
	/**
	 * Gets the company.
	 *
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * Sets the company.
	 *
	 * @param company
	 *            the new company
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * Gets the banking details.
	 *
	 * @return the banking details
	 */
	public BankingDetails getBankingDetails() {
		return bankingDetails;
	}

	/**
	 * Sets the banking details.
	 *
	 * @param bankingDetails
	 *            the new banking details
	 */
	public void setBankingDetails(BankingDetails bankingDetails) {
		this.bankingDetails = bankingDetails;
	}

	public LazyDataModel<SDFCompany> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SDFCompany> dataModel) {
		this.dataModel = dataModel;
	}

	public SDFCompany getSdfCompany() {
		return sdfCompany;
	}

	public void setSdfCompany(SDFCompany sdfCompany) {
		this.sdfCompany = sdfCompany;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public LazyDataModel<SDFCompany> getUsersDataModel() {
		return usersDataModel;
	}

	public void setUsersDataModel(LazyDataModel<SDFCompany> usersDataModel) {
		this.usersDataModel = usersDataModel;
	}

	public boolean isCopyAddress() {
		return copyAddress;
	}

	public void setCopyAddress(boolean copyAddress) {
		this.copyAddress = copyAddress;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public Doc getNewVersionDoc() {
		return newVersionDoc;
	}

	public void setNewVersionDoc(Doc newVersionDoc) {
		this.newVersionDoc = newVersionDoc;
	}

	public SitesService getSitesService() {
		return sitesService;
	}

	public void setSitesService(SitesService sitesService) {
		this.sitesService = sitesService;
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

	public List<CompanyUsers> getCompanyUsers() {
		return companyUsers;
	}

	public void setCompanyUsers(List<CompanyUsers> companyUsers) {
		this.companyUsers = companyUsers;
	}

	public List<Sites> getSites() {
		return sites;
	}

	public void setSites(List<Sites> sites) {
		this.sites = sites;
	}

	public List<BankingDetails> getBankingDetailsList() {
		return bankingDetailsList;
	}

	public void setBankingDetailsList(List<BankingDetails> bankingDetailsList) {
		this.bankingDetailsList = bankingDetailsList;
	}

	public EmployeesImportUI getEmployeesImportUI() {
		return employeesImportUI;
	}

	public void setEmployeesImportUI(EmployeesImportUI employeesImportUI) {
		this.employeesImportUI = employeesImportUI;
	}

	public EmployeesUI getEmployeesUI() {
		return employeesUI;
	}

	public void setEmployeesUI(EmployeesUI employeesUI) {
		this.employeesUI = employeesUI;
	}

	public String getRedirectionPage() {
		return redirectionPage;
	}

	public void setRedirectionPage(String redirectionPage) {
		this.redirectionPage = redirectionPage;
	}

	public ChangeReason getChangeReason() {
		return changeReason;
	}

	public List<RejectReasonsChild> getRejectReasonsChild() {
		return rejectReasonsChild;
	}

	public void setRejectReasonsChild(List<RejectReasonsChild> rejectReasonsChild) {
		this.rejectReasonsChild = rejectReasonsChild;
	}

	public static void setChangeReason(ChangeReason changeReason) {
		CompanyInfoUI.changeReason = changeReason;
	}

	public TrainingComittee getTrainingcomittee() {
		return trainingcomittee;
	}

	public void setTrainingcomittee(TrainingComittee trainingcomittee) {
		this.trainingcomittee = trainingcomittee;
	}

	public Sites getSite() {
		return site;
	}

	public void setSite(Sites site) {
		this.site = site;
	}

	public boolean isCanCreateNewCompany() {
		return canCreateNewCompany;
	}

	public void setCanCreateNewCompany(boolean canCreateNewCompany) {
		this.canCreateNewCompany = canCreateNewCompany;
	}

	/**
	 * @return the activeContracts
	 */
	public List<ActiveContracts> getActiveContracts() {
		return activeContracts;
	}

	/**
	 * @param activeContracts
	 *            the activeContracts to set
	 */
	public void setActiveContracts(List<ActiveContracts> activeContracts) {
		this.activeContracts = activeContracts;
	}

	public SitesSme getSitesSme() {
		return sitesSme;
	}

	public void setSitesSme(SitesSme sitesSme) {
		this.sitesSme = sitesSme;
	}

	public LazyDataModel<SitesSme> getSitesSmeDataModel() {
		return sitesSmeDataModel;
	}

	public void setSitesSmeDataModel(LazyDataModel<SitesSme> sitesSmeDataModel) {
		this.sitesSmeDataModel = sitesSmeDataModel;
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

	public WorkPlaceApproval getWorkPlaceApproval() {
		return workPlaceApproval;
	}

	public void setWorkPlaceApproval(WorkPlaceApproval workPlaceApproval) {
		this.workPlaceApproval = workPlaceApproval;
	}

	public boolean isByQualification() {
		return byQualification;
	}

	public void setByQualification(boolean byQualification) {
		this.byQualification = byQualification;
	}

	public OfoCodes getOfoCodes() {
		return ofoCodes;
	}

	public void setOfoCodes(OfoCodes ofoCodes) {
		this.ofoCodes = ofoCodes;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public List<Sites> getSitesList() {
		return sitesList;
	}

	public void setSitesList(List<Sites> sitesList) {
		this.sitesList = sitesList;
	}

	public boolean isUseCompanyAsSite() {
		return useCompanyAsSite;
	}

	public void setUseCompanyAsSite(boolean useCompanyAsSite) {
		this.useCompanyAsSite = useCompanyAsSite;
	}

	public Sites getSelectedSite() {
		return selectedSite;
	}

	public void setSelectedSite(Sites selectedSite) {
		this.selectedSite = selectedSite;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	public LazyDataModel<Wsp> getDataModelWspWithAllocation() {
		return dataModelWspWithAllocation;
	}

	public void setDataModelWspWithAllocation(LazyDataModel<Wsp> dataModelWspWithAllocation) {
		this.dataModelWspWithAllocation = dataModelWspWithAllocation;
	}

	public LazyDataModel<TrainingProviderApplication> getTpDataModel() {
		return tpDataModel;
	}

	public void setTpDataModel(LazyDataModel<TrainingProviderApplication> tpDataModel) {
		this.tpDataModel = tpDataModel;
	}

	public TrainingProviderApplication getSelectTrainingProviderApplication() {
		return selectTrainingProviderApplication;
	}

	public void setSelectTrainingProviderApplication(TrainingProviderApplication selectTrainingProviderApplication) {
		this.selectTrainingProviderApplication = selectTrainingProviderApplication;
	}
	public static SDFCompany getGlobalSsdfCompany() {
		return globalSsdfCompany;
	}

	public static void setGlobalSsdfCompany(SDFCompany globalSsdfCompany) {
		CompanyInfoUI.globalSsdfCompany = globalSsdfCompany;
	}

	public LazyDataModel<LegacyOrganisationSites> getDataModelLegacySites() {
		return dataModelLegacySites;
	}

	public void setDataModelLegacySites(LazyDataModel<LegacyOrganisationSites> dataModelLegacySites) {
		this.dataModelLegacySites = dataModelLegacySites;
	}

	public Company getPreviouslyLinkedCompany() {
		return previouslyLinkedCompany;
	}

	public void setPreviouslyLinkedCompany(Company previouslyLinkedCompany) {
		this.previouslyLinkedCompany = previouslyLinkedCompany;
	}

	public LegacyEmployerWA2Workplace getLegacyEmployerWA2Workplace() {
		return legacyEmployerWA2Workplace;
	}

	public void setLegacyEmployerWA2Workplace(LegacyEmployerWA2Workplace legacyEmployerWA2Workplace) {
		this.legacyEmployerWA2Workplace = legacyEmployerWA2Workplace;
	}

	public LegacyEmployerWA2Learnership getLegacyEmployerWA2Learnership() {
		return legacyEmployerWA2Learnership;
	}

	public void setLegacyEmployerWA2Learnership(LegacyEmployerWA2Learnership legacyEmployerWA2Learnership) {
		this.legacyEmployerWA2Learnership = legacyEmployerWA2Learnership;
	}

	public LegacyEmployerWA2Qualification getLegacyEmployerWA2Qualification() {
		return legacyEmployerWA2Qualification;
	}

	public void setLegacyEmployerWA2Qualification(LegacyEmployerWA2Qualification legacyEmployerWA2Qualification) {
		this.legacyEmployerWA2Qualification = legacyEmployerWA2Qualification;
	}

	public LegacyEmployerWA2SkillsProgramme getLegacyEmployerWA2SkillsProgramme() {
		return legacyEmployerWA2SkillsProgramme;
	}

	public void setLegacyEmployerWA2SkillsProgramme(LegacyEmployerWA2SkillsProgramme legacyEmployerWA2SkillsProgramme) {
		this.legacyEmployerWA2SkillsProgramme = legacyEmployerWA2SkillsProgramme;
	}

	public LegacyEmployerWA2Trade getLegacyEmployerWA2Trade() {
		return legacyEmployerWA2Trade;
	}

	public void setLegacyEmployerWA2Trade(LegacyEmployerWA2Trade legacyEmployerWA2Trade) {
		this.legacyEmployerWA2Trade = legacyEmployerWA2Trade;
	}

	public LegacyEmployerWA2UnitStandard getLegacyEmployerWA2UnitStandard() {
		return legacyEmployerWA2UnitStandard;
	}

	public void setLegacyEmployerWA2UnitStandard(LegacyEmployerWA2UnitStandard legacyEmployerWA2UnitStandard) {
		this.legacyEmployerWA2UnitStandard = legacyEmployerWA2UnitStandard;
	}

	public String getValidiationExceptionsCompany() {
		return validiationExceptionsCompany;
	}

	public void setValidiationExceptionsCompany(String validiationExceptionsCompany) {
		this.validiationExceptionsCompany = validiationExceptionsCompany;
	}

	public LazyDataModel<CompanyCommunication> getCompanyCommunicationDataModel() {
		return companyCommunicationDataModel;
	}

	public void setCompanyCommunicationDataModel(LazyDataModel<CompanyCommunication> companyCommunicationDataModel) {
		this.companyCommunicationDataModel = companyCommunicationDataModel;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
}
